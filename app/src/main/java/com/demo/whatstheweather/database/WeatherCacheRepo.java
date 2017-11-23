package com.demo.whatstheweather.database;

import android.os.AsyncTask;

import com.demo.whatstheweather.ApplicationClass;
import com.demo.whatstheweather.interfaces.IOnDbActionCompleted;
import com.demo.whatstheweather.models.room.RCurrentWeather;
import com.demo.whatstheweather.models.room.RForecast16;
import com.demo.whatstheweather.models.room.RForecast5;
import com.google.gson.Gson;

/**
 * Created by Behrin.Rasimov on 11/23/2017.
 */

public class WeatherCacheRepo {

    public static final int TYPE_CURRENT = 1;
    public static final int TYPE_FORECAST_5 = 2;
    public static final int TYPE_FORECAST_16 = 3;

    public void getEntityContentAsync(final int type, final IOnDbActionCompleted actionCompletedListener) {

        new AsyncTask<Integer,Void,Void>() {

            private String queryResult;

            @Override
            protected Void doInBackground(Integer... params) {

                if (params[0] == TYPE_CURRENT) {

                    queryResult = ApplicationClass
                            .getInstance()
                            .getCacheDb()
                            .weatherCacheDao()
                            .getCurrentWeatherCache()
                            .getContent();
                }

                if (params[0] == TYPE_FORECAST_5) {

                    queryResult = ApplicationClass
                            .getInstance()
                            .getCacheDb()
                            .weatherCacheDao()
                            .getForecast5Cache()
                            .getContent();

                }

                if (params[0] == TYPE_FORECAST_16) {

                    queryResult = ApplicationClass
                            .getInstance()
                            .getCacheDb()
                            .weatherCacheDao()
                            .getForecast16Cache()
                            .getContent();
                }

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                actionCompletedListener.onComplete(queryResult);
            }
        }.execute(type);

    }

    public void saveEntityAsync(final int type,final Object model) {
        new AsyncTask<Void,Void,Void>() {

            private WeatherCacheDao dao;

            @Override
            protected Void doInBackground(Void... params) {

                dao = ApplicationClass.getInstance().getCacheDb().weatherCacheDao();

                switch (type) {
                    case TYPE_CURRENT:
                        RCurrentWeather dbModelCurrent = new RCurrentWeather(0,new Gson().toJson(model));
                        dao.saveCurrentWeatherCache(dbModelCurrent); break;
                    case TYPE_FORECAST_5:
                        RForecast5 dbModelForecast5 = new RForecast5(0,new Gson().toJson(model));
                        dao.saveForecast5Cache(dbModelForecast5); break;
                    case TYPE_FORECAST_16:
                        RForecast16 dbModelForecast16 = new RForecast16(0,new Gson().toJson(model));
                        dao.saveForecast16Cache(dbModelForecast16); break;

                    default: break;
                }

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);

            }
        }.execute();
    }

}
