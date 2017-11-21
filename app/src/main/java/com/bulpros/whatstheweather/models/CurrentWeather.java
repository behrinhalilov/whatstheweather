package com.bulpros.whatstheweather.models;

/**
 * Created by Behrin.Rasimov on 11/20/2017.
 */

public class CurrentWeather {

    private long id;
    private String name;
    private Wind wind;
    private WeatherMain main;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Wind getWind() {
        return wind;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }

    public WeatherMain getMain() {
        return main;
    }

    public void setMain(WeatherMain main) {
        this.main = main;
    }
}
