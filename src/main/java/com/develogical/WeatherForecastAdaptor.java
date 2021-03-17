package com.develogical;

import com.weather.*;

import java.sql.Timestamp;

public class WeatherForecastAdaptor extends Forecaster implements IWeatherForecaster {

    public WeatherForecast getResult(Region region, Day day) {
        return new WeatherForecast(forecastFor(region, day), new Timestamp(System.currentTimeMillis()));
    }
}
