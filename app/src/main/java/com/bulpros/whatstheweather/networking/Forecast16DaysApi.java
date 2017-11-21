package com.bulpros.whatstheweather.networking;


import com.bulpros.whatstheweather.models.Forecast16Days;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/**
 * Created by Behrin.Rasimov on 11/20/2017.
 */

public interface Forecast16DaysApi {

    @GET("/data/2.5/forecast/daily")
    Call<Forecast16Days> getForecast16Days(@QueryMap Map<String,String> parameters);

}
