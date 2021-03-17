package com.develogical;

import com.weather.*;
import java.util.Date;

public class WeatherForecastClient {
    public WeatherForecastAdapter forecaster ;

    public WeatherForecastClient() {
        this.forecaster = new WeatherForecastAdapter();
    }

    public WeatherForecastClient(WeatherForecastAdapter forecaster) {
        this.forecaster = forecaster;
    }

    public Forecast GetForecast(Region region, Day day) {
        return forecaster.getResult(region, day);
    }
}