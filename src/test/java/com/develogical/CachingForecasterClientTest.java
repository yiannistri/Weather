package com.develogical;

import com.weather.Day;
import com.weather.Forecast;
import com.weather.Region;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CachingForecasterClientTest {
    @Test
    public void getsForecastsFromDelegate() throws Exception {
        ForecasterClient delegate = mock(ForecasterClient.class);

        when(delegate.forecastFor(Region.BIRMINGHAM, Day.FRIDAY)).thenReturn(new Forecast("Sunny", 77));

        CachingForecasterClient forecaster = new CachingForecasterClient(delegate);

        Forecast forecast = forecaster.forecastFor(Region.BIRMINGHAM, Day.FRIDAY);
        assertThat(forecast.summary(), equalTo("Sunny"));
        assertThat(forecast.temperature(), equalTo(77));
    }
}
