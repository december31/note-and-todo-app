package com.example.note_and_todo_app.todo.category;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import com.example.note_and_todo_app.R;
import com.example.note_and_todo_app.database.task.TaskCategory;
import com.example.note_and_todo_app.databinding.FragmentTaskCategoryBinding;
import org.jetbrains.annotations.NotNull;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TaskCategoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TaskCategoryFragment extends Fragment implements TaskCategoryAdapter.OnTaskCategoryItemClickListener {
    private final String TAG = TaskCategoryFragment.class.getSimpleName();
    public TaskCategoryFragment() {
        viewModel = new TaskCategoryViewModel(getContext());
        adapter = new TaskCategoryAdapter(this);
    }
    private FragmentTaskCategoryBinding binding;
    private final TaskCategoryViewModel viewModel;
    private final TaskCategoryAdapter adapter;
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment fragment_todo_category.
     */
    public static TaskCategoryFragment newInstance() {
        TaskCategoryFragment fragment = new TaskCategoryFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
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
    }

    private void setupView() {
        viewModel.categoriesLiveData.observe(getViewLifecycleOwner(), adapter::updateItem);

        binding.myListRv.setAdapter(adapter);
        viewModel.fetchAllCategories();
    }

    CreateCategoryDialog.OnCreateDialogResult dialogResult = new CreateCategoryDialog.OnCreateDialogResult() {
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
        }
    }
}