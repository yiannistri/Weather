package com.develogical;

import com.weather.*;

public interface IWeatherForecaster {

    WeatherForecast getResult(Region region, Day day);

}

