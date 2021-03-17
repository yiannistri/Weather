package com.develogical;

import com.weather.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class WeatherForecastClient {
    public IWeatherForecaster forecaster ;
    public Map<String,WeatherForecast> cache = new LinkedHashMap<>();
    public Integer cacheLimit = 100;

    public WeatherForecastClient(IWeatherForecaster forecaster) {
        this.forecaster = forecaster;
    }

    public WeatherForecastClient(IWeatherForecaster forecaster, Integer cacheLimit) {
        this.forecaster = forecaster;
        this.cacheLimit = cacheLimit;
    }
    public WeatherForecast GetForecast(Region region, Day day) {
        WeatherForecast cached = getFromCacheNotExpired(region, day);

        if (cached != null) {
            return cached;
        }
        else {
            WeatherForecast weatherForecast = forecaster.getResult(region, day);
            addToCache(region.name() + day.name(), weatherForecast);
            return weatherForecast;
        }
    }
    protected WeatherForecast getFromCacheNotExpired(Region region, Day day) {
        WeatherForecast cached = cache.get(region.name() + day.name());
        if (cached != null) {
            if (cached.timestamp.before(new Timestamp(System.currentTimeMillis() - (60 * 60 * 1000)))) {
                cache.remove(region.name(), day.name());
                return null;
            } else {
                return cached;
            }
        } else {
            return null;
        }
    }

    protected void addToCache(String key, WeatherForecast forecast) {
        if (cache.size() >= cacheLimit) {
            ArrayList<String> arrayList = new ArrayList<>(cache.keySet());
            String keyToRemove = arrayList.get(arrayList.size() -1);
            cache.remove(keyToRemove);
        }
        cache.put(key,forecast);
    }
}
