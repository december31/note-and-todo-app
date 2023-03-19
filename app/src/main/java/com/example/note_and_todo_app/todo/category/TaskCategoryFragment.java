package com.example.note_and_todo_app.todo.category;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import com.example.note_and_todo_app.R;
import com.example.note_and_todo_app.base.OnCreateDialogResult;
import com.example.note_and_todo_app.database.task.TaskCategory;
import com.example.note_and_todo_app.databinding.FragmentTaskCategoryBinding;
import com.example.note_and_todo_app.utils.Constants;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TaskCategoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TaskCategoryFragment extends Fragment implements TaskCategoryAdapter.OnTaskCategoryItemClickListener {
    private final String TAG = TaskCategoryFragment.class.getSimpleName();
    public TaskCategoryFragment() {
    }
    private FragmentTaskCategoryBinding binding;
    public TaskCategoryViewModel viewModel;
    private TaskCategoryAdapter adapter;

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
        adapter = new TaskCategoryAdapter(this);
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
        viewModel.categoriesLiveData.observe(getViewLifecycleOwner(), adapter::updateItem);

        binding.myListRv.setAdapter(adapter);
        viewModel.fetchAllCategories();
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
    }

    OnCreateDialogResult dialogResult = new OnCreateDialogResult() {
        @Override
        public void onConfirm() {
            viewModel.fetchAllCategories();
        }

        @Override
        public void onCancel() {

        }
    };
    @Override
    public void onItemClick(@Nullable @org.jetbrains.annotations.Nullable TaskCategory data) {
        if (data == null) {
            Log.i(TAG, "to do create new category");
            new CreateCategoryDialog(dialogResult).show(getParentFragmentManager(), "create category");
        } else {
            Log.i(TAG, "to do go to task list");
            Bundle bundle = new Bundle();
            bundle.putLong(Constants.CATEGORY_ID, data.getId());
            bundle.putString(Constants.CATEGORY_TITLE, data.getTitle());
            Navigation.findNavController(
                    requireView()).navigate(R.id.action_fragment_main_nav_to_fragment_task_list,bundle);
        }
    }
}