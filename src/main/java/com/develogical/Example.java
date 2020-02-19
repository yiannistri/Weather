package com.develogical;

import com.weather.Day;
import com.weather.Forecast;
import com.weather.Region;

import java.time.Clock;

public class Example {
    public static void main(String[] args) {
        ForecasterClient forecaster = new CachingForecasterClient(new ForecasterAdaptor(), 3, () -> Clock.systemUTC().instant());
        doit(forecaster);
        doit(forecaster);
        doit(forecaster);
        System.out.println("And now more slowly ...");
        doit(new ForecasterAdaptor());
        doit(new ForecasterAdaptor());
        doit(new ForecasterAdaptor());
    }

    private static void doit(ForecasterClient forecaster) {
        Forecast londonForecast = forecaster.forecastFor(Region.LONDON, Day.MONDAY);

        System.out.println("London outlook: " + londonForecast.summary());
        System.out.println("London temperature: " + londonForecast.temperature());

        Forecast edinburghForecast = forecaster.forecastFor(Region.EDINBURGH, Day.MONDAY);

        System.out.println("Edinburgh outlook: " + edinburghForecast.summary());
        System.out.println("Edinburgh temperature: " + edinburghForecast.temperature());
    }
}
