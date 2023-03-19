package com.example.note_and_todo_app.note;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
import java.util.Calendar;
import java.util.Objects;

public class DtNoteFragment extends Fragment {
    Note update;
    Note add;
    Note note;
    NoteViewModel noteViewModel;
    AppCompatEditText textTitle;
    TextView textDate;
    AppCompatEditText textInfo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_add_detail, container, false);

        noteViewModel = new ViewModelProvider(this).get(NoteViewModel.class);
        initUi(view);
        setupUi(view);
        Long idEdit = getArguments().getLong("idEdit");
        update = Database.getInstance(this.getContext()).noteDao().getNote(idEdit);
        if( update != null ){
            setData();
        }
        Long idAdd = getArguments().getLong("idAdd");
        add = Database.getInstance(this.getContext()).noteDao().getNote(idAdd);
        //

        view.findViewById(R.id.backAddNote).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateData();
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
    private  void setData(){
        textTitle.setText(update.getTitle());
        textDate.setText(update.getDate());
        textInfo.setText(update.getInfo());

    }
    private  void updateData(){
        String title = textTitle.getText().toString();
        String date = textDate.getText().toString();
        String info = textInfo.getText().toString();
        if(update!= null){
            note = new Note(update.getId(),title,info,date);
        }else {
            note = new Note(add.getId(),title,info,date);
        }
        if(info.isEmpty() && title.isEmpty()){
            noteViewModel.delete(note);
        }else{
            noteViewModel.update(note);
        }


    }

    private boolean inputCheck(String info){
        return !(info.isEmpty());
    }
    @Override
    public void onResume() {
        super.onResume();
        ImageView menu = getView().findViewById(R.id.menuNote);
        ImageView btnCheck = getView().findViewById(R.id.doneButton);
        EditText editText = getView().findViewById(R.id.typingText);
        EditText title = getView().findViewById(R.id.titleNoteAdd);
        btnCheck.setVisibility(View.GONE);
        focusChange(editText,menu,btnCheck);
        focusChange(title,menu,btnCheck);
        btnCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menu.setVisibility(View.VISIBLE);
                btnCheck.setVisibility(View.GONE);
                ((MainActivity)requireActivity()).closeKeyboard();
                editText.clearFocus();

            }
        });
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteNote(getContext());
            }
        });
    }

    private void deleteNote(Context context) {
        new AlertDialog.Builder(context)
                .setTitle("Delete Note")
                .setMessage("Are you sure you want to delete this note?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String title = textTitle.getText().toString();
                        String date = textDate.getText().toString();
                        String info = textInfo.getText().toString();
                        if(update!= null){
                            note = new Note(update.getId(),title,info,date);
                        }else {
                            note = new Note(add.getId(),title,info,date);
                        }
                        noteViewModel.delete(note);
                        Navigation.findNavController(getView()).popBackStack();
                    }
                })
                .setNegativeButton(android.R.string.no, null)
                .setIcon(android.R.drawable.ic_menu_delete)
                .show();

    }

    private void setupUi(View view) {
        TextView textDate = (view).findViewById(R.id.dateAddNote);
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        String dateTime = simpleDateFormat.format(calendar.getTime());
        textDate.setText(dateTime);
    }
    private  void focusChange(EditText editText,ImageView menu,ImageView btnCheck){
        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    menu.setVisibility(View.GONE);
                    btnCheck.setVisibility(View.VISIBLE);
                }

            }
        });
    }

}