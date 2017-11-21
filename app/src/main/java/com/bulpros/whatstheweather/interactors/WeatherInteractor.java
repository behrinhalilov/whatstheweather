package com.bulpros.whatstheweather.interactors;

import com.bulpros.whatstheweather.helpers.Constants;
import com.bulpros.whatstheweather.interfaces.IWeatherDataFetchListener;
import com.bulpros.whatstheweather.models.CurrentWeather;
import com.bulpros.whatstheweather.models.Forecast16Days;
import com.bulpros.whatstheweather.models.Forecast5Days;
import com.bulpros.whatstheweather.networking.CurrentWeatherApi;
import com.bulpros.whatstheweather.networking.Forecast16DaysApi;
import com.bulpros.whatstheweather.networking.Forecast5DaysApi;
import com.bulpros.whatstheweather.networking.NetworkManager;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Behrin.Rasimov on 11/20/2017.
 */

public class WeatherInteractor {

    public <T> void fetchData(final IWeatherDataFetchListener listener, Class<T> type, double lat, double lng) {

        Map<String,String> params = new HashMap<>();
        params.put("lat",String.valueOf(lat));
        params.put("lon",String.valueOf(lng));
        params.put("APPID", Constants.APPID);
        params.put("units","metric");
        params.put("lang","en");

        if (type == CurrentWeather.class) {

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
        } else if (type == Forecast5Days.class) {
            Forecast5DaysApi forecast5DaysApi = NetworkManager.instance().create(Forecast5DaysApi.class);
            Call<Forecast5Days> forecast5DaysCall = forecast5DaysApi.getForecast5Days(params);

            forecast5DaysCall.enqueue(new Callback<Forecast5Days>() {
                @Override
                public void onResponse(Call<Forecast5Days> call, Response<Forecast5Days> response) {
                    if (response.isSuccessful() && response.body() != null) {
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

            params.put("cnt","16");

            Forecast16DaysApi forecast16DaysApi = NetworkManager.instance().create(Forecast16DaysApi.class);
            Call<Forecast16Days> forecast16DaysCall = forecast16DaysApi.getForecast16Days(params);

            forecast16DaysCall.enqueue(new Callback<Forecast16Days>() {
                @Override
                public void onResponse(Call<Forecast16Days> call, Response<Forecast16Days> response) {
                    if (response.isSuccessful() && response.body() != null) {
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
        }
    }

}
