package com.bulpros.whatstheweather.models.room;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by Behrin.Rasimov on 11/23/2017.
 */

@Entity(tableName = "cache_forecast16")
public class RForecast16 {

    public RForecast16(int id, String content) {
        this.id = id;
        this.content = content;
    }

    @PrimaryKey
    public int id;

    private String content;

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
