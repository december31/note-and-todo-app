package com.example.note_and_todo_app.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import com.example.note_and_todo_app.ui.main.NotifyActivity;
import com.example.note_and_todo_app.database.task.TaskRepository;
import com.example.note_and_todo_app.database.task.TaskState;

public class ScreenStateReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        TaskRepository repository = TaskRepository.getInstance(context);
        if (intent.getAction().equals(Intent.ACTION_SCREEN_ON) && !isCallActive(context) && repository.isHavingTaskWithState(TaskState.PROCESSING)) {
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
