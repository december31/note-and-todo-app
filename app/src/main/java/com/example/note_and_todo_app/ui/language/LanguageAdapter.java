package com.example.note_and_todo_app.ui.language;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.note_and_todo_app.R;
import com.example.note_and_todo_app.database.note.Note;
import com.example.note_and_todo_app.database.note.NoteViewModel;
import com.example.note_and_todo_app.databinding.ItemLanguageBinding;
import com.example.note_and_todo_app.ui.language.enumm.Language;
import com.example.note_and_todo_app.ui.language.enumm.LanguageViewHolder;
import com.example.note_and_todo_app.ui.note.ViewHolder;

import java.util.List;

public class LanguageAdapter extends ListAdapter<Language, LanguageViewHolder> {
    public List<Language> items  ;
    public int selectItem = -1;
    private clickItemLanguage clickItemLanguage;
    protected LanguageAdapter(@NonNull DiffUtil.ItemCallback<Language> diffCallback, List<Language> item,clickItemLanguage clickItemLanguage) {
        super(diffCallback);
        this.items = item;
        this.clickItemLanguage = clickItemLanguage;
    }

    @NonNull
    @Override
    public LanguageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate( R.layout.item_language,parent,false);
        return new LanguageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LanguageViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Language listData = items.get(position);


        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onClick(View v) {
                clickItemLanguage.clickItem(position);
                notifyDataSetChanged();
            }
        });

        if(listData == null){
            return;
        }
        holder.textView.setText(listData.getNameLanguage());
        holder.imageView.setImageResource(Integer.parseInt(listData.getImage()));
        if(selectItem == position){
            holder.check.setImageResource(R.drawable.ic_checked_true);
        }else {
            holder.check.setImageResource(R.drawable.ic_radio_unchecked);
        }

    }

    public int getItemCount() {
        if(items !=null){
            return items.size();
        }
        return  0;
    }
    static class WordDiff extends DiffUtil.ItemCallback<Language> {

        @Override
        public boolean areItemsTheSame(@NonNull Language oldItem, @NonNull Language newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Language oldItem, @NonNull Language newItem) {
            return oldItem.getCodeLanguage().equals(newItem.getCodeLanguage());
        }
    }
    interface clickItemLanguage{
        void clickItem(int position);
    }

}
