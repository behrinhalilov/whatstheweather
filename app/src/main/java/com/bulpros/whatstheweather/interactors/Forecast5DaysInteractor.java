package com.bulpros.whatstheweather.interactors;

import com.bulpros.whatstheweather.interfaces.IWeatherDataFetchListener;
import com.bulpros.whatstheweather.models.Forecast5Days;
import com.bulpros.whatstheweather.networking.Forecast5DaysApi;
import com.bulpros.whatstheweather.networking.NetworkManager;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Behrin.Rasimov on 11/21/2017.
 */

public class Forecast5DaysInteractor extends BaseInteractor {

    public void fetchData(final IWeatherDataFetchListener listener, double lat, double lng) {

        Map<String, String> params = getParams();
        params.put("lat", String.valueOf(lat));
        params.put("lon", String.valueOf(lng));

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
    }
}
