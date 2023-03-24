package com.example.note_and_todo_app.setting;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.note_and_todo_app.R;

public class ShareFragment extends AppCompatActivity {

   // @SuppressLint("ResourceType")
    @SuppressLint("ResourceType")
    //@Override
    public boolean onCreateOptionsLayout(Layout layout ) {
        getMenuInflater().inflate(R.layout.shareapp, (Menu) layout);
        return super.onCreateOptionsMenu((Menu) layout);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, " Check out this cool Application");
        intent.putExtra(Intent.EXTRA_TEXT, "Your Application Link Here");
        startActivity(Intent.createChooser(intent, "Share Via"));
        return super.onOptionsItemSelected(item);
    }

   // @SuppressLint("ResourceType")
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {
//       ?
        // Inflate the layout for this fragment
        // phai mo file return
         return inflater.inflate(R.layout.shareapp, container, false);
        //}

       // return null;
    }
}
