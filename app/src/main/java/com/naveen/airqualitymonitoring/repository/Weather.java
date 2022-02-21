package com.naveen.airqualitymonitoring.repository;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "AQI_DATA")
public class Weather {
    @PrimaryKey(autoGenerate = true)
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public double getAqi() {
        return aqi;
    }

    public void setAqi(double aqi) {
        this.aqi = aqi;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    @ColumnInfo(name = "City")
    private String city;

    @ColumnInfo(name = "AQI")
    private double aqi;

    @ColumnInfo(name = "Last_Updated")
    private long time;
}
