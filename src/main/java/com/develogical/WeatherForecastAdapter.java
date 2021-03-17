package com.develogical;

import com.weather.*;

public class WeatherForecastAdapter implements IWeatherForecast {
    private Forecaster forecaster;

    public WeatherForecastAdapter() {
        this.forecaster = new Forecaster();
    }

    public Forecast getResult(Region region, Day day) {
        return this.forecaster.forecastFor(region, day);
    }
}
