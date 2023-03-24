package com.example.note_and_todo_app.ui.setting;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.note_and_todo_app.R;

import java.util.ArrayList;

public class MomentFragment extends Fragment {
    // openmoment
   private ArrayList<String> items;
    private ListView list;
    private Button button;
  //  private Button momentBack;
    private ArrayAdapter itemsAdapter;



    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER


    public MomentFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//       ?
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.openmoment, container, false);

      //  final AppCompatButton momentBack = view.findViewById(R.id.momentback);
        list=view.findViewById(R.id.list);
        button =view.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
              //  Navigation.findNavController(requireView()).popBackStack();
                additem();
            }


            //final AppCompatButton momentBack = view.findViewById(R.id.momentback);
//            Button momentBack = view.findViewById(R.id.momentback);
//        momentBack.setOnClickListener((new View.OnClickListener() {
//          @Override
//            public void onClick(View view)
//
//           {
//                Navigation.findNavController(requireView()).popBackStack();
//
//            }}


        });
        items = new ArrayList<>();
        itemsAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1);
        itemsAdapter.addAll(items);
        list.setAdapter(itemsAdapter);
        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                // String position;
                // int position;
                return remove(i);
            }

        });
//        final AppCompatButton momentBack = view.findViewById(R.id.momentback);
//        momentBack.setOnClickListener((new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        Navigation.findNavController(requireView()).popBackStack();
//
//                    }
//                }
        Button momentBack = view.findViewById(R.id.momentback);
        momentBack.setOnClickListener((new View.OnClickListener() {
                    @Override
                    public void onClick(View view)

                    {
                        Navigation.findNavController(requireView()).popBackStack();

                    }}));




                //return inflater.inflate(R.layout.openmoment, container, false);
        return view;
    }

    private boolean remove(int position){
        // Context context = getApplicationContext();
        Context context;
        Toast.makeText(getContext(), " Item Removed", Toast.LENGTH_LONG).show();
        items.remove(position);
        itemsAdapter.notifyDataSetChanged();
        return true;
    }
    private void additem() {
        EditText input = getView().findViewById(R.id.edit_text);
        String itemText = input.getText().toString();

        if(!(itemText.equals(""))){
            itemsAdapter.add(itemText);
            input.setText("");
            itemsAdapter.notifyDataSetChanged();
        }
        //gg
        else{
            Toast.makeText(getContext(),"Please enter text..", Toast.LENGTH_LONG).show();
        }
    }

}
