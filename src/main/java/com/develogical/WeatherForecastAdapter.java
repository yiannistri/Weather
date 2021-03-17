package com.develogical;

import com.weather.*;

import java.sql.Timestamp;
import java.util.ArrayList;

public class WeatherForecastAdapter implements IWeatherForecaster {
    private Forecaster forecaster;
    public ArrayList<WeatherForecast> cache = new ArrayList<>();
    public Integer cacheLimit = 100;

    public WeatherForecastAdapter() {
        this.forecaster = new Forecaster();
    }

    public WeatherForecast getResult(Region region, Day day) {
        Forecast forecast = forecaster.forecastFor(region, day);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        WeatherForecast weatherForecast = new WeatherForecast(forecast,timestamp);
        cache.add(weatherForecast);
        return weatherForecast;
    }

    public void setCacheLimit(Integer limit){
        //Not implemented
    }
}
