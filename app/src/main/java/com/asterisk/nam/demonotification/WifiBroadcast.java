package com.asterisk.nam.demonotification;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.session.MediaSession;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

public class WifiBroadcast extends BroadcastReceiver {

    private final static String TAG_MEDIA = "tag";
    private final static String CHANNEL_ID = "android8";
    private final static int NOTIFICATION_ID = 69;
    private final static String BUTTON_BACK_TITLE = "back";
    private final static String BUTTON_NEXT_TITLE = "next";
    private final static String BUTTON_PAUSE_TITLE = "pause";
    private NotificationManager mNotificationManager;
    private Notification mBuilder;
    private NotificationCompat.Builder mBuilderC;
    private WifiManager mWifiManager;
    private MediaSession mMediaSession;

    @Override
    public void onReceive(Context context, Intent intent) {
        feelWifi(context);
    }

    public void feelWifi(Context context) {
        mWifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        if (mWifiManager.isWifiEnabled()) {
            showNotifiWhenWifiOn(context);
        } else {
            mNotificationManager.cancel(NOTIFICATION_ID);
        }
    }

    private void showNotifiWhenWifiOn(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mMediaSession = new MediaSession(context, TAG_MEDIA);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            mBuilder = new Notification.Builder(context, CHANNEL_ID)
                    .setSmallIcon(R.drawable.ic_adjust_black_24dp)
                    .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.winter))
                    .setWhen(System.currentTimeMillis())
                    .setContentTitle(context.getString(R.string.em_se_la_co_dau))
                    .setContentText(context.getString(R.string.auth_eslcd))
                    .setAutoCancel(true)
                    .setColor(Color.GRAY)
                    .setStyle(new Notification.MediaStyle().setShowActionsInCompactView(0, 1, 2).setMediaSession(mMediaSession.getSessionToken()))
                    .setContentIntent(Mp3Main.getIntentMp3(context))
                    .addAction(R.drawable.ic_skip_previous_black_24dp, BUTTON_BACK_TITLE, Mp3Main.getIntentMp3(context))
                    .addAction(R.drawable.ic_pause_black_24dp, BUTTON_PAUSE_TITLE, Mp3Main.getIntentMp3(context))
                    .addAction(R.drawable.ic_skip_next_black_24dp, BUTTON_NEXT_TITLE, Mp3Main.getIntentMp3(context)).build()
            ;
        }
        {
            mBuilderC = new NotificationCompat.Builder(context, CHANNEL_ID)
                    .setSmallIcon(R.drawable.ic_adjust_black_24dp)
                    .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.winter))
                    .setWhen(System.currentTimeMillis())
                    .setContentTitle(context.getString(R.string.em_se_la_co_dau))
                    .setContentText(context.getString(R.string.auth_eslcd))
                    .setAutoCancel(true)
                    .setColor(Color.GRAY)
                    .setContentIntent(Mp3Main.getIntentMp3(context))
                    .addAction(R.drawable.ic_skip_previous_black_24dp, BUTTON_BACK_TITLE, Mp3Main.getIntentMp3(context))
                    .addAction(R.drawable.ic_pause_black_24dp, BUTTON_PAUSE_TITLE, Mp3Main.getIntentMp3(context))
                    .addAction(R.drawable.ic_skip_next_black_24dp, BUTTON_NEXT_TITLE, Mp3Main.getIntentMp3(context));
        }
        mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = context.getString(R.string.my_data);
            String description = context.getString(R.string.my_des);
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            channel.enableLights(true);
            channel.enableVibration(true);
            channel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
            mNotificationManager.createNotificationChannel(channel);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            mNotificationManager.notify(NOTIFICATION_ID, mBuilder);
        } else {
            mNotificationManager.notify(NOTIFICATION_ID, mBuilderC.build());
        }
    }
}
