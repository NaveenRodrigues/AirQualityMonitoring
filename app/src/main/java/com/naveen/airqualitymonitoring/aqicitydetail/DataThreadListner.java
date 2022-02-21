package com.naveen.airqualitymonitoring.aqicitydetail;

import com.naveen.airqualitymonitoring.repository.Weather;

import java.util.List;

public interface DataThreadListner {
    void onDataReceived(List<Weather> weather);
}
