package com.example.note_and_todo_app.ui.task.category;

import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import com.example.note_and_todo_app.R;
import com.example.note_and_todo_app.ui.base.OnCreateDialogResult;
import com.example.note_and_todo_app.database.task.TaskCategory;
import com.example.note_and_todo_app.database.task.TaskRepository;
import com.example.note_and_todo_app.databinding.DialogCreateCategoryBinding;
import com.example.note_and_todo_app.utils.Constants;
import org.jetbrains.annotations.NotNull;

import java.util.Calendar;
import java.util.Objects;

public class CreateCategoryDialog extends DialogFragment {
	private final String TAG = CreateCategoryDialog.class.getSimpleName();
	private DialogCreateCategoryBinding binding;
	private final TaskRepository repository = TaskRepository.getInstance(getContext());
	private final OnCreateDialogResult listener;
	private final TaskCategory category;
	public CreateCategoryDialog(OnCreateDialogResult listener) {
		this.listener = listener;
		category = null;
	}
	public CreateCategoryDialog(OnCreateDialogResult listener, TaskCategory category) {
		this.listener = listener;
		this.category = category;
	}

	@Nullable
	@org.jetbrains.annotations.Nullable
	@Override
	public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
		binding = DataBindingUtil.inflate(inflater, R.layout.dialog_create_category, container, false);
		return binding.getRoot();
	}

	@Override
	public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
		Dialog dialog = getDialog();
		dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

		setupListener();
	}

	private void setupListener() {
		if (category != null) {
			binding.editText.setText(category.getTitle());
			binding.saveButton.setEnabled(true);
			if (category.getId().equals(Constants.DEFAULT_CATEGORY_ID)) {
				binding.setIsUpdate(false);
			} else {
				binding.setIsUpdate(true);
			}
			if (getContext() != null) {
				binding.saveButton.setTextColor(ContextCompat.getColor(getContext(), R.color.blue));
			}
		}
		binding.editText.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {

			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {

			}

			@Override
			public void afterTextChanged(Editable s) {
				if (s.length() == 0) {
					binding.saveButton.setEnabled(false);
					if (getContext() != null) {
						binding.saveButton.setTextColor(ContextCompat.getColor(getContext(), R.color.faded_blue));
					}
				} else {
					binding.saveButton.setEnabled(true);
					if (getContext() != null) {
						binding.saveButton.setTextColor(ContextCompat.getColor(getContext(), R.color.blue));
					}
				}
			}
		});

		binding.saveButton.setOnClickListener(v -> {
			if (category != null) {
				updateCategory();
			} else {
				addNewCategory();
			}
			dismiss();
			listener.onConfirm();
		});
		binding.cancelButton.setOnClickListener(v -> {
			dismiss();
			listener.onCancel();
		});
		binding.deleteButton.setOnClickListener(v -> {
			//noinspection deprecation
			new AlertDialog.Builder(getContext())
					.setTitle("Delete Category")
					.setMessage("Are you sure you want to delete this category?")
					.setPositiveButton(android.R.string.yes, (dialog, which) -> {
						if (category != null) {
							repository.deleteSync(category);
						}
						listener.onDelete();
					})
					.setNegativeButton(android.R.string.no, null)
					.setIcon(android.R.drawable.ic_menu_delete)
					.show();
			dismiss();
		});
		binding.editText.setOnKeyListener((v, keyCode, event) -> keyCode == KeyEvent.KEYCODE_ENTER);
	}

	@Override
	public void onStart() {
		super.onStart();
		Dialog dialog = getDialog();
		if (dialog != null) {
			int width = ViewGroup.LayoutParams.MATCH_PARENT;
			int height = ViewGroup.LayoutParams.WRAP_CONTENT;
			dialog.getWindow().setLayout(width, height);
		}
	}

	private void addNewCategory() {
		String title = Objects.requireNonNull(binding.editText.getText()).toString().trim();
		repository.insert(new TaskCategory(title, Calendar.getInstance().getTimeInMillis()));
	}

	private void updateCategory() {
		if (category != null) {
			category.setTitle(Objects.requireNonNull(binding.editText.getText()).toString().trim());
			repository.update(category);
		}
	}
}
