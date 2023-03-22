package com.example.note_and_todo_app.todo.category;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;
import com.example.note_and_todo_app.R;
import com.example.note_and_todo_app.base.OnCreateDialogResult;
import com.example.note_and_todo_app.database.Database;
import com.example.note_and_todo_app.database.task.Task;
import com.example.note_and_todo_app.database.task.TaskCategory;
import com.example.note_and_todo_app.database.task.TaskState;
import com.example.note_and_todo_app.databinding.FragmentTaskCategoryBinding;
import com.example.note_and_todo_app.todo.TaskListener;
import com.example.note_and_todo_app.todo.all.TasksWithTitle;
import com.example.note_and_todo_app.todo.list.CreateTaskDialog;
import com.example.note_and_todo_app.todo.list.TaskListAdapter;
import com.example.note_and_todo_app.utils.Constants;
import org.jetbrains.annotations.NotNull;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TaskCategoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TaskCategoryFragment extends Fragment implements TaskCategoryAdapter.OnTaskCategoryItemClickListener, TaskListener {
    private final String TAG = TaskCategoryFragment.class.getSimpleName();

    public TaskCategoryFragment() {
    }

    private FragmentTaskCategoryBinding binding;
    public TaskCategoryViewModel viewModel;
    private TaskCategoryAdapter categoriesAdapter;
    private TaskListAdapter tasksListAdapter;
    public static final MutableLiveData<Boolean> isSearching = new MutableLiveData<>(false);

    public static TaskCategoryFragment INSTANCE;

    public static TaskCategoryFragment newInstance() {
        TaskCategoryFragment fragment = new TaskCategoryFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        INSTANCE = fragment;
        return fragment;
    }

    @Override
    public void onAttach(@NonNull @NotNull Context context) {
        super.onAttach(context);
        viewModel = new TaskCategoryViewModel(getContext());
        categoriesAdapter = new TaskCategoryAdapter(this);
        tasksListAdapter = new TaskListAdapter(this);
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_task_category, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupView();
        setupListener();
    }

    private void setupView() {
        isSearching.observe(getViewLifecycleOwner(), b -> {
            binding.setIsSearching(b);
        });
        viewModel.fetchAllCategories();
        viewModel.fetchTaskByTitle();
        viewModel.categoriesLiveData.observe(getViewLifecycleOwner(), categories -> {
            categoriesAdapter.updateItem(categories);
        });
        viewModel.tasksListLiveData.observe(getViewLifecycleOwner(), tasks -> {
            tasksListAdapter.updateItems(tasks);
        });

        binding.rv.setAdapter(categoriesAdapter);
        binding.searchListRv.setAdapter(tasksListAdapter);
    }

    private void setupListener() {
        binding.allTask.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString(Constants.KEY, Constants.ALL_TASK);
            Navigation.findNavController(requireView()).navigate(R.id.action_fragment_main_nav_to_fragment_all_task, bundle);
        });

        binding.next7Days.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString(Constants.KEY, Constants.NEXT_7_DAYS);
            Navigation.findNavController(requireView()).navigate(R.id.action_fragment_main_nav_to_fragment_all_task, bundle);
        });

        binding.searchBar.setOnQueryTextFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                isSearching.postValue(true);
            }
        });

        binding.searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                viewModel.fetchTaskByTitle(newText);
                return false;
            }
        });
    }

    OnCreateDialogResult dialogResult = new OnCreateDialogResult() {
        @Override
        public void onConfirm() {
            viewModel.fetchAllCategories();
        }

        @Override
        public void onCancel() {

        }

        @Override
        public void onDelete() {
        }
    };

    @Override
    public void onItemClick(@Nullable @org.jetbrains.annotations.Nullable TaskCategory data) {
        if (data == null) {
            new CreateCategoryDialog(dialogResult).show(getParentFragmentManager(), "create category");
        } else {
            Bundle bundle = new Bundle();
            bundle.putLong(Constants.CATEGORY_ID, data.getId());
            bundle.putString(Constants.CATEGORY_TITLE, data.getTitle());
            Navigation.findNavController(requireView())
                    .navigate(R.id.action_fragment_main_nav_to_fragment_task_list, bundle);
        }
    }

    @Override
    public void onStatusChange(Task task, int position) {
        task.setState(task.getState() != TaskState.DONE ? TaskState.DONE : TaskState.PROCESSING);
        viewModel.updateDatabase(task, Database.ACTION_UPDATE);
        if (!binding.rv.isComputingLayout() && binding.rv.getScrollState() == RecyclerView.SCROLL_STATE_IDLE) {
            tasksListAdapter.updateItems(task, position);
        }
    }

    @Override
    public void deleteTask(Task task, int position) {
        viewModel.updateDatabase(task, Database.ACTION_DELETE);
        tasksListAdapter.deleteItem(position);
    }

    @Override
    public void createNewTasks(TasksWithTitle tasksWithTitle) {

    }

    @Override
    public void showDetails(Task task) {
        new CreateTaskDialog(dialogResult, task).show(getParentFragmentManager(), "edit task");
    }
}