package com.example.note_and_todo_app.services;

import android.annotation.SuppressLint;
import android.app.*;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.note_and_todo_app.preferences.Preferences;
import com.example.note_and_todo_app.ui.main.MainActivity;
import com.example.note_and_todo_app.R;
import com.example.note_and_todo_app.receivers.ScreenStateReceiver;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import android.os.Handler;
import java.util.logging.LogRecord;

public class TodoListServices extends Service {

    public static String CHANNEL = "notificationChanel";
    private ScreenStateReceiver receiver;

    @Override
    public void onCreate() {
        super.onCreate();
        receiver = new ScreenStateReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_SCREEN_ON);
        registerReceiver(receiver, filter);
        handler.postDelayed(runnable, interval);

        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.O) {
            createNotificationChanel();
            Intent notificationIntent = new Intent(this, MainActivity.class);
            PendingIntent pendingIntent =
                    PendingIntent.getActivity(this, 0, notificationIntent,
                            PendingIntent.FLAG_IMMUTABLE);

            Notification notification =
                    new Notification.Builder(this, CHANNEL)
                            .setContentTitle(getResources().getString(R.string.notification_title))
                            .setSmallIcon(R.drawable.ic_launcher)
                            .setContentIntent(pendingIntent)
                            .build();
            // Notification ID cannot be 0.
            startForeground(1, notification);

        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }

    @Override
    public boolean stopService(Intent name) {
        handler.removeCallbacks(runnable);
        return super.stopService(name);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        createNotificationChanel();

        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent =
                PendingIntent.getActivity(this, 0, notificationIntent,
                        PendingIntent.FLAG_IMMUTABLE);

        Notification notification =
                new Notification.Builder(this, CHANNEL)
                        .setContentTitle(getResources().getString(R.string.notification_title))
                        .setSmallIcon(R.drawable.ic_launcher)
                        .setContentIntent(pendingIntent)
                        .build();
        // Notification ID cannot be 0.
        startForeground(1, notification);

        return START_NOT_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void createNotificationChanel() {
        NotificationChannel serviceChanel = new NotificationChannel(
                CHANNEL, "Foreground Service Chanel", NotificationManager.IMPORTANCE_LOW
        );
        NotificationManager manager = getSystemService(NotificationManager.class);
        manager.createNotificationChannel(serviceChanel);
    }
    private void createAlarm(){
        Date date = new Date();
        Integer time = date.getHours()*120+ date.getMinutes()*60 + date.getSeconds();
        if(time.equals(Objects.requireNonNull(Preferences.getPreference()).getTimeAlarm()) && Preferences.getPreference().isOnAlarm()){
            Log.d("dajsdjasid", String.valueOf(date.getMinutes()));
        }
    }
    private final Handler handler = new Handler();
    private final long interval = 500;
    private final Runnable runnable = new Runnable() {
        @Override
        public void run() {
            createAlarm();
            // Lập lịch chạy lại công việc sau interval
            handler.postDelayed(this, interval);
        }
    };

}
