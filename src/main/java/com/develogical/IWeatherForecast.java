package com.develogical;

import com.weather.*;

import java.util.HashMap;

public interface IWeatherForecast {

    public Forecast getResult(Region region, Day day);

}
