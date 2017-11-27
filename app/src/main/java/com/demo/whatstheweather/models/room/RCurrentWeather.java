package com.demo.whatstheweather.models.room;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by Behrin.Rasimov on 11/23/2017.
 */

@Entity(tableName = "cache_current")
public class RCurrentWeather {

    public RCurrentWeather(int id, String content) {
        this.content = content;
        this.id = id;
    }

    @PrimaryKey
    public int id;

    @ColumnInfo(name = "content")
    private String content;

    @ColumnInfo(name = "lat")
    private double lat;

    @ColumnInfo(name = "lng")
    private double lng;

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
