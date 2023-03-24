package com.example.note_and_todo_app.ui.setting;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.note_and_todo_app.databinding.FragmentSettingBinding;
import org.jetbrains.annotations.NotNull;

public class SettingFragment extends Fragment implements SettingAdapter.SettingListener {

    private FragmentSettingBinding binding;
    private final SettingAdapter adapter = new SettingAdapter(this);
    private final SettingViewModel viewModel = new SettingViewModel();

    public static SettingFragment newInstance() {
        SettingFragment fragment = new SettingFragment();
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
        binding = FragmentSettingBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupView();
        setupToolBar();
    }

    private void setupView() {
        binding.rv.setAdapter(adapter);
        adapter.updateData(viewModel.getSettingItems());
    }

    private void setupToolBar() {

    }

    @Override
    public void onClick(SettingItem item) {
        switch (item.getType()) {
            case LANGUAGE:
                break;
            case PRIVACY:
                break;
            case TERM_OF_USE:
                break;
            case SHARE:
                break;
            case RATE:
                break;
            default:
                break;
        }
    }

    @Override
    public void onStateChanged(SettingItem item, boolean checked) {

    }
}