package com.naveen.airqualitymonitoring.repository;

import androidx.lifecycle.LiveData;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;
@androidx.room.Dao
public interface WeatherDao {
    @Insert
    void insert(Weather weather);

    @Update
    void update(Weather weather);

    @Delete
    void delete(Weather weather);

    @Query("DELETE FROM AQI_DATA")
    void deleteAllWeathers();

    @Query("SELECT *\n" +
            "FROM AQI_DATA s1\n" +
            "WHERE Last_Updated = (SELECT MAX(Last_Updated) FROM AQI_DATA s2 WHERE s1.city = s2.city) \n" +
            "ORDER BY city;")
   public LiveData<List<Weather>> getAllCityLatestWeather();

    @Query("SELECT *\n" +
            "FROM AQI_DATA s1\n" +
            "WHERE Last_Updated = (SELECT MAX(Last_Updated) FROM AQI_DATA s2 WHERE s1.city = s2.city) and city = :city\n" +
            "ORDER BY Last_Updated;")
    Weather getLatestWeatherForCity(String city);

    @Query("SELECT *\n"+"FROM AQI_DATA s1\n"+" WHERE city = :city\n"+ "ORDER BY Last_Updated DESC ")
    List<Weather> getLatestCity(String city);

}
