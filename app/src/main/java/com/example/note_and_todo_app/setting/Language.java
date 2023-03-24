package com.example.note_and_todo_app.setting;
import static androidx.databinding.DataBindingUtil.setContentView;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.note_and_todo_app.R;
public class Language extends Fragment {
    TextView messageView;
    Button btnHindi, btnEnglish;
    Context context;
    Resources resources;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmentlanguage, container, false);
        Button buttonback = view.findViewById(R.id.buttonback);

        buttonback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Navigation.findNavController(getView()).popBackStack();
            }

        });

        // referencing the text and button views
        messageView =view.findViewById(R.id.textView);
        btnHindi = view.findViewById(R.id.btnHindi);
        btnEnglish = view.findViewById(R.id.btnEnglish);

        // setting up on click listener event over the button
        // in order to change the language with the help of
        // LocaleHelper class
        btnEnglish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context = LocaleHelper.setLocale(getContext(), "en");
                resources = context.getResources();
                messageView.setText(resources.getString(R.string.language));
            }
        });

        btnHindi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context = LocaleHelper.setLocale(getContext(), "hi");
                resources = context.getResources();
                messageView.setText(resources.getString(R.string.language));
            }
        });

        return view;
    }


}
