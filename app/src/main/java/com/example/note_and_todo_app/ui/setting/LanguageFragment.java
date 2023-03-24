package com.example.note_and_todo_app.ui.setting;

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

import org.jetbrains.annotations.Nullable;

import com.example.note_and_todo_app.R;
public class LanguageFragment extends Fragment {

    //code buid change language
    TextView messageView;
    Button btnHindi, btnEnglish;
    Context context;
    Resources resources;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public LanguageFragment() {
        // Required empty public constructor
    }
    public static LanguageFragment newInstance() {
        LanguageFragment fragment = new LanguageFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }




    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_language, container, false);


        // language
        messageView = view.findViewById(R.id.changetext);
        btnHindi = view.findViewById(R.id.btnHindi);
        btnEnglish = view.findViewById(R.id.btnEnglish);

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

        Button languageBack = view.findViewById(R.id.languageback);
        languageBack.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view)

            {
                Navigation.findNavController(getView()).popBackStack();

            }


        }));


         //return inflater.inflate(R.layout.fragment_language, container, false);
        return view;
    }

}
