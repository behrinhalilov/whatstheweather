package com.demo.whatstheweather.networking;

import com.demo.whatstheweather.models.CurrentWeather;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/**
 * Created by Behrin.Rasimov on 11/20/2017.
 */

public interface CurrentWeatherApi {

    @GET("/data/2.5/weather")
    Call<CurrentWeather> getCurrentWeather(@QueryMap Map<String,String> parameters);

}
