package com.example.note_and_todo_app.todo.list;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.note_and_todo_app.database.task.Task;
import com.example.note_and_todo_app.databinding.LayoutTaskItemBinding;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class TaskListAdapter extends RecyclerView.Adapter<TaskListAdapter.TaskListViewHolder> {

    private List<Task> items = new ArrayList<>();

    public TaskListAdapter() {

    }

    @NonNull
    @NotNull
    @Override
    public TaskListViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return new TaskListViewHolder(LayoutTaskItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull TaskListViewHolder holder, int position) {

    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateItems(List<Task> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return items.size() + 1;
    }

    public static class TaskListViewHolder extends RecyclerView.ViewHolder {

        LayoutTaskItemBinding binding;

        public TaskListViewHolder(@NonNull @NotNull LayoutTaskItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
