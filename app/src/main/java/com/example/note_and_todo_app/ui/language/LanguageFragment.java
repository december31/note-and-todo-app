package com.example.note_and_todo_app.ui.language;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.note_and_todo_app.databinding.FragmentLanguageBinding;
import com.example.note_and_todo_app.preferences.Preferences;
import com.example.note_and_todo_app.ui.language.enumm.Language;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;
import java.util.Objects;

import static android.content.Intent.getIntent;

public class LanguageFragment extends Fragment implements LanguageAdapter.ClickItemLanguage {

    private LanguageAdapter languageAdapter;
    private FragmentLanguageBinding binding;
    private LanguageViewModel viewModel;
    private Language selectedLanguage;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentLanguageBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initiateView();
    }

    private void initiateView() {

        languageAdapter = new LanguageAdapter(new LanguageAdapter.WordDiff(), this);
        binding.rvLanguage.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        binding.rvLanguage.setAdapter(languageAdapter);
        binding.actionBackBtn.setOnClickListener(v ->
                Navigation.findNavController(binding.getRoot()).popBackStack());

        viewModel = new ViewModelProvider(requireActivity()).get(LanguageViewModel.class);
        viewModel.init();

        viewModel.onSuccessFetchAllLanguage().observe(getViewLifecycleOwner(), listLanguage -> {
            languageAdapter.setData(listLanguage);
        });

        binding.choseBtn.setOnClickListener( v -> {
            Objects.requireNonNull(Preferences.getPreference()).setLanguage(selectedLanguage.getCodeLanguage());
            setLocale(selectedLanguage.getCodeLanguage());
            Intent intent = requireActivity().getIntent();
            requireActivity().finish();
            startActivity(intent);
        });

        viewModel.fetchAllLanguage();
    }

    @SuppressWarnings("deprecation")
    public void setLocale(String languageCode) {
        Locale locale = new Locale(languageCode);
        Locale.setDefault(locale);
        Resources resources = requireActivity().getResources();
        Configuration config = resources.getConfiguration();
        config.setLocale(locale);
        resources.updateConfiguration(config, resources.getDisplayMetrics());
    }

    @Override
    public void clickItem(Language listData, int position) {
        selectedLanguage = listData;
    }
}
