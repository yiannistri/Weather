package com.develogical;

import com.weather.*;

import java.util.ArrayList;

public class WeatherForecastClient {
    public IWeatherForecaster forecaster ;
    public ArrayList<WeatherForecast> cache = new ArrayList<WeatherForecast>();
    public Integer cacheLimit = 100;

    public WeatherForecastClient(IWeatherForecaster forecaster) {
        this.forecaster = forecaster;
    }

    public WeatherForecastClient(IWeatherForecaster forecaster, Integer cacheLimit) {
        this.forecaster = forecaster;
        this.cacheLimit = cacheLimit;
    }
    public WeatherForecast GetForecast(Region region, Day day) {
        WeatherForecast weatherForecast = forecaster.getResult(region, day);
        addToCache(weatherForecast);
        return weatherForecast;
    }

    protected void addToCache(WeatherForecast forecast) {
        if (cache.size() >= cacheLimit) {
            cache.remove(cacheLimit-1);
        }
        cache.add(forecast);
    }
}
