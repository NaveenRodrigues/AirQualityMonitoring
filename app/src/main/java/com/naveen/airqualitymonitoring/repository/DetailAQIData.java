package com.naveen.airqualitymonitoring.repository;

import com.naveen.airqualitymonitoring.aqicitydetail.DataThreadListner;
import java.util.List;

public class DetailAQIData {
    List<Weather> weatherDb;

  public void getCityData(String city, WeatherDao dao, DataThreadListner listner){
      new Thread(new Runnable() {
          @Override
          public void run() {
               weatherDb =
                      dao.getLatestCity(city);
               listner.onDataReceived(weatherDb);
          }
      }).start();
  }
}
