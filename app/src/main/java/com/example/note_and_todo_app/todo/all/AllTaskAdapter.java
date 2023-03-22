package com.example.note_and_todo_app.todo.all;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.example.note_and_todo_app.R;
import com.example.note_and_todo_app.database.task.Task;
import com.example.note_and_todo_app.database.task.TaskState;
import com.example.note_and_todo_app.databinding.LayoutCategoryListItemBinding;
import com.example.note_and_todo_app.todo.TaskListener;
import com.example.note_and_todo_app.todo.list.TaskListAdapter;
import com.example.note_and_todo_app.utils.Constants;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class AllTaskAdapter extends RecyclerView.Adapter<AllTaskAdapter.AllTaskViewHolder> {

    private List<TasksWithTitle> items = new ArrayList<>();
    private final TaskListener listener;

    public AllTaskAdapter(TaskListener listener) {
        this.listener = listener;
    }

    @NonNull
    @NotNull
    @Override
    public AllTaskViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return new AllTaskViewHolder(LayoutCategoryListItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull AllTaskViewHolder holder, int position) {
        TasksWithTitle item = items.get(position);
        holder.bind(item, position);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateItems(List<TasksWithTitle> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    protected class AllTaskViewHolder extends RecyclerView.ViewHolder {
        private final LayoutCategoryListItemBinding binding;

        AllTaskViewHolder(LayoutCategoryListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(TasksWithTitle item, int position) {
            if (item.getTitleTextColor() != Constants.DEFAULT_COLOR_RES) {
                binding.title.setTextColor(
                        ContextCompat.getColor(binding.getRoot().getContext(), item.getTitleTextColor())
                );
            } else {
                binding.title.setTextColor(
                        ContextCompat.getColor(binding.getRoot().getContext(), Constants.DEFAULT_COLOR_RES)
                );
            }
            binding.title.setText(item.getTitle());

            binding.setIsShowRV(item.isCanShowRV());
            binding.setNumberOfTasks(item.getTasks().size());
            binding.setIsShowTasksCount(!item.isCanShowRV() && !item.getTasks().isEmpty());

            binding.title.setOnClickListener(v -> {
                item.setCanShowRV(!item.getTasks().isEmpty() && !item.isCanShowRV());
                items.set(position, item);
                notifyItemChanged(position);
            });

            binding.newTaskButton.setOnClickListener(v -> listener.createNewTasks(item));

            TaskListAdapter adapter = new TaskListAdapter();
            TaskListener onTaskStateChangedListener = new TaskListener() {
                @Override
                public void onStatusChange(Task task, int position) {
                    task.setState(task.getState() == TaskState.DONE ? TaskState.PROCESSING : TaskState.DONE);
                    adapter.updateItems(task, position);
                    listener.onStatusChange(task, position);
                }

                @Override
                public void deleteTask(Task task, int position) {
                    adapter.deleteItem(position);
                    listener.deleteTask(task, position);
                }

                @Override
                public void createNewTasks(TasksWithTitle tasksWithTitle) {

                }
            };
            adapter.setListener(onTaskStateChangedListener);
            binding.rv.setAdapter(adapter);
            adapter.updateItems(item.getTasks());
        }
    }
}
