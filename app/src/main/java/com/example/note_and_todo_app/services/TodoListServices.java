package com.example.note_and_todo_app.services;

import android.app.*;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import androidx.annotation.Nullable;
import com.example.note_and_todo_app.MainActivity;
import com.example.note_and_todo_app.R;
import com.example.note_and_todo_app.receivers.ScreenStateReceiver;

public class TodoListServices extends Service {

    public static String CHANNEL = "notificationChanel";

    @Override
    public void onCreate() {
        super.onCreate();
        ScreenStateReceiver receiver = new ScreenStateReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_SCREEN_ON);
        registerReceiver(receiver, filter);
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
                        .setContentTitle(getText(R.string.notification_title))
                        .setContentText(getText(R.string.notification_message))
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
                CHANNEL, "Foreground Service Chanel", NotificationManager.IMPORTANCE_DEFAULT
        );
        NotificationManager manager = getSystemService(NotificationManager.class);
        manager.createNotificationChannel(serviceChanel);
    }
}
