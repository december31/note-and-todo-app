package com.example.note_and_todo_app.preferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import androidx.preference.PreferenceManager;
public class Preferences {
    private final String prefNewUser = "new_user_pref";
    private final String prefLanguage = "language_pref";
    private final String prefShowNotification = "show_notification_pref";

    private final SharedPreferences mPref;

    private Preferences(Context context) {
        mPref = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public boolean isNewUser() {
        return mPref.getBoolean(prefNewUser, true);
    }
    public void setIsNewUser(boolean value) {
        SharedPreferences.Editor editor = mPref.edit();
        editor.putBoolean(prefNewUser, value);
        editor.apply();
    }

    public boolean isShowNotification() {
        return mPref.getBoolean(prefShowNotification, false);
    }
    public void setIsShowNotification(boolean value) {
        SharedPreferences.Editor editor = mPref.edit();
        editor.putBoolean(prefShowNotification, value);
        editor.apply();
    }

    public String getLanguage() {
        return mPref.getString(prefLanguage, "en");
    }
    public void SetLanguage(String value) {
        SharedPreferences.Editor editor = mPref.edit();
        editor.putString(prefLanguage, value);
        editor.apply();
    }
    private static Preferences INSTANCE;
    public static Preferences initPref(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new Preferences(context);
        }
        return INSTANCE;
    }
    public static Preferences getPreference() {
        if (INSTANCE == null) {
            Log.e(Preferences.class.getSimpleName(), "Preferences not initialized!");
            return null;
        }
        return INSTANCE;
    }
}
