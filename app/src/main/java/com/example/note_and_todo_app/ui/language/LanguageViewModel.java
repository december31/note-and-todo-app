package com.example.note_and_todo_app.ui.language;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.preference.Preference;
import com.example.note_and_todo_app.R;
import com.example.note_and_todo_app.preferences.Preferences;
import com.example.note_and_todo_app.ui.language.enumm.Language;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class LanguageViewModel extends ViewModel {

    private MutableLiveData<List<Language>> listLanguageLiveData;

    public MutableLiveData<List<Language>> onSuccessFetchAllLanguage() {
        return listLanguageLiveData;
    }

    void init() {
        listLanguageLiveData = new MutableLiveData<>();
    }

    public void fetchAllLanguage() {
        List<Language> items = new ArrayList<>();

        items.add(new Language("en", R.string.english, R.mipmap.ic_uk));
        items.add(new Language("vi", R.string.vietnamese, R.mipmap.ic_vn));
        items.add(new Language("hi", R.string.hindi, R.mipmap.ic_hindi));
        items.add(new Language("es", R.string.spanish, R.mipmap.ic_span));
        items.add(new Language("it", R.string.italian, R.mipmap.ic_it));

        String selectedLanguage = Objects.requireNonNull(Preferences.getPreference()).getLanguage();

        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getCodeLanguage().equals(selectedLanguage)) {
                items.get(i).setSelected(true);
            }
        }

        listLanguageLiveData.postValue(items);
    }
}
