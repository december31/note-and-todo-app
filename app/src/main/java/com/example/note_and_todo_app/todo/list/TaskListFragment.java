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
import com.example.note_and_todo_app.R;
import com.example.note_and_todo_app.base.OnCreateDialogResult;
import com.example.note_and_todo_app.database.task.Task;
import com.example.note_and_todo_app.database.task.TaskState;
import com.example.note_and_todo_app.databinding.FragmentTaskListBinding;
import com.example.note_and_todo_app.utils.Constants;
import org.jetbrains.annotations.NotNull;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TaskListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TaskListFragment extends Fragment implements TaskListAdapter.OnTaskStatusChangeListener {
    private final String TAG = TaskListFragment.class.getSimpleName();
    private FragmentTaskListBinding binding;
    private final TaskListViewModel viewModel = new TaskListViewModel(getContext());
    private final TaskListAdapter adapter = new TaskListAdapter(this);
    private Bundle arguments;

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
    }

    OnCreateDialogResult dialogResult = new OnCreateDialogResult() {
        @Override
        public void onConfirm() {
            viewModel.fetchItems();
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
        viewModel.tasksListLiveData.observe(getViewLifecycleOwner(), adapter::updateItems);
        binding.rv.setAdapter(adapter);
        viewModel.fetchItems();
        setupToolBar();
    }

    @Override
    public void onResume() {
        super.onResume();
        viewModel.fetchItems();
    }

    private void setupToolBar() {
        binding.toolbar.title.setText(arguments != null ? arguments.getString(Constants.CATEGORY_TITLE) : "Tasks");
        binding.toolbar.icLeft.setOnClickListener(v -> Navigation.findNavController(requireView()).popBackStack());
        binding.toolbar.icLeft.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_back));
    }

    @Override
    public void onStatusChange(Task task, boolean status, int position) {
        task.setState(status? TaskState.DONE:TaskState.PROCESSING);
        adapter.updateItems(task, position);
    }
}