package com.develogical;

import com.weather.Day;
import com.weather.Forecast;
import com.weather.Region;

import java.util.HashMap;
import java.util.Map;

public class CachingForecasterClient implements ForecasterClient {
    private final Map<Region, Forecast> cache = new HashMap<>();
    private final ForecasterClient delegate;

    public CachingForecasterClient(ForecasterClient delegate) {
        this.delegate = delegate;
    }

    @Override
    public Forecast forecastFor(Region region, Day day) {
        if (!cache.containsKey(region)) {
            cache.put(region, delegate.forecastFor(region, day));
        }
        return cache.get(region);
    }
}
