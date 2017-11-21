package com.bulpros.whatstheweather.interactors;

import com.bulpros.whatstheweather.interfaces.IWeatherDataFetchListener;
import com.bulpros.whatstheweather.models.Forecast16Days;
import com.bulpros.whatstheweather.networking.Forecast16DaysApi;
import com.bulpros.whatstheweather.networking.NetworkManager;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Behrin.Rasimov on 11/21/2017.
 */

public class Forecast16DaysInteractor extends BaseInteractor {


    public void fetchData(final IWeatherDataFetchListener listener, double lat, double lng) {

        Map<String, String> params = getParams();

        params.put("lat", String.valueOf(lat));
        params.put("lon", String.valueOf(lng));
        params.put("cnt", "16");

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
