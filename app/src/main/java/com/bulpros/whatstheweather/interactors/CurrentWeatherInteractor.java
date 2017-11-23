package com.bulpros.whatstheweather.interactors;

import com.bulpros.whatstheweather.database.WeatherCacheRepo;
import com.bulpros.whatstheweather.interfaces.IOnDbActionCompleted;
import com.bulpros.whatstheweather.interfaces.IWeatherDataFetchListener;
import com.bulpros.whatstheweather.models.CurrentWeather;
import com.bulpros.whatstheweather.networking.CurrentWeatherApi;
import com.bulpros.whatstheweather.networking.NetworkManager;
import com.google.gson.Gson;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Behrin.Rasimov on 11/20/2017.
 */

public class CurrentWeatherInteractor extends BaseInteractor implements IOnDbActionCompleted {

    private IWeatherDataFetchListener weatherDataFetchListener;
    
    public void fetchData(final WeatherCacheRepo repo, final IWeatherDataFetchListener listener, double lat, double lng) {

        Map<String, String> params = getParams();
        params.put("lat", String.valueOf(lat));
        params.put("lon", String.valueOf(lng));
        weatherDataFetchListener = listener;

        CurrentWeatherApi currentWeatherApi = NetworkManager.instance().create(CurrentWeatherApi.class);
        Call<CurrentWeather> currentWeatherCall = currentWeatherApi.getCurrentWeather(params);

        if (NetworkManager.hasNetworkAccess()) {

            currentWeatherCall.enqueue(new Callback<CurrentWeather>() {
                @Override
                public void onResponse(Call<CurrentWeather> call, Response<CurrentWeather> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        repo.saveEntityAsync(WeatherCacheRepo.TYPE_CURRENT,response.body());
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
        } else {
            repo.getEntityContentAsync(WeatherCacheRepo.TYPE_CURRENT,this);
        }
    }

    @Override
    public void onComplete(String queryResult) {
        weatherDataFetchListener.onSuccess(new Gson().fromJson(queryResult,CurrentWeather.class));
    }
}
