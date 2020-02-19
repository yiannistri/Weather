package com.develogical;

import com.weather.Day;
import com.weather.Forecast;
import com.weather.Region;

public class CachingForecasterClient implements ForecasterClient {
    private final ForecasterClient delegate;
    private Forecast cache;

    public CachingForecasterClient(ForecasterClient delegate) {
        this.delegate = delegate;
    }

    @Override
    public Forecast forecastFor(Region region, Day day) {
        if (cache != null) {
            return cache;
        }
        cache = delegate.forecastFor(region, day);
        return cache;
    }
}
