package com.develogical;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

public class WeatherClientTest {
    @Test
    public void defaultConstructorInitilisesForecaster() {
        WeatherClient underTest = new WeatherClient();
        assert(underTest.forecaster != null);
    }
}