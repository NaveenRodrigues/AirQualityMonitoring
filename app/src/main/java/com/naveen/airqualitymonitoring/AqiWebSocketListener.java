package com.naveen.airqualitymonitoring;

import android.content.Context;
import com.naveen.airqualitymonitoring.repository.Weather;
import com.naveen.airqualitymonitoring.repository.WeatherDao;
import com.naveen.airqualitymonitoring.repository.WeatherDataBaseClass;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.math.RoundingMode;

import okhttp3.WebSocket;
import okhttp3.WebSocketListener;

public class AqiWebSocketListener extends WebSocketListener {
    private final WeatherDao dao;

    public AqiWebSocketListener(Context context) {
        WeatherDataBaseClass database = WeatherDataBaseClass.getInstance(context);
        dao = database.WeatherDao();
    }

    @Override
    public void onMessage(WebSocket webSocket, String message) {
        parseAndUpdateDB(message);
    }

    private void parseAndUpdateDB(String message) {
        if (null != message) {
            try {
                JSONArray jsonArray = new JSONArray(message);

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject aqiJson = jsonArray.getJSONObject(i);
                    AqiModel model = parseAqiJson(aqiJson);
                    if (null != model) {
                        updateModelToDb(model);
                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void updateModelToDb(AqiModel model) {
        Weather weatherDb =
                dao.getLatestWeatherForCity(model.getCity());

        if (null == weatherDb || weatherDb.getAqi() != model.getAqi()) {
            long currentTimeMillis = System.currentTimeMillis();
            Weather weather = new Weather();
            weather.setAqi(model.getAqi());
            weather.setCity(model.getCity());
            weather.setTime(currentTimeMillis);
            dao.insert(weather);

            //Adding delay to ease up database query for UI
            try {
                Thread.sleep(30000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private AqiModel parseAqiJson(JSONObject aqiJson) {
        AqiModel model = null;

        try {
            model = new AqiModel();
            model.setCity(aqiJson.getString("city"));
            model.setAqi(round(aqiJson.getDouble("aqi"), 2));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return model;
    }

    private static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

}

