package com.example.note_and_todo_app.preferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import androidx.preference.PreferenceManager;
public class Preferences {
    private final String prefNewUser = "new_user_pref";
    private final String prefLanguage = "language_pref";
    private final String prefShowDoneTask = "show_done_task_pref";
    private final String prefShowNotification = "show_notification_pref";
    private final String prefTimeAlarm = "prefTimeAlarm";
    private final String prefHourAlarm = "prefHourAlarm";
    private final String prefMinAlarm = "prefMinAlarm";

    private final String prefOnAlarm = "prefOnAlarm";
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

    public boolean isOnAlarm() {
        return mPref.getBoolean(prefOnAlarm, false);
    }
    public void setIsOnAlarm(boolean value) {
        SharedPreferences.Editor editor = mPref.edit();
        editor.putBoolean(prefOnAlarm, value);
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

    public Integer getTimeAlarm() {
        return mPref.getInt(prefTimeAlarm, 0);
    }
    public void setTimeAlarm(Integer value) {
        SharedPreferences.Editor editor = mPref.edit();
        editor.putInt(prefTimeAlarm, value);
        editor.apply();
    }
    public Integer getHourAlarm() {
        return mPref.getInt(prefHourAlarm, 0);
    }
    public void setHourAlarm(Integer value) {
        SharedPreferences.Editor editor = mPref.edit();
        editor.putInt(prefHourAlarm, value);
        editor.apply();
    }


    public Integer getMinAlarm() {
        return mPref.getInt(prefMinAlarm, 0);
    }
    public void setMinAlarm(Integer value) {
        SharedPreferences.Editor editor = mPref.edit();
        editor.putInt(prefMinAlarm, value);
        editor.apply();
    }

    ///

    public String getLanguage() {
        return mPref.getString(prefLanguage, "en");
    }
    public void setLanguage(String value) {
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
