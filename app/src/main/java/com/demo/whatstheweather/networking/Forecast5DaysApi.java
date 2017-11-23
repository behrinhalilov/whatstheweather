package com.demo.whatstheweather.networking;

import com.demo.whatstheweather.models.Forecast5Days;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/**
 * Created by Behrin.Rasimov on 11/20/2017.
 */

public interface Forecast5DaysApi {

    @GET("/data/2.5/forecast")
    Call<Forecast5Days> getForecast5Days(@QueryMap Map<String,String> parameters);

}
