package com.bulpros.whatstheweather.presenters;

import com.bulpros.whatstheweather.database.WeatherCacheRepo;
import com.bulpros.whatstheweather.interactors.CurrentWeatherInteractor;
import com.bulpros.whatstheweather.interactors.Forecast16DaysInteractor;
import com.bulpros.whatstheweather.interactors.Forecast5DaysInteractor;
import com.bulpros.whatstheweather.interfaces.IWeatherDataFetchListener;
import com.bulpros.whatstheweather.models.CurrentWeather;
import com.bulpros.whatstheweather.models.Forecast5Days;
import com.bulpros.whatstheweather.views.WeatherView;

/**
 * Created by Behrin.Rasimov on 11/20/2017.
 */

public class WeatherPresenter implements IWeatherDataFetchListener {

    private WeatherView view;
    private WeatherCacheRepo repo;

    public WeatherPresenter(WeatherView view) {
        this.view = view;
        repo = new WeatherCacheRepo();
    }

    public <T> void attemptFetchData(Class<T> type,double lat, double lng) {

        if (type == CurrentWeather.class) {
            new CurrentWeatherInteractor().fetchData(repo,this,lat,lng);
        } else if (type == Forecast5Days.class) {
            new Forecast5DaysInteractor().fetchData(repo,this,lat,lng);
        } else {
            new Forecast16DaysInteractor().fetchData(repo,this,lat,lng);
        }

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
