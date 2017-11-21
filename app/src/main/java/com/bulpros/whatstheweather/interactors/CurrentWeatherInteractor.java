package com.bulpros.whatstheweather.interactors;

import com.bulpros.whatstheweather.interfaces.IWeatherDataFetchListener;
import com.bulpros.whatstheweather.models.CurrentWeather;
import com.bulpros.whatstheweather.networking.CurrentWeatherApi;
import com.bulpros.whatstheweather.networking.NetworkManager;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Behrin.Rasimov on 11/20/2017.
 */

public class CurrentWeatherInteractor extends BaseInteractor {

    public void fetchData(final IWeatherDataFetchListener listener, double lat, double lng) {

        Map<String,String> params = getParams();
        params.put("lat",String.valueOf(lat));
        params.put("lon",String.valueOf(lng));

        CurrentWeatherApi currentWeatherApi = NetworkManager.instance().create(CurrentWeatherApi.class);
        Call<CurrentWeather> currentWeatherCall = currentWeatherApi.getCurrentWeather(params);

        currentWeatherCall.enqueue(new Callback<CurrentWeather>() {
            @Override
            public void onResponse(Call<CurrentWeather> call, Response<CurrentWeather> response) {
                if (response.isSuccessful() && response.body() != null) {
                    listener.onSuccess(response.body());
                }
            }

            @Override
            public void onFailure(Call<CurrentWeather> call, Throwable t) {
                if (t != null) {
                    listener.onFail(t.getMessage());
                }
            }
        });

    }
}
