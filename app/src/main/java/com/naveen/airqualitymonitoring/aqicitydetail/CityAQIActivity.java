package com.naveen.airqualitymonitoring.aqicitydetail;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.google.gson.Gson;
import com.naveen.airqualitymonitoring.R;
import com.naveen.airqualitymonitoring.aqihome.MainActivity;
import com.naveen.airqualitymonitoring.repository.DetailAQIData;
import com.naveen.airqualitymonitoring.repository.Weather;
import com.naveen.airqualitymonitoring.repository.WeatherDao;
import com.naveen.airqualitymonitoring.repository.WeatherDataBaseClass;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class CityAQIActivity extends AppCompatActivity {
    Context context;
    private final WeatherDao dao;

    public CityAQIActivity() {
        super(R.layout.activity_city_aqiactivity);
        WeatherDataBaseClass database = WeatherDataBaseClass.getInstance(context);
        dao = database.WeatherDao();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_aqiactivity);
        Intent intent = getIntent();
        String str = intent.getStringExtra(MainActivity.INTENT_KEY_ARTICLE);
        if (str != null) {
            Gson gson = new Gson();
            Weather cdata = gson.fromJson(str, Weather.class);


            DetailAQIData detailAQIData = new DetailAQIData();
            detailAQIData.getCityData(cdata.getCity(), dao, new DataThreadListner() {
                @Override
                public void onDataReceived(List<Weather> weather) {
                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            List<Weather> weatherDb = weather;
                            drawLineChart(weatherDb);
                    }
                });
                }
            });
        }
    }

    private void drawLineChart(List<Weather> weathers) {

        LineChart lineChart = findViewById(R.id.lineChart);
        SimpleDateFormat formatter = new SimpleDateFormat("h:mm a");
        List<Entry> lineEntries = getDataSet(weathers);
        LineDataSet lineDataSet = new LineDataSet(lineEntries, "AQI");
        lineDataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
        lineDataSet.setLineWidth(5);
        lineDataSet.setDrawValues(true);
        lineDataSet.setColor(Color.BLUE);
        lineDataSet.setDrawHighlightIndicators(true);
        lineDataSet.setHighlightEnabled(true);
        lineDataSet.setHighLightColor(Color.BLUE);
        lineDataSet.setValueTextSize(12);
        lineDataSet.setValueTextColor(Color.DKGRAY);
        lineDataSet.setMode(LineDataSet.Mode.LINEAR);

        LineData lineData = new LineData(lineDataSet);
        lineChart.getDescription().setText(weathers.get(0).getCity());
        lineChart.getDescription().setTextSize(12);
        lineChart.getDescription().setEnabled(true);
        lineChart.animateY(1000);
        lineChart.setData(lineData);

        float high = scaleData(weathers.get(0).getTime());
        float low = scaleData(weathers.get(weathers.size() - 1).getTime());
        float granularity = (high - low) / weathers.size();

        // Setup X Axis
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.TOP);
        xAxis.setGranularityEnabled(true);
        xAxis.setGranularity(granularity);
        xAxis.setXOffset(1f);
        xAxis.setLabelCount(25);
        xAxis.setAxisMinimum(low);
        xAxis.setAxisMaximum(high);
        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getAxisLabel(float value, AxisBase axis) {
                return formatter.format(new Date((long) value));
            }
        });

        // Setup Y Axis
        YAxis yAxis = lineChart.getAxisLeft();
        yAxis.setAxisMinimum(0);
        yAxis.setAxisMaximum(500);
        yAxis.setGranularity(50f);

        //lineChart.getAxisLeft().setCenterAxisLabels(true);
        lineChart.getAxisLeft().setValueFormatter(new ValueFormatter() {
            @Override
            public String getAxisLabel(float value, AxisBase axis) {
                String label = "";
                if(value <= 50.00 ){
                    label = getString(R.string.good);
                }
                else if(value <= 100.00 ){
                    label = getString(R.string.satisfactory);
                }
                else if(value <= 200.00 ) {
                    label = getString(R.string.moderate);
                }
                else if (value <= 300.00 ){
                    label = getString(R.string.poor);
                }
                else if (value <= 400.00 ) {
                    label = getString(R.string.very_poor);
                }
                else if (value <= 500.00 ) {
                    label = getString(R.string.severe);
                }

                return label;
            }
        });
        lineChart.getAxisRight().setEnabled(false);
        lineChart.invalidate();
    }

    private float scaleData(long time) {
        return (float) time;
    }

    private List<Entry> getDataSet(List<Weather> weathers) {
        List<Entry> lineEntries = new ArrayList<>();

        for(Weather data : weathers){
            float scaledData = scaleData(data.getTime());

           lineEntries.add(new Entry(scaledData, (float) data.getAqi()));
        }
        Collections.reverse(lineEntries);

        return lineEntries;
    }
}


