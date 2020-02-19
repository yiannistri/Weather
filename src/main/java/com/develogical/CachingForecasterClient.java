package com.develogical;

import com.weather.Day;
import com.weather.Forecast;
import com.weather.Region;

public class CachingForecasterClient implements ForecasterClient {
    private final ForecasterClient delegate;

    public CachingForecasterClient(ForecasterClient delegate) {
        this.delegate = delegate;
    }

    @Override
    public Forecast forecastFor(Region region, Day day) {
        return delegate.forecastFor(region, day);
    }
}
