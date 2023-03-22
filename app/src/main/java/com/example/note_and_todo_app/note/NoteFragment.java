package com.example.note_and_todo_app.note;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.SearchView;


import com.example.note_and_todo_app.R;
import com.example.note_and_todo_app.database.note.Note;
import com.example.note_and_todo_app.database.note.NoteViewModel;

import java.util.List;

public class NoteFragment extends Fragment {
    NoteApdapter noteApdapter;
    RecyclerView recyclerView;
    NoteViewModel noteViewModel;
    SearchView searchView;

    public static NoteFragment newInstance() {
        NoteFragment fragment = new NoteFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_note, container, false);
        recyclerView = view.findViewById(R.id.recyclerViewNote);
        noteApdapter = new NoteApdapter(new NoteApdapter.WordDiff(), this.getContext());

        noteViewModel = new ViewModelProvider(this).get(NoteViewModel.class);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, true));
        recyclerView.setAdapter(noteApdapter);
        noteViewModel.getAllNote().observe(getViewLifecycleOwner(), noteApdapter::updateItem);

        view.findViewById(R.id.searchNote);
        Bundle bundle = new Bundle();
        view.findViewById(R.id.actionNote).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Note noteBundle = new Note("", "", "");
                bundle.putLong("idAdd", noteViewModel.addNote(noteBundle));
                Navigation.findNavController(view).navigate(R.id.addNoteFragment, bundle);
            }
        });

        //search view
        searchView = view.findViewById(R.id.searchNote);
        //searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                noteApdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                noteApdapter.getFilter().filter(newText);
                return false;
            }
        });
        return view;
    }


}