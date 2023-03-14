package com.example.note_and_todo_app.note;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.note_and_todo_app.R;

import java.util.List;

public class NoteApdapter extends RecyclerView.Adapter<ViewHolder> {
    List<Data> data ;
    public NoteApdapter(List<Data> data) {

        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate( R.layout.layout_note_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Data listData = data.get(position);
        holder.title.setText(listData.getTitle());
        holder.info.setText(listData.getInfo());
        holder.date.setText(listData.getDate().toString());
        holder.cardNote.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
               Navigation.findNavController(holder.itemView).navigate(R.id.noteDetail);
            }
        });


    }


    @Override
    public int getItemCount() {
        return data.size();
    }
    // allows clicks events to be caught


    // parent activity will implement this method to respond to click events
}
