package com.example.note_and_todo_app.note;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CompoundButton;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.note_and_todo_app.R;
import com.example.note_and_todo_app.database.note.Note;
import com.example.note_and_todo_app.database.note.NoteViewModel;
import com.example.note_and_todo_app.database.task.TaskCategory;
import com.example.note_and_todo_app.utils.Constants;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class NoteApdapter extends ListAdapter<Note,ViewHolder> implements Filterable {


    public List<Note> items  ;
    private List<Note> itemOld;
    private NoteViewModel noteViewModel;
    private Context context;

    private int positionNotify = 0;
    protected NoteApdapter(@NonNull DiffUtil.ItemCallback<Note> diffCallback,Context context) {
        super(diffCallback);
        this.context = context;
    }


    @SuppressLint("NotifyDataSetChanged")
    public void updateItem(List<Note> notes) {
        this.items =  notes;
        this.itemOld = notes;
        //notifyItemChanged(positionNotify);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate( R.layout.layout_note_item,parent,false);
        noteViewModel = new ViewModelProvider((ViewModelStoreOwner) context).get(NoteViewModel.class);
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
        if(listData.getImagePath() != null){
            holder.imageView.setImageBitmap(BitmapFactory.decodeFile(listData.getImagePath()));
            holder.imageView.setVisibility(View.VISIBLE);
        }else {
            holder.imageView.setVisibility(View.GONE);
        }

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

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String str = constraint.toString();
                List<Note> filteredList = new ArrayList<>();
                if (str.isEmpty()) {
                    items = itemOld;
                } else {
                    for (Note item : itemOld) {
                        if (item.getTitle().toLowerCase().contains(str.toLowerCase())) {
                            filteredList.add(item);
                        }
                    }
                    items = filteredList;
                }
                FilterResults results = new FilterResults();
                results.values = items;
                return results;
            }

            @SuppressLint("NotifyDataSetChanged")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                items = (List<Note>) results.values;
                notifyDataSetChanged();
            }
        };
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
