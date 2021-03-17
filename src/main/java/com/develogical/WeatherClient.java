package com.develogical;

import com.weather.*;

public class WeatherClient {
    public Forecaster forecaster;

    public WeatherClient() {
        this.forecaster = new Forecaster();
    }

    public WeatherClient(Forecaster forecaster) {
        this.forecaster = forecaster;
    }
}
