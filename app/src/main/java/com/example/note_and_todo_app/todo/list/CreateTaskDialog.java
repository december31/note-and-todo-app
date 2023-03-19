package com.example.note_and_todo_app.todo.list;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.MutableLiveData;
import com.example.note_and_todo_app.R;
import com.example.note_and_todo_app.base.OnCreateDialogResult;
import com.example.note_and_todo_app.database.Database;
import com.example.note_and_todo_app.database.task.Task;
import com.example.note_and_todo_app.database.task.TaskDao;
import com.example.note_and_todo_app.database.task.TaskState;
import com.example.note_and_todo_app.databinding.DialogCreateTaskBinding;
import com.example.note_and_todo_app.utils.Constants;
import org.jetbrains.annotations.NotNull;

import java.util.Calendar;

public class CreateTaskDialog extends DialogFragment {
    private static final String TAG = CreateTaskDialog.class.getSimpleName();
    private final MutableLiveData<Calendar> dueDateLiveData = new MutableLiveData<>();
    private DialogCreateTaskBinding binding;
    OnCreateDialogResult listener;
    private TimePickerDialog timePickerDialog;
    private DatePickerDialog datePickerDialog;
    private final Long categoryId;
    private final Calendar initDate;

    public CreateTaskDialog(OnCreateDialogResult listener, Long categoryId) {
        this.categoryId = categoryId;
        this.listener = listener;
        initDate = Calendar.getInstance();
        initDate.add(Calendar.DAY_OF_MONTH, 1);
    }

    public CreateTaskDialog(OnCreateDialogResult listener, Calendar initDate) {
        this.initDate = initDate;
        this.categoryId = Constants.DEFAULT_CATEGORY_ID;
        this.listener = listener;
    }

    public CreateTaskDialog(OnCreateDialogResult listener) {
        this.categoryId = Constants.DEFAULT_CATEGORY_ID;
        this.listener = listener;
        initDate = Calendar.getInstance();
        initDate.add(Calendar.DAY_OF_MONTH, 1);
    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        binding = DialogCreateTaskBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        Dialog dialog = getDialog();
        if (dialog != null) {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        setupView();
        setupListener();
    }

    private void setupView() {
        binding.dueDate.setText(Constants.SIMPLE_DATE_FORMAT_1.format(initDate.getTime()));
    }

    private void setupListener() {
        dueDateLiveData.observe(getViewLifecycleOwner(), calendar -> {
            binding.dueDate.setText(Constants.SIMPLE_DATE_FORMAT_1.format(calendar.getTime()));
        });

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

        binding.dueDateContainer.setOnClickListener(v -> {
            timePickerDialog = new TimePickerDialog(
                    getContext(),
                    timeSetListener,
                    initDate.get(Calendar.HOUR_OF_DAY),
                    initDate.get(Calendar.MINUTE),
                    true
            );

            datePickerDialog = new DatePickerDialog(
                    getContext(),
                    dateSetListener,
                    initDate.get(Calendar.YEAR),
                    initDate.get(Calendar.MONTH),
                    initDate.get(Calendar.DAY_OF_MONTH));
            datePickerDialog.show();
        });

        binding.saveButton.setOnClickListener(v -> {
            addNewTask();
            dismiss();
            listener.onConfirm();
        });
        binding.cancelButton.setOnClickListener(v -> {
            dismiss();
            listener.onCancel();
        });
        binding.editText.setOnKeyListener((v, keyCode, event) -> keyCode == KeyEvent.KEYCODE_ENTER);
    }

    private final TimePickerDialog.OnTimeSetListener timeSetListener = (view, hourOfDay, minute) -> {
        Log.i(TAG, hourOfDay + ":" + minute);
        Calendar calendar = dueDateLiveData.getValue();
        if (calendar != null) {
            calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
            calendar.set(Calendar.MINUTE, minute);
            dueDateLiveData.postValue(calendar);
        }
    };

    private final DatePickerDialog.OnDateSetListener dateSetListener = (view, year, month, dayOfMonth) -> {
        Log.i(TAG, year + " - " + month + " - " + dayOfMonth);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        dueDateLiveData.postValue(calendar);
        timePickerDialog.show();
    };

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

    private void addNewTask() {
        TaskDao taskDao = Database.getInstance(getContext()).taskDao();
        if (binding.editText.getText() == null) return;
        Calendar dueDate = dueDateLiveData.getValue() == null ? initDate : dueDateLiveData.getValue();
        Task task = new Task(
                categoryId,
                Calendar.getInstance().getTimeInMillis(),
                binding.editText.getText().toString(),
                TaskState.PROCESSING,
                dueDate.getTimeInMillis()
        );
        taskDao.insert(task);
    }
}
