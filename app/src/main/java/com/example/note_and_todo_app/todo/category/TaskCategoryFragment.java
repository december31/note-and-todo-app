package com.example.note_and_todo_app.todo.category;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.note_and_todo_app.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TaskCategoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TaskCategoryFragment extends Fragment {

    public TaskCategoryFragment() {}

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment fragment_todo_category.
     */
    public static TaskCategoryFragment newInstance() {
        TaskCategoryFragment fragment = new TaskCategoryFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_todo_category, container, false);
    }
}