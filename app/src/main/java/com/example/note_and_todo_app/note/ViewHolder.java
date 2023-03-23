package com.example.note_and_todo_app.note;

import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.note_and_todo_app.R;
import com.example.note_and_todo_app.utils.Constants;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ViewHolder extends RecyclerView.ViewHolder {
    TextView title;
    TextView info;
    TextView date;
    CardView cardNote;

    ImageView imageView;

    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        this.title = itemView.findViewById(R.id.txtTile);
        this.info = itemView.findViewById(R.id.txtInfo);
        this.date = itemView.findViewById(R.id.txtDate);
        this.cardNote = itemView.findViewById(R.id.cardNote);
        this.imageView = itemView.findViewById(R.id.imageRecycler);
    }



}
