package com.asterisk.nam.demonotification;

import android.content.IntentFilter;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private WifiBroadcast mWifiBroadcast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        registerWifi();
    }

    public void registerWifi() {
        mWifiBroadcast = new WifiBroadcast();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        registerReceiver(mWifiBroadcast, intentFilter);
    }

    public void unregisterWifi() {
        unregisterReceiver(mWifiBroadcast);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterWifi();
    }
}
