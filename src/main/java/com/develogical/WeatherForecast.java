package com.develogical;

import com.weather.*;

import java.sql.Timestamp;

public class WeatherForecast {
    public Forecast forecast;
    public Timestamp timestamp;

    public WeatherForecast(Forecast forecast, Timestamp timestamp){
        this.forecast = forecast;
        this.timestamp = timestamp;
    }
}
