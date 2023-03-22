package com.example.note_and_todo_app.todo.all;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import com.example.note_and_todo_app.R;
import com.example.note_and_todo_app.base.OnCreateDialogResult;
import com.example.note_and_todo_app.database.Database;
import com.example.note_and_todo_app.database.task.Task;
import com.example.note_and_todo_app.databinding.FragmentTaskListBinding;
import com.example.note_and_todo_app.todo.TaskListener;
import com.example.note_and_todo_app.todo.category.TaskCategoryFragment;
import com.example.note_and_todo_app.todo.list.CreateTaskDialog;
import com.example.note_and_todo_app.utils.Constants;
import org.jetbrains.annotations.NotNull;

public class AllTaskFragment extends Fragment implements TaskListener {
    private final String TAG = AllTaskFragment.class.getSimpleName();
    private FragmentTaskListBinding binding;
    private final AllTaskViewModel viewModel = new AllTaskViewModel(getContext());
    private final AllTaskAdapter adapter = new AllTaskAdapter(this);
    private Bundle arguments;
    private String type;

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        binding = FragmentTaskListBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        arguments = getArguments();
        setupView();
        setupListener();
    }

    private void setupView() {
        type = arguments.getString(Constants.KEY);
        viewModel.fetchItems(type);
        viewModel.allTaskLiveData.observe(getViewLifecycleOwner(), tasks -> {
            if (tasks.size() > 0) {
                adapter.updateItems(tasks);
                binding.setIsTaskEmpty(false);
            } else {
                binding.setIsTaskEmpty(true);
            }
        });
        binding.toolbar.title.setText(getResources().getString(
                type.equals(Constants.ALL_TASK) ? R.string.all_tasks : R.string.next_7_days
        ));
        binding.toolbar.icLeft.setImageResource(R.drawable.ic_back);
        binding.newTaskButton.setVisibility(View.GONE);
        binding.rv.setAdapter(adapter);
    }

    private void setupListener() {
        binding.toolbar.icLeft.setOnClickListener(v -> {
            TaskCategoryFragment.INSTANCE.viewModel.fetchAllCategories();
            Navigation.findNavController(binding.getRoot()).popBackStack();
        });
    }

    OnCreateDialogResult dialogResult = new OnCreateDialogResult() {
        @Override
        public void onConfirm() {
            viewModel.fetchItems(type);
        }

        @Override
        public void onCancel() {

        }

        @Override
        public void onDelete() {

        }
    };

    @Override
    public void onStatusChange(Task task, int position) {
        viewModel.updateDatabase(task, Database.ACTION_UPDATE);
    }

    @Override
    public void deleteTask(Task task, int position) {
        viewModel.updateDatabase(task, Database.ACTION_DELETE);
        viewModel.fetchItems(type);
    }

    @Override
    public void createNewTasks(TasksWithTitle tasksWithTitle) {
        if (tasksWithTitle.getCalendar() == null) {
            new CreateTaskDialog(dialogResult).show(getParentFragmentManager(), "create task");
        } else {
            new CreateTaskDialog(dialogResult, tasksWithTitle.getCalendar()).show(getParentFragmentManager(), "create task");
        }
    }

    @Override
    public void showDetails(Task task) {
        new CreateTaskDialog(dialogResult, task).show(getParentFragmentManager(), "edit task");
    }
}
