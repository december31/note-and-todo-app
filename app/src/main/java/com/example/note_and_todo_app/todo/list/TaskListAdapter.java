package com.example.note_and_todo_app.todo.list;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.example.note_and_todo_app.R;
import com.example.note_and_todo_app.base.CustomDiffUtilsCallback;
import com.example.note_and_todo_app.database.task.Task;
import com.example.note_and_todo_app.database.task.TaskState;
import com.example.note_and_todo_app.databinding.LayoutTaskItemBinding;
import com.example.note_and_todo_app.utils.Constants;
import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.*;

public class TaskListAdapter extends RecyclerView.Adapter<TaskListAdapter.TaskListViewHolder> {

    private final List<Task> items = new ArrayList<>();
    private final Map<Integer, Boolean> hasAnimated = new HashMap<>();

    private final OnTaskStatusChangeListener listener;
    public TaskListAdapter(OnTaskStatusChangeListener listener) {
        this.listener = listener;
    }

    @NonNull
    @NotNull
    @Override
    public TaskListViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return new TaskListViewHolder(LayoutTaskItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull TaskListViewHolder holder, int position) {
        Task item = items.get(position);
        if (item.getState() == TaskState.DONE) {
            setAnimationOnDone(holder.binding.streak);
        }
        holder.binding.setData(item);
        holder.binding.dueDate.setText(new SimpleDateFormat("dd/MM hh:mm", Locale.UK).format(new Date(item.getDueDate())));
        holder.binding.checkbox.setOnCheckedChangeListener((v,b) -> listener.onStatusChange(item, b, position));
        if (hasAnimated.get(position) == null) {
            setAnimation(holder.binding.getRoot(), position);
            hasAnimated.put(position, true);
        }
    }

    public void updateItems(List<Task> tasks) {
        DiffUtil.DiffResult result = DiffUtil.calculateDiff(new CustomDiffUtilsCallback<>(items, tasks));
        items.clear();
        items.addAll(tasks);
        result.dispatchUpdatesTo(this);
    }

    public void updateItems(Task task, int position) {
        items.set(position, task);
        notifyItemChanged(position);
    }
    private void setAnimation(View view, int position) {
        Animation animation = AnimationUtils.loadAnimation(view.getContext(), R.anim.item_animation_slide_from_right);
        animation.setStartOffset(position * Constants.ANIMATION_OFFSET);
        animation.setDuration(Constants.ANIMATION_DURATION);
        view.startAnimation(animation);
    }

    private void setAnimationOnDone(View view) {
        Animation animation = AnimationUtils.loadAnimation(view.getContext(), R.anim.animation_wipe_from_left);
        animation.setDuration(Constants.ANIMATION_DURATION);
        view.startAnimation(animation);
    }
    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class TaskListViewHolder extends RecyclerView.ViewHolder {

        LayoutTaskItemBinding binding;

        public TaskListViewHolder(@NonNull @NotNull LayoutTaskItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public interface OnTaskStatusChangeListener {
        void onStatusChange(Task task, boolean status, int position);
    }
}
