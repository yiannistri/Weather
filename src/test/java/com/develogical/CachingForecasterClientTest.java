package com.develogical;

import com.weather.Day;
import com.weather.Forecast;
import com.weather.Region;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CachingForecasterClientTest {
    private final ForecasterClient delegate = mock(ForecasterClient.class);
    private final CachingForecasterClient forecaster = new CachingForecasterClient(delegate);

    @Test
    public void getsForecastsFromDelegate() throws Exception {
        when(delegate.forecastFor(Region.BIRMINGHAM, Day.FRIDAY)).thenReturn(new Forecast("Sunny", 77));

        Forecast forecast = forecaster.forecastFor(Region.BIRMINGHAM, Day.FRIDAY);
        assertThat(forecast.summary(), equalTo("Sunny"));
        assertThat(forecast.temperature(), equalTo(77));
    }

    @Test
    public void cachesForecastsFromDelegate() throws Exception {
        when(delegate.forecastFor(Region.BIRMINGHAM, Day.FRIDAY)).thenReturn(new Forecast("Sunny", 77));

        forecaster.forecastFor(Region.BIRMINGHAM, Day.FRIDAY);
        Forecast forecast = forecaster.forecastFor(Region.BIRMINGHAM, Day.FRIDAY);
        assertThat(forecast.summary(), equalTo("Sunny"));
        assertThat(forecast.temperature(), equalTo(77));

        verify(delegate, times(1)).forecastFor(ArgumentMatchers.<Region>any(), ArgumentMatchers.<Day>any());
    }

    @Test
    public void returnsDifferentAnswerForDifferentRegion() throws Exception {
        when(delegate.forecastFor(Region.BIRMINGHAM, Day.FRIDAY)).thenReturn(new Forecast("Sunny", 77));
        when(delegate.forecastFor(Region.GLASGOW, Day.FRIDAY)).thenReturn(new Forecast("Cloudy", 17));

        forecaster.forecastFor(Region.BIRMINGHAM, Day.FRIDAY);
        Forecast forecast = forecaster.forecastFor(Region.GLASGOW, Day.FRIDAY);
        assertThat(forecast.summary(), equalTo("Cloudy"));
        assertThat(forecast.temperature(), equalTo(17));
    }

    @Test
    public void returnsDifferentAnswerForDifferentDay() throws Exception {
        when(delegate.forecastFor(Region.BIRMINGHAM, Day.FRIDAY)).thenReturn(new Forecast("Sunny", 77));
        when(delegate.forecastFor(Region.BIRMINGHAM, Day.THURSDAY)).thenReturn(new Forecast("Cloudy", 17));

        forecaster.forecastFor(Region.BIRMINGHAM, Day.FRIDAY);
        Forecast forecast = forecaster.forecastFor(Region.BIRMINGHAM, Day.THURSDAY);
        assertThat(forecast.summary(), equalTo("Cloudy"));
        assertThat(forecast.temperature(), equalTo(17));
    }
}
