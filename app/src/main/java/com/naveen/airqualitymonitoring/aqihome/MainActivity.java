package com.naveen.airqualitymonitoring.aqihome;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.naveen.airqualitymonitoring.DataSyncService;
import com.naveen.airqualitymonitoring.R;
import com.naveen.airqualitymonitoring.aqicitydetail.CityAQIActivity;
import com.naveen.airqualitymonitoring.aqicitydetail.OnDataClickListner;
import com.naveen.airqualitymonitoring.repository.Weather;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final String INTENT_KEY_ARTICLE = "myJson";
    private DashboardViewModel mDashboardViewModel;

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter1;
    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        setupView();

        mDashboardViewModel = ViewModelProviders.of(this).get(DashboardViewModel.class);

        startWeatherMonitoringService();

        registerForDataUpdate();
    }

    private void registerForDataUpdate() {
        mDashboardViewModel.getAllCourses().observe(this, new Observer<List<Weather>>() {
            @Override
            public void onChanged(List<Weather> weathers) {
                if (null != weathers) {
                    adapter1 = new RecyclerAdapter(MainActivity.this, weathers, new OnDataClickListner() {
                        @Override
                        public void onDataClick(Weather weather) {
                            Gson gson = new Gson();
                            String myJson = gson.toJson(weather);
                            Intent intent = new Intent(getApplicationContext(), CityAQIActivity.class);
                            intent.putExtra(INTENT_KEY_ARTICLE,myJson);
                            startActivity(intent);
                        }
                    });
                    recyclerView.setAdapter(adapter1);
                }
            }
        });
    }

    private void startWeatherMonitoringService() {
        Intent intent = new Intent(MainActivity.this, DataSyncService.class);
        startService(intent);
    }

    private void setupView() {
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
    }
}

