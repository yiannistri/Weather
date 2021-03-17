package com.develogical;

import com.weather.Day;
import com.weather.Forecast;
import com.weather.Forecaster;
import com.weather.Region;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.mockito.Mockito.*;

public class WeatherClientTest {
    @Test
    public void defaultConstructorInitialisesForecaster() {
        WeatherForecastClient underTest = new WeatherForecastClient();
        assert(underTest.forecaster != null);
    }

    @Test
    public void overloadConstructorAssignsForecaster() {
        WeatherForecastAdapter forecaster = mock(WeatherForecastAdapter.class);
        WeatherForecastClient underTest = new WeatherForecastClient(forecaster);
        assert(underTest.forecaster != null);
    }

    @Test
    public void getForecastCallsForecastAdaptorWithCorrectParameters() {
        WeatherForecastAdapter forecaster = mock(WeatherForecastAdapter.class);
        WeatherForecastClient underTest = new WeatherForecastClient(forecaster);
        Region region = Region.LONDON;
        Day day = Day.MONDAY;
        underTest.GetForecast(region,day);
        verify(forecaster).getResult(region,day);
    }

    @Test
    public void getForecastCachesResult() {
        WeatherForecastAdapter forecaster = mock(WeatherForecastAdapter.class);
        WeatherForecastClient underTest = new WeatherForecastClient(forecaster);
        Region region = Region.LONDON;
        Day day = Day.MONDAY;
        Forecast forecast0 = underTest.GetForecast(region,day);
        Forecast forecast1 = underTest.GetForecast(region,day);
        assert (forecast0 == forecast1);
    }
}