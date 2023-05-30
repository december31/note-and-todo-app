package com.example.note_and_todo_app.ui.language.enumm;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.note_and_todo_app.R;
import com.example.note_and_todo_app.ui.language.LanguageAdapter;

public class LanguageViewHolder extends RecyclerView.ViewHolder  {
    public ConstraintLayout constraintLayout;
    public ImageView imageView;
    public TextView textView;
    public ImageView check;
    public LanguageViewHolder(@NonNull View itemView) {
        super(itemView);
        constraintLayout = itemView.findViewById(R.id.container_main);
        imageView = itemView.findViewById(R.id.flag_img);
        textView = itemView.findViewById(R.id.language_name_txt);
        check = itemView.findViewById(R.id.checkbox);
    }
}
