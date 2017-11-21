package com.bulpros.whatstheweather.models;

import org.json.JSONObject;

import java.util.List;

/**
 * Created by Behrin.Rasimov on 11/20/2017.
 */

public class WeatherDescription {

    private List<JSONObject> weather;

    public List<JSONObject> getWeather() {
        return weather;
    }

    public void setWeather(List<JSONObject> weather) {
        this.weather = weather;
    }
}
