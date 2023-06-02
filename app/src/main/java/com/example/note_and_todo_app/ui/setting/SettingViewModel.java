package com.example.note_and_todo_app.ui.setting;

import androidx.lifecycle.ViewModel;
import androidx.work.impl.model.Preference;
import com.example.note_and_todo_app.R;
import com.example.note_and_todo_app.preferences.Preferences;
import com.example.note_and_todo_app.ui.language.enumm.Language;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class SettingViewModel extends ViewModel {
    List<SettingItem> getSettingItems() {
        List<SettingItem> items = new ArrayList<>();
        items.add(new SettingItem(R.string.general, SettingItem.Type.CATEGORY, false));
        items.add(new SettingItem(R.string.language, SettingItem.Type.LANGUAGE, false));
        items.add(new SettingItem(R.string.notify_when_open_device, SettingItem.Type.NOTIFICATION, true, Objects.requireNonNull(Preferences.getPreference()).isShowNotification()));
        items.add(new SettingItem(R.string.about_application, SettingItem.Type.CATEGORY, false));
//        items.add(new SettingItem(R.string.privacy_and_policy, SettingItem.Type.PRIVACY, false));
//        items.add(new SettingItem(R.string.term_of_use, SettingItem.Type.TERM_OF_USE, false));
        items.add(new SettingItem(R.string.share_app, SettingItem.Type.SHARE, false));
        items.add(new SettingItem(R.string.rate_app, SettingItem.Type.RATE, false));
        items.add(new SettingItem(R.string.report_bugs, SettingItem.Type.REPORT_BUGS, false));

        items.get(1).setRightTextRes(getSelectedLanguage().getNameLanguageRes());

        return items;
    }

    public Language getSelectedLanguage() {
        List<Language> items = new ArrayList<>();

        items.add(new Language("en", R.string.english, R.mipmap.ic_uk));
        items.add(new Language("vi", R.string.vietnamese, R.mipmap.ic_vn));
        items.add(new Language("hi", R.string.hindi, R.mipmap.ic_hindi));
        items.add(new Language("es", R.string.spanish, R.mipmap.ic_span));
        items.add(new Language("it", R.string.italian, R.mipmap.ic_it));

        String selectedLanguage = Objects.requireNonNull(Preferences.getPreference()).getLanguage();

        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getCodeLanguage().equals(selectedLanguage)) {
                return items.get(i);
            }
        }
        return null;
    }
}
