package com.example.note_and_todo_app.setting;

import android.annotation.SuppressLint;
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

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.note_and_todo_app.R;

import java.util.ArrayList;

public class Fragment_openmoment extends Fragment {
    private ArrayList<String> items;
    private Button button;
    private ListView list;
    private ArrayAdapter itemsAdapter;
    // private Object container;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.open_moment, container, false);
        list = view.findViewById(R.id.list);
        button = view.findViewById(R.id.button);
        View v = inflater.inflate(R.layout.open_moment, container, false);
        Button back_moment= view.findViewById(R.id.back_moment);
        back_moment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(getView()).popBackStack();
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                additem();

            }

//            private void additem(View view) {
//
//            }
        });
        items = new ArrayList<>();
        itemsAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1);
        itemsAdapter.addAll(items);
        list.setAdapter(itemsAdapter);
        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                return remove(i);
            }

        });
        return view;
    }

    private boolean remove(int position) {
        Context context;
        Toast.makeText(getContext(), "Item Removed", Toast.LENGTH_LONG).show();
        items.remove(position);
        itemsAdapter.notifyDataSetChanged();
        return true;
    }

    private void additem() {
        EditText input = getView().findViewById(R.id.edit_text);
        String itemText = input.getText().toString();
        if (!(itemText.equals(""))) {
            itemsAdapter.add(itemText);
            input.setText("");
            itemsAdapter.notifyDataSetChanged();
        } else {
            Toast.makeText(getContext(), "Please enter text", Toast.LENGTH_LONG).show();
        }
    }
}

