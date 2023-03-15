package com.example.note_and_todo_app.todo.list;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TimePicker;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.room.Dao;
import com.example.note_and_todo_app.R;
import com.example.note_and_todo_app.base.OnCreateDialogResult;
import com.example.note_and_todo_app.database.Database;
import com.example.note_and_todo_app.database.task.Task;
import com.example.note_and_todo_app.database.task.TaskDao;
import com.example.note_and_todo_app.database.task.TaskState;
import com.example.note_and_todo_app.databinding.DialogCreateTaskBinding;
import com.example.note_and_todo_app.utils.Constants;
import org.jetbrains.annotations.NotNull;

import java.text.DateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoField;
import java.util.Date;
import java.util.Objects;

public class CreateTaskDialog extends DialogFragment {
    private static final String TAG = CreateTaskDialog.class.getSimpleName();
    private static final MutableLiveData<LocalDateTime> dueDateLiveData = new MutableLiveData<>();
    private DialogCreateTaskBinding binding;
    OnCreateDialogResult listener;
    private TimePickerDialog timePickerDialog;
    private DatePickerDialog datePickerDialog;
    private final Long categoryId;

    CreateTaskDialog(OnCreateDialogResult listener, Long categoryId) {
        this.categoryId = categoryId;
        this.listener = listener;
    }

    CreateTaskDialog(OnCreateDialogResult listener) {
        this.categoryId = Constants.CATEGORY_ID_DEFAULT;
        this.listener = listener;
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
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        setupListener();
    }

    private void setupListener() {
        dueDateLiveData.observe(getViewLifecycleOwner(), localDateTime -> {
            binding.dueDate.setText(localDateTime.format(Constants.DATE_TIME_FORMATTER_1));
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
            LocalDate date = LocalDate.now();
            date = date.plusDays(1);

            timePickerDialog = new TimePickerDialog(
                    getContext(),
                    timeSetListener,
                    date.atStartOfDay().getHour(),
                    date.atStartOfDay().getMinute(),
                    true
            );

            datePickerDialog = new DatePickerDialog(
                    getContext(),
                    dateSetListener,
                    date.get(ChronoField.YEAR),
                    date.get(ChronoField.MONTH_OF_YEAR),
                    date.get(ChronoField.DAY_OF_MONTH));
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
    }

    private final TimePickerDialog.OnTimeSetListener timeSetListener = (view, hourOfDay, minute) -> {
        Log.i(TAG, hourOfDay + ":" + minute);
        LocalDateTime time = dueDateLiveData.getValue();
        if (time != null) {
            dueDateLiveData.postValue(LocalDateTime.of(time.toLocalDate(), LocalTime.of(hourOfDay, minute)));
        }
    };

    private final DatePickerDialog.OnDateSetListener dateSetListener = (view, year, month, dayOfMonth) -> {
        Log.i(TAG, year + " - " + month + " - " + dayOfMonth);
        dueDateLiveData.postValue(LocalDateTime.of(year, month, dayOfMonth, 0, 0, 0));
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
        LocalDateTime dueDate = dueDateLiveData.getValue() == null ? LocalDateTime.now().plusDays(1): dueDateLiveData.getValue();
        Task task = new Task(
                categoryId,
                LocalDateTime.now().atZone(ZoneOffset.UTC).toInstant().toEpochMilli(),
                binding.editText.getText().toString(),
                TaskState.PROCESSING,
                dueDate.atZone(ZoneOffset.UTC).toInstant().toEpochMilli()
        );
        taskDao.insert(task);
    }
}
