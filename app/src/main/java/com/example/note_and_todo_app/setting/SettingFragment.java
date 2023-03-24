package com.example.note_and_todo_app.setting;

import static androidx.databinding.DataBindingUtil.setContentView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.note_and_todo_app.BuildConfig;
import com.example.note_and_todo_app.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SettingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SettingFragment extends Fragment {


    TextView textView;
    Switch switchCompat;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    //private progressBar;

    public SettingFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment fragment_setting.
     */
    // TODO: Rename and change types and number of parameters
    public static SettingFragment newInstance() {
        SettingFragment fragment = new SettingFragment();
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
        //open moment

    }

    private EditText findViewById(int btnHindi) {


        return null;
    }


    @SuppressLint({"MissingInflatedId", "WrongViewCast"})
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View view = inflater.inflate(R.layout.setting_layout, container, false);
        view.findViewById(R.id.rateApp).setOnClickListener(v -> {

            RateUsDialog rateUsDialog = new RateUsDialog(getContext());
            rateUsDialog.getWindow().setBackgroundDrawable(new ColorDrawable(getResources().getColor(android.R.color.transparent)));
            rateUsDialog.setCancelable(false);
            rateUsDialog.show();
        });
        textView = view.findViewById(R.id.switch_1);
        switchCompat = view.findViewById(R.id.switch1);

        switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                textView.setText("Show completed tasks                 "
                        + (switchCompat.isChecked() ? "On" : "Off"));
            }
        });


        view.findViewById(R.id.language).setOnClickListener(v -> Navigation.findNavController(view).navigate(R.id.fragment_task_language));
        view.findViewById(R.id.SharedApp).setOnClickListener(v -> {

            try {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, "My application name");
                String shareMessage = "\nLet me recommend you this application\n\n";
                shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID + "\n\n";
                shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                startActivity(Intent.createChooser(shareIntent, "choose one"));
            } catch (Exception e) {

            }
        });
        view.findViewById(R.id.privacy).setOnClickListener(v -> Navigation.findNavController(view).navigate(R.id.layout_task_privacy));
        view.findViewById(R.id.openmoment).setOnClickListener(v -> Navigation.findNavController(view).navigate(R.id.layout_task_open));

        return view;
    }

    public Context createConfigurationContext(Configuration configuration) {
        return null;
    }



}




