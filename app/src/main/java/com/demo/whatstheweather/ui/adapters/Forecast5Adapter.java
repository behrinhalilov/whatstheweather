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
import com.demo.whatstheweather.models.Forecast5Days;
import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Behrin.Rasimov on 11/21/2017.
 */

public class Forecast5Adapter extends RecyclerView.Adapter<Forecast5Adapter.Forecast5DaysViewHolder> {


    private Forecast5Days forecastData;
    private Context context;

    public Forecast5Adapter(Context context, Forecast5Days forecast) {
        this.context = context;
        this.forecastData = forecast;
    }

    @Override
    public Forecast5Adapter.Forecast5DaysViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.forecast_5days_view, parent, false);
        return new Forecast5DaysViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(Forecast5Adapter.Forecast5DaysViewHolder holder, int position) {
        populateForecast5(holder, position);
    }

    private void populateForecast5(Forecast5Adapter.Forecast5DaysViewHolder holder, int position) {
        Glide.with(context)
                .load(Constants.IMAGE_URL + (forecastData).getList().get(position).getWeather().get(0).getIcon() + ".png")
                .into(holder.image);
        String date = (forecastData).getList().get(position).getDt_txt().replace(" ", "\n");
        String mDate = date.replace("-","/");
        holder.date.setText(mDate);
        holder.temp.setText((int) (forecastData).getList().get(position).getMain().getTemp() + " C'");
    }

    @Override
    public int getItemCount() {
        return forecastData.getList().size();
    }

    class Forecast5DaysViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.forecast_5_days_image)
        ImageView image;

        @BindView(R.id.forecast_5days_date)
        TextView date;

        @BindView(R.id.forecast_5days_temp)
        TextView temp;

        Forecast5DaysViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
