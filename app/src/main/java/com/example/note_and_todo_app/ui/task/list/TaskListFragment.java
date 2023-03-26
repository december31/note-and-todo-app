package com.example.note_and_todo_app.ui.task.list;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.lifecycle.LifecycleOwner;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;
import com.example.note_and_todo_app.R;
import com.example.note_and_todo_app.ui.base.OnCreateDialogResult;
import com.example.note_and_todo_app.database.Database;
import com.example.note_and_todo_app.database.task.Task;
import com.example.note_and_todo_app.database.task.TaskState;
import com.example.note_and_todo_app.databinding.FragmentTaskListBinding;
import com.example.note_and_todo_app.ui.task.TaskListener;
import com.example.note_and_todo_app.ui.task.all.TasksWithTitle;
import com.example.note_and_todo_app.ui.task.category.CreateCategoryDialog;
import com.example.note_and_todo_app.ui.task.category.TaskCategoryFragment;
import com.example.note_and_todo_app.utils.Constants;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;

public class TaskListFragment extends Fragment implements TaskListener {
    private FragmentTaskListBinding binding;
    private final TaskListViewModel viewModel = new TaskListViewModel(getContext());
    private final TaskListAdapter adapter = new TaskListAdapter(this);
    private Bundle arguments;
    public static TaskListFragment INSTANCE;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        INSTANCE = this;
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_task_list, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        arguments = getArguments();
        Long id = arguments != null ? arguments.getLong(Constants.CATEGORY_ID) : 0;
        viewModel.category = Database.getInstance(getContext()).taskDao().getTaskCategoryById(id);
        setupView();
        setupListener();
        setupToolBar();
    }

    OnCreateDialogResult dialogResult = new OnCreateDialogResult() {
        @Override
        public void onConfirm() {
            viewModel.fetchItemsByCategory();
        }

        @Override
        public void onCancel() {
        }

        @Override
        public void onDelete() {

        }
    };

    private void setupListener() {
        binding.newTaskButton.setOnClickListener(v -> new CreateTaskDialog(dialogResult, viewModel.category.getId()).show(getParentFragmentManager(), "create task"));
        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                binding.toolbar.icLeft.performClick();
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback((LifecycleOwner) requireContext(), callback);

    }

    private void setupView() {
        viewModel.fetchItemsByCategory();
        viewModel.tasksListLiveData.observe(getViewLifecycleOwner(), tasks -> {
            if (tasks.size() > 0) {
                adapter.updateItems(tasks);
                binding.setIsTaskEmpty(false);
            } else {
                adapter.updateItems(Collections.emptyList());
                binding.setIsTaskEmpty(true);
            }
        });
        binding.rv.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        viewModel.fetchItemsByCategory();
    }

    private void setupToolBar() {
        binding.toolbar.title.setText(arguments != null ? arguments.getString(Constants.CATEGORY_TITLE) : "Tasks");
        binding.toolbar.icLeft.setOnClickListener(v -> {
            TaskCategoryFragment.INSTANCE.viewModel.fetchAllCategories();
            Navigation.findNavController(requireView()).popBackStack();
        });
        binding.toolbar.icLeft.setImageResource(R.drawable.ic_back);
        binding.toolbar.icRight.setImageResource(R.drawable.ic_pencil);
        binding.toolbar.icRight.setOnClickListener(v -> {
            new CreateCategoryDialog(new OnCreateDialogResult() {
                @Override
                public void onConfirm() {
                    binding.toolbar.title.setText(
                            Database.getInstance(getContext())
                                    .taskDao()
                                    .getTaskCategoryById(viewModel.category.getId())
                                    .getTitle());
                }

                @Override
                public void onCancel() {

                }

                @Override
                public void onDelete() {
                    TaskCategoryFragment.INSTANCE.viewModel.fetchAllCategories();
                    Navigation.findNavController(requireView()).popBackStack();
                }
            }, viewModel.category).show(getParentFragmentManager(), null);
        });
    }

    @Override
    public void onStatusChange(Task task, int position) {
        task.setState(task.getState() != TaskState.DONE ? TaskState.DONE : TaskState.PROCESSING);
        viewModel.updateDatabase(task, Database.ACTION_UPDATE);
        if (!binding.rv.isComputingLayout() && binding.rv.getScrollState() == RecyclerView.SCROLL_STATE_IDLE) {
            adapter.updateItems(task, position);
        }
    }

    @Override
    public void deleteTask(Task task, int position) {
        viewModel.updateDatabase(task, Database.ACTION_DELETE);
        adapter.deleteItem(position);
    }

    @Override
    public void createNewTasks(TasksWithTitle tasksWithTitle) {

    }

    @Override
    public void showDetails(Task task) {
        new CreateTaskDialog(dialogResult, task).show(getParentFragmentManager(), "edit task");
    }
}