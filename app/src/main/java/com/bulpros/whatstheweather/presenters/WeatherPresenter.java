package com.bulpros.whatstheweather.presenters;

import com.bulpros.whatstheweather.interactors.WeatherInteractor;
import com.bulpros.whatstheweather.interfaces.IWeatherDataFetchListener;
import com.bulpros.whatstheweather.views.WeatherView;

/**
 * Created by Behrin.Rasimov on 11/20/2017.
 */

public class WeatherPresenter implements IWeatherDataFetchListener {

    private WeatherInteractor interactor;
    private WeatherView view;

    public WeatherPresenter(WeatherView view) {
        this.view = view;
        interactor = new WeatherInteractor();
    }

    public <T> void attemptFetchData(Class<T> type,double lat, double lng) {
        interactor.fetchData(this,type,lat,lng);
    }

    @Override
    public void onSuccess(Object object) {
        view.fetchSuccessful(object);
    }

    @Override
    public void onFail(String message) {
        view.fetchFailed(message);
    }
}
