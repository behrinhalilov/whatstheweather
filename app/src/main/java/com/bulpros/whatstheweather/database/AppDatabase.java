package com.bulpros.whatstheweather.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.bulpros.whatstheweather.models.room.RCurrentWeather;
import com.bulpros.whatstheweather.models.room.RForecast16;
import com.bulpros.whatstheweather.models.room.RForecast5;

/**
 * Created by Behrin.Rasimov on 11/23/2017.
 */

@Database(entities = {RCurrentWeather.class, RForecast5.class, RForecast16.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract WeatherCacheDao weatherCacheDao();
}
