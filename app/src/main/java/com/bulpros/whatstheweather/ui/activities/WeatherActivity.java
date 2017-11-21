package com.bulpros.whatstheweather.ui.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bulpros.whatstheweather.R;
import com.bulpros.whatstheweather.models.CurrentWeather;
import com.bulpros.whatstheweather.models.Forecast16Days;
import com.bulpros.whatstheweather.models.Forecast5Days;
import com.bulpros.whatstheweather.presenters.WeatherPresenter;
import com.bulpros.whatstheweather.views.WeatherView;

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

        } else if(object instanceof Forecast5Days) {

        } else {

        }

        loadingProgressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void fetchFailed(String message) {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
        loadingProgressBar.setVisibility(View.INVISIBLE);
    }
}
