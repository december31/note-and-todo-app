package com.example.note_and_todo_app.ui.splash;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.note_and_todo_app.databinding.FragmentNavSplashBinding;
import org.jetbrains.annotations.NotNull;

public class SplashNavFragment extends Fragment {

    private FragmentNavSplashBinding binding;

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        binding = FragmentNavSplashBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
}
