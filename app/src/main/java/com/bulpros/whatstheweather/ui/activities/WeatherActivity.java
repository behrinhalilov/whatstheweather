package com.bulpros.whatstheweather.ui.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bulpros.whatstheweather.R;
import com.bulpros.whatstheweather.helpers.Constants;
import com.bulpros.whatstheweather.models.CurrentWeather;
import com.bulpros.whatstheweather.models.Forecast16Days;
import com.bulpros.whatstheweather.models.Forecast5Days;
import com.bulpros.whatstheweather.presenters.WeatherPresenter;
import com.bulpros.whatstheweather.ui.adapters.Forecast16Adapter;
import com.bulpros.whatstheweather.ui.adapters.Forecast5Adapter;
import com.bulpros.whatstheweather.views.WeatherView;
import com.bumptech.glide.Glide;

public class WeatherActivity extends AppCompatActivity implements WeatherView {

    private WeatherPresenter presenter;
    private ProgressBar loadingProgressBar;
    private TextView windSpeed;
    private TextView windDegree;
    private TextView pressure;
    private TextView currentTemp;
    private TextView minTemp;
    private TextView maxTemp;
    private TextView description;
    private TextView city;
    private ImageView weatherMainImage;
    private Forecast5Adapter forecast5Adapter;
    private Forecast16Adapter forecast16Adapter;
    private RecyclerView forecast5DaysRecyclerView;
    private RecyclerView forecast16DaysRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        presenter = new WeatherPresenter(this);

        loadingProgressBar = findViewById(R.id.loading_progress_bar);
        windSpeed = findViewById(R.id.weather_wind_speed);
        windDegree = findViewById(R.id.weather_degree);
        pressure = findViewById(R.id.pressure);
        currentTemp = findViewById(R.id.current_temperature);
        minTemp = findViewById(R.id.minimum_temperature);
        maxTemp = findViewById(R.id.maximum_temperature);
        description = findViewById(R.id.weather_description);
        city = findViewById(R.id.city_name);
        weatherMainImage = findViewById(R.id.current_weather_icon);

        forecast5DaysRecyclerView = findViewById(R.id.forecast_5_days_recycler);
        forecast16DaysRecyclerView = findViewById(R.id.forecast_16_days_recycler);

        forecast5DaysRecyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false));
        forecast16DaysRecyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false));

        loadingProgressBar.setVisibility(View.VISIBLE);

        presenter.attemptFetchData(CurrentWeather.class,42.6977,23.3216);
        presenter.attemptFetchData(Forecast5Days.class,42.6977,23.3216);
        presenter.attemptFetchData(Forecast16Days.class,42.6977,23.3216);

    }

    @Override
    public void fetchSuccessful(Object object) {

        if (object instanceof CurrentWeather) {

            CurrentWeather weather = (CurrentWeather) object;

            city.setText("Weather now in "+weather.getName());
            windSpeed.setText("Wind speed:"+String.valueOf(weather.getWind().getSpeed())+" km/h");
            windDegree.setText("Degree:"+String.valueOf(weather.getWind().getDeg())+" '");
            pressure.setText("Pressure:"+String.valueOf(weather.getMain().getPressure())+" hPa");
            currentTemp.setText("Temp:"+String.valueOf(weather.getMain().getTemp())+" C'");
            minTemp.setText("Min t:"+String.valueOf(weather.getMain().getTemp_min())+" C'");
            maxTemp.setText("Max t:"+String.valueOf(weather.getMain().getTemp_max())+" C'");

            Glide.with(this)
                    .load(Constants.IMAGE_URL+weather.getWeather().get(0).getIcon()+".png")
                    .into(weatherMainImage);



        } else if(object instanceof Forecast5Days) {
            Forecast5Days forecast5 = (Forecast5Days) object;
            forecast5Adapter = new Forecast5Adapter(this,forecast5);
            forecast5DaysRecyclerView.setAdapter(forecast5Adapter);



        } else if(object instanceof Forecast16Days){
            Forecast16Days forecast16 = (Forecast16Days) object;
            forecast16Adapter = new Forecast16Adapter(this,forecast16);
            forecast16DaysRecyclerView.setAdapter(forecast16Adapter);
        }

        loadingProgressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void fetchFailed(String message) {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
        loadingProgressBar.setVisibility(View.INVISIBLE);
    }
}
