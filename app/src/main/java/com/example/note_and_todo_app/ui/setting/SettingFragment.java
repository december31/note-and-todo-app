package com.example.note_and_todo_app.ui.setting;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.note_and_todo_app.databinding.FragmentSettingBinding;
import com.example.note_and_todo_app.preferences.Preferences;
import com.example.note_and_todo_app.ui.main.MainActivity;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

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
    }

    private void setupView() {
        binding.rv.setAdapter(adapter);
        adapter.updateData(viewModel.getSettingItems());
    }

    @Override
    public void onClick(SettingItem item) {
        MainActivity activity = (MainActivity) getActivity();
        switch (item.getType()) {
            case LANGUAGE:
                break;
            case SHARE:
                if (activity != null) activity.shareApp();
                break;
            case RATE:
                if (activity != null) activity.rateApp();
                break;
            case REPORT_BUGS:
                if (activity != null) activity.reportBugs();
                break;
            default:
                break;
        }
    }

    @Override
    public void onStateChanged(SettingItem item, boolean checked, int position) {
        if (Objects.requireNonNull(item.getType()) == SettingItem.Type.NOTIFICATION) {
            Objects.requireNonNull(Preferences.getPreference()).setIsShowNotification(checked);
            MainActivity activity = (MainActivity) getActivity();
            MainActivity.isShowNotification.observe(getViewLifecycleOwner(), b -> {
                if (!b) {
                    item.setChecked(false);
                    adapter.updateData(item, position);
                    MainActivity.isShowNotification.postValue(true);
                }
            });
            if (activity != null) {
                if (checked) {
                    activity.startAppService();
                } else {
                    activity.stopAppService();
                }
            }
        }
    }
}