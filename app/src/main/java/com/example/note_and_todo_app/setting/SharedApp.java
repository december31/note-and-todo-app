package com.example.note_and_todo_app.setting;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.text.Layout;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.note_and_todo_app.R;

import java.util.zip.Inflater;

public class SharedApp extends AppCompatActivity {
    //private Inflater inflater;
@SuppressLint("ResourceType")

    public boolean onCreateOptionLayout(Layout layout){
        getMenuInflater().inflate(R.layout.menushared, (Menu) layout);
        return super.onCreateOptionsMenu((Menu) layout);


        //return inflater.inflate(R.layout.menushared, container, false);

    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        Intent intent= new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, "check out this cool Application");
        intent.putExtra(Intent.EXTRA_TEXT, "Your Application link Here");
        startActivity(Intent.createChooser(intent, "Share App"));

        return super.onOptionsItemSelected(item);
    }

}
