package com.example.note_and_todo_app.ui.setting;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.note_and_todo_app.R;

public class PrivacyFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_privacy, container, false);

        @SuppressLint({"MissingInflatedId", "LocalSuppress"})

        ImageView privacyBack = view.findViewById(R.id.privacyback);
        privacyBack.setOnClickListener((new View.OnClickListener() {
                    @Override
                    public void onClick(View view)

                    {
                        Navigation.findNavController(getView()).popBackStack();

                    }


                }));
//       ?
        // Inflate the layout for this fragment
       // return inflater.inflate(R.layout.layout_privacy, container, false);
        return view;
    }

}
