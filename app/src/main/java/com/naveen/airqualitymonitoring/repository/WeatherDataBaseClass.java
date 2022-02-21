package com.naveen.airqualitymonitoring.repository;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Weather.class}, version = 1)
public abstract class WeatherDataBaseClass extends RoomDatabase {
    private static final String DB_NAME = "weather_db";

    private static WeatherDataBaseClass instance;

    public abstract WeatherDao WeatherDao();

    public static synchronized WeatherDataBaseClass getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context, WeatherDataBaseClass.class, DB_NAME)
                .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        PopulateDbAsyncTask(WeatherDataBaseClass instance) {
            WeatherDao dao = instance.WeatherDao();
        }
        @Override
        protected Void doInBackground(Void... voids) {
            return null;
        }
    }
}
