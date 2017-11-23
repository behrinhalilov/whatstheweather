package com.demo.whatstheweather.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.demo.whatstheweather.models.room.RCurrentWeather;
import com.demo.whatstheweather.models.room.RForecast16;
import com.demo.whatstheweather.models.room.RForecast5;

/**
 * Created by Behrin.Rasimov on 11/23/2017.
 */

@Dao
public interface WeatherCacheDao {

    @Query("SELECT * FROM cache_current")
    RCurrentWeather getCurrentWeatherCache();

    @Query("SELECT * FROM cache_forecast5")
    RForecast5 getForecast5Cache();

    @Query("SELECT * FROM cache_forecast16")
    RForecast16 getForecast16Cache();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void saveCurrentWeatherCache(RCurrentWeather currentWeather);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void saveForecast5Cache(RForecast5 forecast5);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void saveForecast16Cache(RForecast16 forecast16);

}
