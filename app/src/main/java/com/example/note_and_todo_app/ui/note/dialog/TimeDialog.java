package com.example.note_and_todo_app.ui.note.dialog;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CompoundButton;


import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import com.shawnlin.numberpicker.NumberPicker;
import com.example.note_and_todo_app.R;
import com.example.note_and_todo_app.preferences.Preferences;

import java.util.Objects;

public class TimeDialog extends DialogFragment {


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.time_dialog, container, false);
        if (getDialog() != null) {
            getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        }
        v.findViewById(R.id.close_time).setOnClickListener(v1 -> dismiss());

        NumberPicker hour = v.findViewById(R.id.numberPickerHour);
        NumberPicker min = v.findViewById(R.id.numberPickerMin);
        NumberPicker second = v.findViewById(R.id.numberPickerSec);
        setUpNumber(hour);
        setUpNumber(min);
        setUpNumber(second);
        int se = Objects.requireNonNull(Preferences.getPreference()).getTimeAlarm() - Preferences.getPreference().getHourAlarm()*120 - Preferences.getPreference().getMinAlarm()*60;
        hour.setValue(Objects.requireNonNull(Preferences.getPreference()).getHourAlarm());
        min.setValue(Preferences.getPreference().getMinAlarm());
        second.setValue(se);
        @SuppressLint("UseSwitchCompatOrMaterialCode") Switch sw = v.findViewById(R.id.swTime);
        sw.setChecked(Objects.requireNonNull(Preferences.getPreference()).isOnAlarm());
        sw.setOnCheckedChangeListener((buttonView, isChecked) -> Objects.requireNonNull(Preferences.getPreference()).setIsOnAlarm(isChecked));
        v.findViewById(R.id.btnSave).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int time = hour.getValue()*120 + min.getValue()*60 + second.getValue();
                Objects.requireNonNull(Preferences.getPreference()).setTimeAlarm(time);
                Preferences.getPreference().setHourAlarm(hour.getValue());
                Preferences.getPreference().setMinAlarm(min.getValue());
                dismiss();
            }
        });
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        WindowManager.LayoutParams params = Objects.requireNonNull(getDialog()).getWindow().getAttributes();
        if (params != null) {
            params.width = ViewGroup.LayoutParams.MATCH_PARENT;
            params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            getDialog().getWindow().setGravity(Gravity.BOTTOM);
            getDialog().getWindow().setAttributes(params);
        }
    }

    @SuppressLint("DefaultLocale")
    private void setUpNumber(NumberPicker number) {
        number.setFormatter(value -> String.format("%02d", value));
    }

}
