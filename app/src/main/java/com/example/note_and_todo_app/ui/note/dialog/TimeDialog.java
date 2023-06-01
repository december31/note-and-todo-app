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
import androidx.lifecycle.ViewModelProvider;

import com.example.note_and_todo_app.database.note.Note;
import com.example.note_and_todo_app.database.note.NoteViewModel;
import com.example.note_and_todo_app.ui.note.NoteDetailFragment;
import com.shawnlin.numberpicker.NumberPicker;
import com.example.note_and_todo_app.R;
import com.example.note_and_todo_app.preferences.Preferences;

import java.util.Date;
import java.util.Objects;

public class TimeDialog extends DialogFragment {

    Long Id ;
    NoteDetailFragment noteDetailFragment;
    NoteViewModel noteViewModel;

    public  TimeDialog(Long id){
        this.Id = id;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.time_dialog, container, false);
        if (getDialog() != null) {
            getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        }
        v.findViewById(R.id.close_time).setOnClickListener(v1 -> dismiss());
        noteViewModel = new ViewModelProvider(this).get(NoteViewModel.class);
        NumberPicker hour = v.findViewById(R.id.numberPickerHour);
        NumberPicker min = v.findViewById(R.id.numberPickerMin);
        NumberPicker second = v.findViewById(R.id.numberPickerSec);
        setUpNumber(hour);
        setUpNumber(min);
        setUpNumber(second);

//        hour.setValue(noteViewModel.getTimeNote(noteDetailFragment.update.getId()).getHours());
//        min.setValue(noteViewModel.getTimeNote(noteDetailFragment.update.getId()).getMinutes());
//        second.setValue(noteViewModel.getTimeNote(noteDetailFragment.update.getId()).getSeconds());

        @SuppressLint("UseSwitchCompatOrMaterialCode") Switch sw = v.findViewById(R.id.swTime);
        sw.setChecked(noteViewModel.getIsTimeNote(Id));
        v.findViewById(R.id.btnSave).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int time = hour.getValue() + min.getValue() + second.getValue();
                if(sw.isChecked()){
                    Note note = new Note(Id, (long) time,true);
                    noteViewModel.update(note);
                }else {
                    Note note = new Note(Id,0L,false);
                    noteViewModel.update(note);
                }

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
