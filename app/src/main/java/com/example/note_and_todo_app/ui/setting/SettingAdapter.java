package com.example.note_and_todo_app.ui.setting;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.note_and_todo_app.R;
import com.example.note_and_todo_app.databinding.LayoutSettingItemBinding;
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
        holder.bind(items.get(position));
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

    protected class SettingViewHolder extends RecyclerView.ViewHolder {

        private final LayoutSettingItemBinding binding;

        public SettingViewHolder(@NonNull @NotNull LayoutSettingItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(SettingItem item) {
            binding.setData(item);
            binding.title.setText(item.getTitleRes());
            if (item.getType() == SettingItem.Type.LANGUAGE) {
                binding.textRight.setText(R.string.english);
            }
            if (item.isHasSwitch()) {
                binding.check.setOnCheckedChangeListener((v, b) -> listener.onStateChanged(item, b));
            } else {
                binding.getRoot().setOnClickListener(v -> listener.onClick(item));
            }
        }
    }

    interface SettingListener {
        void onClick(SettingItem item);

        void onStateChanged(SettingItem item, boolean checked);
    }
}
