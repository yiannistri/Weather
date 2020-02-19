package com.develogical;

import com.weather.Day;
import com.weather.Forecast;
import com.weather.Region;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;

public class CachingForecasterClient implements ForecasterClient {
    private final Supplier<Instant> clock;
    private final Map<Key, ForecastWithTime> cache = new LinkedHashMap<>();
    private final ForecasterClient delegate;
    private final int maxSize;

    public CachingForecasterClient(ForecasterClient delegate, int maxSize, Supplier<Instant> clock) {
        this.delegate = delegate;
        this.maxSize = maxSize;
        this.clock = clock;
    }

    @Override
    public Forecast forecastFor(Region region, Day day) {
        evictOldestIfNecessary();
        Key key = new Key(region, day);
        evictIfStale(key);
        if (!cache.containsKey(key)) {
            Forecast forecast = delegate.forecastFor(region, day);
            Instant evictionTime = now().plus(1, ChronoUnit.HOURS);
            cache.put(key, new ForecastWithTime(forecast, evictionTime));
        }
        return cache.get(key).forecast;
    }

    private void evictIfStale(Key key) {
        ForecastWithTime forecastWithTime = cache.get(key);
        if (forecastWithTime != null && forecastWithTime.isStale(now())) {
            cache.remove(key);
        }
    }

    private void evictOldestIfNecessary() {
        if (cache.size() >= maxSize) {
            cache.remove(cache.keySet().iterator().next());
        }
    }

    private Instant now() {
        return clock.get();
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

    static class ForecastWithTime {
        private final Forecast forecast;
        private final Instant evictionTime;

        ForecastWithTime(Forecast forecast, Instant evictionTime) {
            this.forecast = forecast;
            this.evictionTime = evictionTime;
        }

        private boolean isStale(Instant now) {
            return now.isAfter(evictionTime);
        }
    }
}
