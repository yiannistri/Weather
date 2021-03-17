package com.develogical;

import com.weather.Day;
import com.weather.Forecast;
import com.weather.Forecaster;
import com.weather.Region;
import org.junit.Test;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

public class WeatherClientTest {
    @Test
    public void defaultConstructorInitialisesForecaster() {
        WeatherForecastAdaptor forecaster = mock(WeatherForecastAdaptor.class);
        WeatherForecastClient underTest = new WeatherForecastClient(forecaster);
        assert(underTest.forecaster != null);
    }

    @Test
    public void overloadConstructorAssignsForecaster() {
        WeatherForecastAdaptor forecaster = mock(WeatherForecastAdaptor.class);
        WeatherForecastClient underTest = new WeatherForecastClient(forecaster);
        assert(underTest.forecaster != null);
    }

    @Test
    public void getForecastCallsForecastAdaptorWithCorrectParameters() {
        IWeatherForecaster forecaster =  mock(IWeatherForecaster.class);
        WeatherForecastClient underTest = new WeatherForecastClient(forecaster);
        Region region = Region.LONDON;
        Day day = Day.MONDAY;
        underTest.GetForecast(region,day);
        verify(forecaster).getResult(region,day);
    }

    @Test
    public void getForecastCachesResult() {
        IWeatherForecaster forecaster =  mock(IWeatherForecaster.class);
        WeatherForecastClient underTest = new WeatherForecastClient(forecaster);
        Region region = Region.LONDON;
        Day day = Day.MONDAY;
        WeatherForecast forecast0 = underTest.GetForecast(region,day);
        WeatherForecast forecast1 = underTest.GetForecast(region,day);
        assert (underTest.cache != null);
    }

    @Test
    public void setForecasterCacheLimit() {
        IWeatherForecaster forecaster =  mock(IWeatherForecaster.class);
        WeatherForecastClient underTest = new WeatherForecastClient(forecaster,1);
        assert(underTest.cacheLimit == 1);
    }

    @Test
    public void checkIfMaxLimitIsRespected(){
        IWeatherForecaster forecaster =  mock(IWeatherForecaster.class);
        WeatherForecastClient underTest = new WeatherForecastClient(forecaster,1);

        Region region = Region.LONDON;
        Day monday = Day.MONDAY;
        Day tuesday = Day.TUESDAY;
        underTest.GetForecast(region,monday);
        underTest.GetForecast(region,tuesday);
        underTest.GetForecast(region,tuesday);

        assert(underTest.cache.size() == 1);
    }

    @Test
    public void checkIfTimeLimitIsRespected(){
        IWeatherForecaster forecaster =  mock(IWeatherForecaster.class);
        WeatherForecastAdaptor canaryGenerator = new WeatherForecastAdaptor();
        WeatherForecastClient underTest = new WeatherForecastClient(forecaster,2);

        Region region = Region.LONDON;
        Day tuesday = Day.TUESDAY;
        WeatherForecast canary = canaryGenerator.getResult(region,tuesday);
        canary.timestamp = new Timestamp(System.currentTimeMillis() - (60 * 60 * 1000));
        underTest.cache.put(region.name()+ tuesday.name(), canary);

        underTest.GetForecast(region,tuesday);
        assert(underTest.cache.size() == 1);
    }

    @Test
    public void fetchesFromCacheRatherThanReading(){
        IWeatherForecaster forecaster =  mock(IWeatherForecaster.class);
        given(forecaster.getResult(Region.LONDON, Day.MONDAY)).willReturn(new WeatherForecast(new Forecast("Hot",30), new Timestamp(System.currentTimeMillis())));
        WeatherForecastClient underTest = new WeatherForecastClient(forecaster,2);
        underTest.GetForecast(Region.LONDON, Day.MONDAY);
        WeatherForecast weatherForecast2 = underTest.GetForecast(Region.LONDON, Day.MONDAY);
        assertThat(weatherForecast2.forecast.summary(), equalTo("Hot"));
        verify(forecaster, times(1)).getResult(Region.LONDON, Day.MONDAY);
    }
}