package com.example.note_and_todo_app.setting;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.note_and_todo_app.R;

public class Privacy extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.layout_privacy, container, false);
                Button BackPrivacy = view.findViewById(R.id.BackPrivacy);
        BackPrivacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(getView()).popBackStack();
            }


        });


        return view;
        //return inflater.inflate(R.layout.layout_privacy, container, false);


    }
}

