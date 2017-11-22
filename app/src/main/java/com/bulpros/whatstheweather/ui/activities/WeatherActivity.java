package com.bulpros.whatstheweather.ui.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
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
import com.bulpros.whatstheweather.services.LocationServiceObserver;
import com.bulpros.whatstheweather.ui.adapters.Forecast16Adapter;
import com.bulpros.whatstheweather.ui.adapters.Forecast5Adapter;
import com.bulpros.whatstheweather.views.WeatherView;
import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class WeatherActivity extends AppCompatActivity implements WeatherView {

    private WeatherPresenter presenter;

    @BindView(R.id.loading_progress_bar)
    ProgressBar loadingProgressBar;

    @BindView(R.id.weather_wind_speed)
    TextView windSpeed;

    @BindView(R.id.weather_degree)
    TextView windDegree;

    @BindView(R.id.pressure)
    TextView pressure;

    @BindView(R.id.current_temperature)
    TextView currentTemp;

    @BindView(R.id.minimum_temperature)
    TextView minTemp;

    @BindView(R.id.maximum_temperature)
    TextView maxTemp;

    @BindView(R.id.weather_description)
    TextView description;

    @BindView(R.id.city_name)
    TextView city;

    @BindView(R.id.current_weather_icon)
    ImageView weatherMainImage;

    @BindView(R.id.forecast_5_days_recycler)
    RecyclerView forecast5DaysRecyclerView;

    @BindView(R.id.forecast_16_days_recycler)
    RecyclerView forecast16DaysRecyclerView;

    private Forecast5Adapter forecast5Adapter;
    private Forecast16Adapter forecast16Adapter;

    /*
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
     */

    private Unbinder viewUnbinder;

    private LocationListener currentLocationListener;
    private BroadcastReceiver locationReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            double lat = intent.getDoubleExtra("lat",0);
            double lng = intent.getDoubleExtra("lng",0);
            fetchData(lat,lng);
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(locationReceiver,new IntentFilter(Constants.ACTION_LOCATION_UPDATE));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewUnbinder = ButterKnife.bind(this);

        getLifecycle().addObserver(new LocationServiceObserver(this));

        presenter = new WeatherPresenter(this);

        forecast5DaysRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        forecast16DaysRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        loadingProgressBar.setVisibility(View.VISIBLE);

        initLocationListener();
    }

    private void requestSingleTimeLocation(LocationListener locationListener) {

        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_COARSE);
        criteria.setPowerRequirement(Criteria.POWER_LOW);
        criteria.setAltitudeRequired(false);
        criteria.setBearingRequired(false);
        criteria.setSpeedRequired(false);
        criteria.setCostAllowed(true);
        criteria.setHorizontalAccuracy(Criteria.ACCURACY_HIGH);
        criteria.setVerticalAccuracy(Criteria.ACCURACY_HIGH);

        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Looper looper = null;

        locationManager.requestSingleUpdate(criteria, locationListener, looper);
    }

    private void initLocationListener() {

        currentLocationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                fetchData(location.getLatitude(),location.getLongitude());
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };

        requestSingleTimeLocation(currentLocationListener);
    }

    public void fetchData(double lat,double lng) {
        loadingProgressBar.setVisibility(View.VISIBLE);
        presenter.attemptFetchData(CurrentWeather.class, lat, lng);
        presenter.attemptFetchData(Forecast5Days.class, lat, lng);
        presenter.attemptFetchData(Forecast16Days.class, lat, lng);
    }

    @Override
    public void fetchSuccessful(Object object) {

        if (object instanceof CurrentWeather) {

            CurrentWeather weather = (CurrentWeather) object;

            city.setText("Weather now in " + weather.getName());
            windSpeed.setText("Wind speed:" + String.valueOf(weather.getWind().getSpeed()) + " km/h");
            windDegree.setText("Degree:" + String.valueOf(weather.getWind().getDeg()) + " '");
            pressure.setText("Pressure:" + String.valueOf(weather.getMain().getPressure()) + " hPa");
            currentTemp.setText("Temp:" + String.valueOf(weather.getMain().getTemp()) + " C'");
            minTemp.setText("Min t:" + String.valueOf(weather.getMain().getTemp_min()) + " C'");
            maxTemp.setText("Max t:" + String.valueOf(weather.getMain().getTemp_max()) + " C'");
            description.setText(weather.getWeather().get(0).getDescription());

            Glide.with(this)
                    .load(Constants.IMAGE_URL + weather.getWeather().get(0).getIcon() + ".png")
                    .into(weatherMainImage);


        } else if (object instanceof Forecast5Days) {
            Forecast5Days forecast5 = (Forecast5Days) object;
            forecast5Adapter = new Forecast5Adapter(this, forecast5);
            forecast5DaysRecyclerView.setAdapter(forecast5Adapter);


        } else if (object instanceof Forecast16Days) {
            Forecast16Days forecast16 = (Forecast16Days) object;
            forecast16Adapter = new Forecast16Adapter(this, forecast16);
            forecast16DaysRecyclerView.setAdapter(forecast16Adapter);
        }

        loadingProgressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void fetchFailed(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        loadingProgressBar.setVisibility(View.INVISIBLE);
    }


    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(locationReceiver);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        viewUnbinder.unbind();
    }
}
