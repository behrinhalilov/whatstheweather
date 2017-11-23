package com.demo.whatstheweather.views;

/**
 * Created by Behrin.Rasimov on 11/20/2017.
 */

public interface WeatherView {

    void fetchSuccessful(Object object);
    void fetchFailed(String message);
}
