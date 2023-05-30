package com.example.note_and_todo_app.ui.language;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.note_and_todo_app.R;
import com.example.note_and_todo_app.database.note.NoteViewModel;
import com.example.note_and_todo_app.ui.language.enumm.Language;
import com.example.note_and_todo_app.ui.language.enumm.LanguageViewHolder;
import com.example.note_and_todo_app.ui.note.NoteApdapter;

import java.util.ArrayList;
import java.util.List;

public class LanguageFragment extends Fragment implements LanguageAdapter.clickItemLanguage {

    LanguageAdapter languageAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_language, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.rv_language);
        List<Language> items = new ArrayList<>();
        items.add(new Language("en",getString(R.string.english) ,String.valueOf(R.drawable.ic_en)));
        items.add(new Language("vn",getString(R.string.vietnamese) ,String.valueOf(R.drawable.ic_vi)));

        languageAdapter = new LanguageAdapter(new LanguageAdapter.WordDiff(), items,this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false));
        recyclerView.setAdapter(languageAdapter);
        view.findViewById(R.id.action_back_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).popBackStack();
            }
        });
        return view;
    }

    @Override
    public void clickItem(int position) {
        languageAdapter.selectItem = position;
    }
}