package com.example.note_and_todo_app.todo.all;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
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

    AllTaskAdapter(TaskListener listener) {
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
            binding.title.setText(item.getTitle());

            binding.setIsShowRV(item.canShowRV);
            binding.setNumberOfTasks(item.getTasks().size());
            binding.setIsShowTasksCount(!item.canShowRV && item.getTasks().size() > 0);

            binding.title.setOnClickListener(v -> {
                item.canShowRV = item.getTasks().size() > 0 && !item.canShowRV;
                items.set(position, item);
                notifyItemChanged(position);
            });

            binding.newTaskButton.setOnClickListener(v -> {
                Log.i(AllTaskAdapter.class.getSimpleName(), Constants.SIMPLE_DATE_FORMAT_1.format(item.calendar.getTime()));
                listener.createNewTasks(item);
            });

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
