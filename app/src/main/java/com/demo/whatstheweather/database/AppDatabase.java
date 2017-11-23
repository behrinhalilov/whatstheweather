package com.demo.whatstheweather.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.demo.whatstheweather.models.room.RCurrentWeather;
import com.demo.whatstheweather.models.room.RForecast16;
import com.demo.whatstheweather.models.room.RForecast5;

/**
 * Created by Behrin.Rasimov on 11/23/2017.
 */

@Database(entities = {RCurrentWeather.class, RForecast5.class, RForecast16.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract WeatherCacheDao weatherCacheDao();
}
