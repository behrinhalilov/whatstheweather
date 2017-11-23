package com.demo.whatstheweather.interactors;

import com.demo.whatstheweather.database.WeatherCacheRepo;
import com.demo.whatstheweather.interfaces.IOnDbActionCompleted;
import com.demo.whatstheweather.interfaces.IWeatherDataFetchListener;
import com.demo.whatstheweather.models.Forecast16Days;
import com.demo.whatstheweather.networking.Forecast16DaysApi;
import com.demo.whatstheweather.networking.NetworkManager;
import com.google.gson.Gson;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Behrin.Rasimov on 11/21/2017.
 */

public class Forecast16DaysInteractor extends BaseInteractor implements IOnDbActionCompleted {

    private IWeatherDataFetchListener weatherDataFetchListener;

    public void fetchData(final WeatherCacheRepo repo, final IWeatherDataFetchListener listener, double lat, double lng) {

        Map<String, String> params = getParams();
        params.put("lat", String.valueOf(lat));
        params.put("lon", String.valueOf(lng));
        params.put("cnt", "16");
        weatherDataFetchListener = listener;

        Forecast16DaysApi forecast16DaysApi = NetworkManager.instance().create(Forecast16DaysApi.class);
        Call<Forecast16Days> forecast16DaysCall = forecast16DaysApi.getForecast16Days(params);

        if (NetworkManager.hasNetworkAccess()) {

            forecast16DaysCall.enqueue(new Callback<Forecast16Days>() {
                @Override
                public void onResponse(Call<Forecast16Days> call, Response<Forecast16Days> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        repo.saveEntityAsync(WeatherCacheRepo.TYPE_FORECAST_16,response.body());
                        listener.onSuccess(response.body());
                    }
                }

                @Override
                public void onFailure(Call<Forecast16Days> call, Throwable t) {
                    if (t != null) {
                        listener.onFail(t.getMessage());
                    }
                }
            });
        } else {
            repo.getEntityContentAsync(WeatherCacheRepo.TYPE_FORECAST_16,this);
        }
    }

    @Override
    public void onComplete(String queryResult) {
        weatherDataFetchListener.onSuccess(new Gson().fromJson(queryResult,Forecast16Days.class));
    }
}
