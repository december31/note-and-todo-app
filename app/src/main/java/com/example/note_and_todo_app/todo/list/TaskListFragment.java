package com.example.note_and_todo_app.todo.list;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;
import com.example.note_and_todo_app.R;
import com.example.note_and_todo_app.base.OnCreateDialogResult;
import com.example.note_and_todo_app.database.Database;
import com.example.note_and_todo_app.database.task.Task;
import com.example.note_and_todo_app.database.task.TaskState;
import com.example.note_and_todo_app.databinding.FragmentTaskListBinding;
import com.example.note_and_todo_app.todo.TaskListener;
import com.example.note_and_todo_app.todo.all.TasksWithTitle;
import com.example.note_and_todo_app.todo.category.TaskCategoryFragment;
import com.example.note_and_todo_app.utils.Constants;
import org.jetbrains.annotations.NotNull;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TaskListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TaskListFragment extends Fragment implements TaskListener {
    private final String TAG = TaskListFragment.class.getSimpleName();
    private FragmentTaskListBinding binding;
    private final TaskListViewModel viewModel = new TaskListViewModel(getContext());
    private final TaskListAdapter adapter = new TaskListAdapter(this);
    private Bundle arguments;

    public static TaskListFragment INSTANCE;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment fragment_todo_list.
     */
    public static TaskListFragment newInstance(String param1, String param2) {
        TaskListFragment fragment = new TaskListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

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
        viewModel.categoryId = arguments != null ? arguments.getLong(Constants.CATEGORY_ID) : 0;
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
    };

    private void setupListener() {
        binding.newTaskButton.setOnClickListener(v -> {
            new CreateTaskDialog(dialogResult, viewModel.categoryId).show(getParentFragmentManager(), "create task");
        });
    }

    private void setupView() {
        viewModel.fetchItemsByCategory();
        viewModel.tasksListLiveData.observe(getViewLifecycleOwner(), tasks -> {
            if (tasks.size() > 0) {
                adapter.updateItems(tasks);
                binding.setIsTaskEmpty(false);
            } else {
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
        binding.toolbar.icLeft.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_back));
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
}