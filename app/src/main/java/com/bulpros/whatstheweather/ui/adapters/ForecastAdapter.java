package com.bulpros.whatstheweather.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Behrin.Rasimov on 11/21/2017.
 */

public class ForecastAdapter extends RecyclerView.Adapter <RecyclerView.ViewHolder> {

    public static final int FORECAST_5_DAYS = 0;
    public static final int FORECAST_16_DAYS = 1;
    private int viewType = -1;
    private Object forecastData;

    public ForecastAdapter(int viewType, Object forecastData) {
        this.viewType = viewType;
        this.forecastData = forecastData;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType != -1) {
            switch (viewType) {
                case FORECAST_5_DAYS: return new Forecast5DaysViewHolder(parent);
                case FORECAST_16_DAYS: return new Forecast16DaysViewHolder(parent);
                default: break;
            }
        }

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (viewType != -1) {
            switch (viewType) {
                case FORECAST_5_DAYS: ;break;
                case FORECAST_16_DAYS: ;break;
                default: break;
            }
        }

    }

    @Override
    public int getItemCount() {
        switch (viewType) {
            case FORECAST_5_DAYS: ; break;
            case FORECAST_16_DAYS: ;break;
        }
        return 0;
    }

    private class Forecast5DaysViewHolder extends RecyclerView.ViewHolder {

        Forecast5DaysViewHolder(View itemView) {
            super(itemView);
        }
    }

    private class Forecast16DaysViewHolder extends RecyclerView.ViewHolder {

        Forecast16DaysViewHolder(View itemView) {
            super(itemView);
        }
    }
}
