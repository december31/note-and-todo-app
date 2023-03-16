package com.example.note_and_todo_app.note;

import android.os.Bundle;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.note_and_todo_app.MainActivity;
import com.example.note_and_todo_app.R;
import com.example.note_and_todo_app.database.Database;
import com.example.note_and_todo_app.database.note.Note;
import com.example.note_and_todo_app.database.note.NoteViewModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class AddNoteFragment extends Fragment {
    Note note;
    NoteApdapter noteApdapter;
    NoteViewModel noteViewModel;
    AppCompatEditText textTitle;
    TextView textDate;
    AppCompatEditText textInfo;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_add_note, container, false);

        noteViewModel = new ViewModelProvider(this).get(NoteViewModel.class);
        initUi(view);

        setupUi(view);
        view.findViewById(R.id.backAddNote).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertData();
                Navigation.findNavController(view).popBackStack();
            }
        });

        return view;
    }

    private  void   initUi(View view){
        textTitle = view.findViewById(R.id.titleNoteAdd);
        textDate = view.findViewById(R.id.dateAddNote);
        textInfo = view.findViewById(R.id.typingText);
    }
    private void insertData() {
        String title = textTitle.getText().toString();
        String date = textDate.getText().toString();
        String info = textInfo.getText().toString();

        if(inputCheck(info)){
            note = new Note(title,info,date );
            noteViewModel.insert(note);
            Toast.makeText(this.getContext(),"add sucessfully",Toast.LENGTH_SHORT).show();
        }

    }
    private boolean inputCheck(String info){
        return !(info.isEmpty());
    }
    @Override
    public void onResume() {
        super.onResume();
        ImageView menu = getView().findViewById(R.id.menuAddNote);
        ImageView btnCheck = getView().findViewById(R.id.doneButton);
        EditText editText = getView().findViewById(R.id.typingText);
        btnCheck.setVisibility(View.GONE);
        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                menu.setVisibility(View.GONE);
                btnCheck.setVisibility(View.VISIBLE);
            }
        });
        btnCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menu.setVisibility(View.VISIBLE);
                btnCheck.setVisibility(View.GONE);
                ((MainActivity)requireActivity()).closeKeyboard();
            }
        });
    }

    private void setupUi(View view) {
        TextView textDate = (view).findViewById(R.id.dateAddNote);
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        String dateTime = simpleDateFormat.format(calendar.getTime());
        textDate.setText(dateTime);
    }


}