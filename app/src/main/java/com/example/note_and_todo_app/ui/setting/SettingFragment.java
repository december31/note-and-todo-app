package com.example.note_and_todo_app.ui.setting;

import static android.system.Os.remove;
import static androidx.databinding.DataBindingUtil.setContentView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
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

    //code buid change language
    TextView messageView;
    Button btnHindi, btnEnglish;
    Context context;
    Resources resources;

    // openmoment
//     ArrayList<String> items;
//     ListView list;
//     Button button;
//      private ArrayAdapter itemsAdapter;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

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

    private TextView textView6;


    @SuppressLint("MissingInflatedId")
    @Override
    public void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_setting);

    }


    private void setContentView(int fragment_setting) {
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting, container, false);




// openmoent

//        list=view.findViewById(R.id.list);
//        button =view.findViewById(R.id.button);
//        button.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View view){
//                additem(view);
//            }


//            private void additem(View view) {
//                EditText input = view.findViewById(R.id.edit_text);
//                String itemText = input.getText().toString();
//
//                if(!(itemText.equals(""))){
//                    itemsAdapter.add(itemText);
//                    input.setText("");
//                }
//        //gg
//                else{
//                    Toast.makeText(context.getApplicationContext(),"Please enter text..", Toast.LENGTH_LONG).show();                }
//            }
     //   });
//        items = new ArrayList<>();
//        itemsAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1);
//        itemsAdapter.addAll(items);
//        list.setAdapter(itemsAdapter);
//        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//            @Override
//            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
//                // String position;
//               // int position;
//                return remove(i);
//            }

        //});





        // show rating dialog


        view.findViewById(R.id.textView9).setOnClickListener(v->{
            RateUsDialog rateUsDialog = new RateUsDialog(getContext());
            rateUsDialog.getWindow().setBackgroundDrawable(new ColorDrawable(getResources().getColor(android.R.color.transparent)));
            rateUsDialog.setCancelable(false);
            rateUsDialog.show();
        });

        textView =view.findViewById(R.id.textView5);
        switchCompat = view.findViewById(R.id.switch1);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
// them
        switchCompat.setOnCheckedChangeListener(
                new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        textView.setText("Show completed tasks                                         " +
                                (switchCompat.isChecked() ? "On" : "Off"));
                    }
                });
        view.findViewById(R.id.openMoment).setOnClickListener(v -> Navigation.findNavController(view).navigate(R.id.fragment_task_moment));
        view.findViewById(R.id.textView2).setOnClickListener(v -> Navigation.findNavController(view).navigate(R.id.fragment_task_privacy));
        view.findViewById(R.id.textView6).setOnClickListener(v -> Navigation.findNavController(view).navigate(R.id.fragment_task_language));
        view.findViewById(R.id.textView8).setOnClickListener(v -> {
            try {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, "My application name");
                String shareMessage= "\nLet me recommend you this application\n\n";
                shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID +"\n\n";
                shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                startActivity(Intent.createChooser(shareIntent, "choose one"));
            } catch(Exception e) {
                //e.toString();
            }
        });

        return view;
    }
// open

//     private boolean remove(int position){
//       // Context context = getApplicationContext();
//        Toast.makeText(context, " Item Removed", Toast.LENGTH_LONG).show();
//        items.remove(position);
//        itemsAdapter.notifyDataSetChanged();
//         return true;
//     }
//    private void additem(View view) {
//        EditText input = view.findViewById(R.id.edit_text);
//        String itemText = input.getText().toString();
//
//        if(!(itemText.equals(""))){
//            itemsAdapter.add(itemText);
//            input.setText("");
//        }
//        //gg
//        else{
//            Toast.makeText(context.getApplicationContext(),"Please enter text..", Toast.LENGTH_LONG).show();                }
//    }
}