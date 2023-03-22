package com.example.note_and_todo_app.todo.list;

import android.graphics.Color;
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
import com.example.note_and_todo_app.todo.TaskListener;
import com.example.note_and_todo_app.utils.Constants;
import com.google.android.gms.tasks.Tasks;
import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.*;

public class TaskListAdapter extends RecyclerView.Adapter<TaskListAdapter.TaskListViewHolder> {
    private final List<Task> items = new ArrayList<>();
    private final Map<Long, Boolean> hasAnimated = new HashMap<>();
    private TaskListener listener;
    public TaskListAdapter(TaskListener listener) {
        this.listener = listener;
    }

    public TaskListAdapter() {
    }

    public void setListener(TaskListener listener) {
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
        holder.bind(item, position);
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

    public void deleteItem(int position) {
        items.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, items.size());
    }

    private void setAnimation(View view, int position, OnAnimationEndListener animationEndListener) {
        Animation animation = AnimationUtils.loadAnimation(view.getContext(), R.anim.item_animation_slide_from_right);
        animation.setStartOffset(position * Constants.ANIMATION_OFFSET);
        animation.setDuration(Constants.ANIMATION_DURATION);
        view.startAnimation(animation);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                animationEndListener.onAnimationEnd();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
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

    public class TaskListViewHolder extends RecyclerView.ViewHolder {

        private final LayoutTaskItemBinding binding;

        public TaskListViewHolder(@NonNull @NotNull LayoutTaskItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(Task task, int position) {
            Calendar today = Calendar.getInstance();

            binding.setData(task);
            binding.setToday(today.getTimeInMillis());
            binding.setDueDateString(Constants.SIMPLE_DATE_FORMAT_2.format(new Date(task.getDueDate())));

            binding.deleteButton.setOnClickListener(v -> listener.deleteTask(task, position));
            binding.checkbox.setOnClickListener(v -> {
                if (Boolean.TRUE.equals(hasAnimated.get(task.getId()))) {
                    listener.onStatusChange(task, position);
                }
            });
            if (hasAnimated.get(task.getId()) == null) {
                setAnimation(binding.getRoot(), position, () -> {
                    if (task.getState() == TaskState.DONE) {
                        binding.streak.setVisibility(View.VISIBLE);
                        setAnimationOnDone(binding.streak);
                    }
                    hasAnimated.put(task.getId(), true);
                });
            } else {
                binding.streak.setVisibility(task.getState() == TaskState.DONE ? View.VISIBLE : View.GONE);
                if (task.getState() == TaskState.DONE) {
                    setAnimationOnDone(binding.streak);
                }
            }
        }
    }

    public interface OnAnimationEndListener {
        void onAnimationEnd();
    }
}
