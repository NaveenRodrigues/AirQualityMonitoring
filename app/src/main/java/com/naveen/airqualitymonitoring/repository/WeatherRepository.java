package com.naveen.airqualitymonitoring.repository;

import android.app.Application;
import androidx.lifecycle.LiveData;
import java.util.List;

public class WeatherRepository {
    private LiveData<List<Weather>> mAllCityAqi;

    public WeatherRepository(Application application) {
        WeatherDataBaseClass database = WeatherDataBaseClass.getInstance(application);
        WeatherDao dao = database.WeatherDao();
        mAllCityAqi = dao.getAllCityLatestWeather();
    }

    public LiveData<List<Weather>> getAllWeather() {
        return mAllCityAqi;
    }
}
