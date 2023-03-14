package com.example.note_and_todo_app.note;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.note_and_todo_app.R;
import com.example.note_and_todo_app.todo.category.TaskCategoryFragment;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

public class NoteFragment extends Fragment  {

	NoteApdapter noteApdapter;
	RecyclerView recyclerView;
	List<Data> data;


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


		 data = new ArrayList<>();
		 data.add(new Data("tt", "cc", new Date(2022, 4, 9)));
		 data.add(new Data("vv","uu",new Date(2022,4,9)));
		noteApdapter = new NoteApdapter(data);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
		recyclerView.setAdapter(noteApdapter);
		view.findViewById(R.id.actionNote).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Navigation.findNavController(view).navigate(R.id.addNoteFragment);
			}
		});
		return view;
    }


}