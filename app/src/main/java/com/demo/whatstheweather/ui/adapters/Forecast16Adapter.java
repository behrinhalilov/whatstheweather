package com.demo.whatstheweather.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.demo.whatstheweather.R;
import com.demo.whatstheweather.helpers.Constants;
import com.demo.whatstheweather.models.Forecast16Days;
import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Behrin.Rasimov on 11/21/2017.
 */

public class Forecast16Adapter extends RecyclerView.Adapter<Forecast16Adapter.Forecast16DaysViewHolder> {


    private Forecast16Days forecastData;
    private Context context;

    public Forecast16Adapter(Context context, Forecast16Days forecast) {
        this.context = context;
        this.forecastData = forecast;
    }

    @Override
    public Forecast16Adapter.Forecast16DaysViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.forecast_16days_view, parent, false);
        return new Forecast16DaysViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(Forecast16Adapter.Forecast16DaysViewHolder holder, int position) {
        populateForecast16(holder, position);
    }

    private void populateForecast16(Forecast16Adapter.Forecast16DaysViewHolder holder, int position) {
        Glide.with(context)
                .load(Constants.IMAGE_URL + (forecastData).getList().get(position).getWeather().get(0).getIcon() + ".png")
                .into(holder.image);
        String date = (forecastData).getList().get(position).getWeather().get(0).getDescription();
        holder.date.setText(date);
    }

    @Override
    public int getItemCount() {
        return forecastData.getList().size();
    }

    class Forecast16DaysViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.forecast_16_days_image)
        ImageView image;

        @BindView(R.id.forecast_16days_date)
        TextView date;

        Forecast16DaysViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
