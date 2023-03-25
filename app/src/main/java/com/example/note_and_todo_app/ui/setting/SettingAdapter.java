package com.example.note_and_todo_app.ui.setting;

import android.annotation.SuppressLint;
import android.graphics.Typeface;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.note_and_todo_app.R;
import com.example.note_and_todo_app.databinding.LayoutSettingItemBinding;
import com.example.note_and_todo_app.preferences.Preferences;
import com.example.note_and_todo_app.ui.main.MainActivity;
import kotlin.Unit;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class SettingAdapter extends RecyclerView.Adapter<SettingAdapter.SettingViewHolder> {

    private List<SettingItem> items = new ArrayList<>();
    private final SettingListener listener;

    SettingAdapter(SettingListener listener) {
        this.listener = listener;
    }

    @NonNull
    @NotNull
    @Override
    public SettingViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return new SettingViewHolder(LayoutSettingItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull SettingViewHolder holder, int position) {
        holder.bind(items.get(position), position);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    void updateData(List<SettingItem> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    void updateData(SettingItem item, int position) {
        this.items.set(position, item);
        notifyItemChanged(position);
    }

    protected class SettingViewHolder extends RecyclerView.ViewHolder {

        private final LayoutSettingItemBinding binding;

        public SettingViewHolder(@NonNull @NotNull LayoutSettingItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(SettingItem item, int position) {
            binding.setData(item);
            binding.title.setText(item.getTitleRes());
            if (item.getType() == SettingItem.Type.LANGUAGE) {
                binding.textRight.setText(R.string.english);
            } else if (item.getType() == SettingItem.Type.CATEGORY) {
                binding.title.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 18);
                binding.title.setTypeface(null, Typeface.BOLD);
            } else {
                binding.title.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16);
                binding.title.setTypeface(null, Typeface.NORMAL);
            }
            if (item.isHasSwitch()) {
                binding.check.setChecked(item.isChecked());
                binding.check.setOnClickListener(v -> {
                    listener.onStateChanged(item, binding.check.isChecked(), position);
                });
            } else {
                binding.getRoot().setOnClickListener(v -> listener.onClick(item));
            }
        }
    }

    interface SettingListener {
        void onClick(SettingItem item);

        void onStateChanged(SettingItem item, boolean checked, int position);
    }
}
