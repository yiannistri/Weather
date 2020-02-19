package com.develogical;

import com.weather.Day;
import com.weather.Forecast;
import com.weather.Region;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public class CachingForecasterClient implements ForecasterClient {
    private final Map<Key, Forecast> cache = new LinkedHashMap<>();
    private final ForecasterClient delegate;
    private final int maxSize;

    public CachingForecasterClient(ForecasterClient delegate, int maxSize) {
        this.delegate = delegate;
        this.maxSize = maxSize;
    }

    @Override
    public Forecast forecastFor(Region region, Day day) {
        evictOldestIfNecessary();
        Key key = new Key(region, day);
        if (!cache.containsKey(key)) {
            cache.put(key, delegate.forecastFor(region, day));
        }
        return cache.get(key);
    }

    private void evictOldestIfNecessary() {
        if (cache.size() >= maxSize) {
            cache.remove(cache.keySet().iterator().next());
        }
    }

    static class Key {
        private final Region region;
        private final Day day;

        Key(Region region, Day day) {
            this.region = region;
            this.day = day;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Key key = (Key) o;
            return region == key.region &&
                    day == key.day;
        }

        @Override
        public int hashCode() {
            return Objects.hash(region, day);
        }
    }
}
