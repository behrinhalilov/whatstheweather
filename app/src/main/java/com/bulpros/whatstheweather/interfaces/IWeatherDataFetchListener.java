package com.bulpros.whatstheweather.interfaces;

/**
 * Created by Behrin.Rasimov on 11/20/2017.
 */

public interface IWeatherDataFetchListener {

    void onSuccess(Object object);
    void onFail(String message);
}
