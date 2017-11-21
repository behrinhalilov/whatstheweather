package com.bulpros.whatstheweather.models;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by Behrin.Rasimov on 11/20/2017.
 */

public class CurrentWeather {

    private long id;
    private String name;
    private Wind wind;
    private WeatherMain main;
    private List<WeatherDescription> weather;

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

    public List<WeatherDescription> getWeather() {
        return weather;
    }

    public void setWeather(List<WeatherDescription> weather) {
        this.weather = weather;
    }
}
