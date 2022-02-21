package com.naveen.airqualitymonitoring.aqihome;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.naveen.airqualitymonitoring.repository.Weather;
import com.naveen.airqualitymonitoring.repository.WeatherRepository;

import java.util.List;

public class DashboardViewModel extends AndroidViewModel {
    private WeatherRepository repository;

    private LiveData<List<Weather>> allWeathers;

    public DashboardViewModel(@NonNull Application application) {
        super(application);
        repository = new WeatherRepository(application);
        allWeathers = repository.getAllWeather();
    }

    public LiveData<List<Weather>> getAllCourses() {
        return allWeathers;
    }
}
