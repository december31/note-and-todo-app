package com.example.note_and_todo_app.ui.setting;

import androidx.lifecycle.ViewModel;
import com.example.note_and_todo_app.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SettingViewModel extends ViewModel {
    List<SettingItem> getSettingItems() {
        List<SettingItem> items = new ArrayList<>();

        Collections.addAll(items,
                new SettingItem(R.string.general, SettingItem.Type.CATEGORY, false),
                new SettingItem(R.string.language, SettingItem.Type.LANGUAGE, false),
                new SettingItem(R.string.show_done_task, SettingItem.Type.SHOW_COMPLETED_TASK, false),
                new SettingItem(R.string.notify_when_open_device, SettingItem.Type.NOTIFICATION, false),
                new SettingItem(R.string.about_application, SettingItem.Type.CATEGORY, false),
                new SettingItem(R.string.privacy_and_policy, SettingItem.Type.PRIVACY, false),
                new SettingItem(R.string.term_of_use, SettingItem.Type.TERM_OF_USE, false),
                new SettingItem(R.string.share_app, SettingItem.Type.SHARE, false),
                new SettingItem(R.string.rate_app, SettingItem.Type.RATE, false)
        );

        return items;
    }
}
