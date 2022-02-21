package com.naveen.airqualitymonitoring;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public class AqiModel {
    @SerializedName("city")
    @Expose
    private String city;

    @SerializedName("aqi")
    @Expose
    private double aqi;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AqiModel)) return false;
        AqiModel aqiModel = (AqiModel) o;
        return getCity().equals(aqiModel.getCity());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCity());
    }

    public String getCity(){
        return city;
    }
    public void setCity(String city){
        this.city = city;
    }

    public double getAqi(){
        return aqi;
    }
    public void setAqi(double aqi){
        this.aqi = aqi;
    }

}

