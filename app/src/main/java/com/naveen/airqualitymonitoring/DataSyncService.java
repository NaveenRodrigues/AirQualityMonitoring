package com.naveen.airqualitymonitoring;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.WebSocket;

public class DataSyncService extends Service {
    private static final String AQI_URL = "ws://city-ws.herokuapp.com";
    private OkHttpClient mClient;

    @Override
    public int onStartCommand(Intent intent,int flags,int startID){
        mClient = new OkHttpClient();

        Request request = new Request.Builder().url(AQI_URL).build();
        AqiWebSocketListener listener = new AqiWebSocketListener(this.getApplicationContext());
        WebSocket webSocket = mClient.newWebSocket(request, listener);
        mClient.dispatcher().executorService().shutdown();

        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}