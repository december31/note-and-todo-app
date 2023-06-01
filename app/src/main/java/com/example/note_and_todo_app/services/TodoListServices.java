package com.example.note_and_todo_app.services;

import android.annotation.SuppressLint;
import android.app.*;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.example.note_and_todo_app.database.Database;
import com.example.note_and_todo_app.database.note.Note;
import com.example.note_and_todo_app.database.note.NoteDao;
import com.example.note_and_todo_app.database.note.NoteViewModel;
import com.example.note_and_todo_app.preferences.Preferences;
import com.example.note_and_todo_app.ui.main.MainActivity;
import com.example.note_and_todo_app.R;
import com.example.note_and_todo_app.receivers.ScreenStateReceiver;
import com.example.note_and_todo_app.ui.note.NoteFragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import android.os.Handler;
import java.util.logging.LogRecord;

public class TodoListServices extends Service {

    public static String CHANNEL = "notificationChanel";
    private ScreenStateReceiver receiver;
    private NoteDao noteDao;
    private NoteFragment noteFragment;

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
        int time = date.getHours()+ date.getMinutes() + date.getSeconds();
        if (Objects.requireNonNull(Preferences.getPreference()).isOnAlarm()){
            Database db = Database.getInstance(this);

            ArrayList<Note> notes = (ArrayList<Note>) db.noteDao().getAllTime();;
            for (int i = 0; i < notes.size(); i++) {

                int timeNote = Math.toIntExact(notes.get(i).getTime());
                if(time == timeNote && timeNote != 0){
                    int finalI = i;
                    new AlertDialog.Builder(getApplicationContext())
                            .setTitle(notes.get(i).getTitle())
                            .setMessage(notes.get(i).getInfo())
                            .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    db.noteDao().update(new Note(notes.get(finalI).getId(),0L,false));
                                }
                            })
                            .setIcon(R.drawable.ic_clock)
                            .show();
                }
            }
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
