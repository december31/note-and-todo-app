package com.example.note_and_todo_app.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.util.Log;
import com.example.note_and_todo_app.NotifyActivity;

public class ScreenStateReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_SCREEN_ON) && !isCallActive(context)) {
            Intent startActivityIntent = new Intent(context, NotifyActivity.class);
            startActivityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(startActivityIntent);
        }
    }

    private boolean isCallActive(Context context) {
        AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        return audioManager.getMode() == AudioManager.MODE_RINGTONE;
    }
}
