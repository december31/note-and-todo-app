package com.example.note_and_todo_app.ui.language;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.example.note_and_todo_app.R;
import com.example.note_and_todo_app.ui.language.enumm.Language;
import com.example.note_and_todo_app.ui.language.enumm.LanguageViewHolder;

import java.util.ArrayList;
import java.util.List;

public class LanguageAdapter extends ListAdapter<Language, LanguageViewHolder> {
    public List<Language> items = new ArrayList<>();
    private final ClickItemLanguage clickItemLanguage;

    private int previousPosition = 0;

    protected LanguageAdapter(@NonNull DiffUtil.ItemCallback<Language> diffCallback, ClickItemLanguage clickItemLanguage) {
        super(diffCallback);
        this.clickItemLanguage = clickItemLanguage;
    }

    @NonNull
    @Override
    public LanguageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_language, parent, false);
        return new LanguageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LanguageViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Language listData = items.get(position);

        holder.constraintLayout.setOnClickListener(v -> {
            clickItemLanguage.clickItem(listData, position);

            items.get(previousPosition).setSelected(false);
            items.get(position).setSelected(true);
            notifyItemChanged(previousPosition);
            notifyItemChanged(position);

            previousPosition = position;
        });

        if (listData == null) {
            return;
        }

        holder.textView.setText(listData.getNameLanguageRes());
        holder.imageView.setImageResource(listData.getImageRes());

        if (listData.getSelected()) {
            holder.check.setImageResource(R.drawable.ic_checked_true);
        } else {
            holder.check.setImageResource(R.drawable.ic_radio_unchecked);
        }
    }

    public int getItemCount() {
        if (items != null) {
            return items.size();
        }
        return 0;
    }

    public void setData(List<Language> listLanguage) {
        this.items.clear();
        this.items.addAll(listLanguage);
        for (int i = 0; i < listLanguage.size(); i++) {
            if (listLanguage.get(i).getSelected()) {
                previousPosition = i;
                break;
            }
        }
        notifyDataSetChanged();
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

    interface ClickItemLanguage {
        void clickItem(Language listData, int position);
    }

}
