package com.example.note_and_todo_app.note;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.note_and_todo_app.R;
import com.example.note_and_todo_app.database.note.Note;
import com.example.note_and_todo_app.database.task.TaskCategory;
import com.example.note_and_todo_app.utils.Constants;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NoteApdapter extends ListAdapter<Note,ViewHolder> {


    private List<Note> items  ;
    private int positionNotify = 0;
    protected NoteApdapter(@NonNull DiffUtil.ItemCallback<Note> diffCallback) {
        super(diffCallback);
    }


    @SuppressLint("NotifyDataSetChanged")
    public void updateItem(List<Note> notes) {
        this.items =  notes;
        notifyItemChanged(positionNotify);
       // notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate( R.layout.layout_note_item,parent,false);
        view.findViewById(R.id.checkBox).setVisibility(View.GONE);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Note listData = items.get(position);
        if(listData == null){
            return;
        }
        holder.title.setText(listData.getTitle());
        holder.info.setText(listData.getInfo());
        holder.date.setText(listData.getDate());

        holder.cardNote.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putLong("idEdit",listData.getId());
               Navigation.findNavController(holder.itemView).navigate(R.id.addNoteFragment,bundle);
            }
        });
        setAnimation(holder.itemView,position);
        positionNotify = position;
    }

    @Override
    public int getItemCount() {
        if(items !=null){
            return items.size();
        }
        return  0;
    }
    private void setAnimation(View view, int position) {
        Animation animation = AnimationUtils.loadAnimation(view.getContext(), R.anim.item_animation_fall_down);
        animation.setStartOffset(position * Constants.ANIMATION_OFFSET);
        animation.setDuration(Constants.ANIMATION_DURATION);
        view.startAnimation(animation);

    }
    static class WordDiff extends DiffUtil.ItemCallback<Note> {

        @Override
        public boolean areItemsTheSame(@NonNull Note oldItem, @NonNull Note newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Note oldItem, @NonNull Note newItem) {
            return oldItem.getTitle().equals(newItem.getTitle());
        }
    }


}
