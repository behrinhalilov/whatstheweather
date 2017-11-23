package com.demo.whatstheweather.models;

import java.util.List;

/**
 * Created by Behrin.Rasimov on 11/20/2017.
 */

public class Forecast5Days {

    private List<CurrentWeather> list;

    public List<CurrentWeather> getList() {
        return list;
    }

    public void setList(List<CurrentWeather> list) {
        this.list = list;
    }
}
