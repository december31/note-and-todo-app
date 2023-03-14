package com.example.note_and_todo_app.note;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.note_and_todo_app.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ViewHolder extends RecyclerView.ViewHolder {
    TextView title;
    TextView info;
    TextView date;
    CardView cardNote;

    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        this.title = itemView.findViewById(R.id.txtTile);
        this.info = itemView.findViewById(R.id.txtInfo);
        this.date = itemView.findViewById(R.id.txtDate);
        this.cardNote = itemView.findViewById(R.id.cardNote);
        //mClickListener.onItemClick(itemView.setOnClickListener(this),itemView.p);
    }



}
