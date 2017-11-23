package com.bulpros.whatstheweather.interactors;

import com.bulpros.whatstheweather.database.WeatherCacheRepo;
import com.bulpros.whatstheweather.interfaces.IOnDbActionCompleted;
import com.bulpros.whatstheweather.interfaces.IWeatherDataFetchListener;
import com.bulpros.whatstheweather.models.Forecast5Days;
import com.bulpros.whatstheweather.networking.Forecast5DaysApi;
import com.bulpros.whatstheweather.networking.NetworkManager;
import com.google.gson.Gson;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Behrin.Rasimov on 11/21/2017.
 */

public class Forecast5DaysInteractor extends BaseInteractor implements IOnDbActionCompleted {

    private IWeatherDataFetchListener weatherDataFetchListener;

    public void fetchData(final WeatherCacheRepo repo, final IWeatherDataFetchListener listener, double lat, double lng) {

        Map<String, String> params = getParams();
        params.put("lat", String.valueOf(lat));
        params.put("lon", String.valueOf(lng));
        weatherDataFetchListener = listener;

        Forecast5DaysApi forecast5DaysApi = NetworkManager.instance().create(Forecast5DaysApi.class);
        Call<Forecast5Days> forecast5DaysCall = forecast5DaysApi.getForecast5Days(params);

        if (NetworkManager.hasNetworkAccess()) {

            forecast5DaysCall.enqueue(new Callback<Forecast5Days>() {
                @Override
                public void onResponse(Call<Forecast5Days> call, Response<Forecast5Days> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        repo.saveEntityAsync(WeatherCacheRepo.TYPE_FORECAST_5,response.body());
                        listener.onSuccess(response.body());
                    }
                }

                @Override
                public void onFailure(Call<Forecast5Days> call, Throwable t) {
                    if (t != null) {
                        listener.onFail(t.getMessage());
                    }
                }
            });
        } else {
            repo.getEntityContentAsync(WeatherCacheRepo.TYPE_FORECAST_5,this);
        }
    }

    @Override
    public void onComplete(String queryResult) {
        weatherDataFetchListener.onSuccess(new Gson().fromJson(queryResult,Forecast5Days.class));
    }
}
