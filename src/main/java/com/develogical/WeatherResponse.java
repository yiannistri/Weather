package com.develogical;

public class WeatherResponse {
    private int _temperature;
    private String _summary;

    public WeatherResponse(int temperature, String summary){
        this._temperature = temperature;
        this._summary = summary;
    }

    public String getSummary() {
        return _summary;
    }

    public int getTemperature() {
        return _temperature;
    }
}
