package com.example.note_and_todo_app.ui.task.all;

import android.content.Context;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.note_and_todo_app.database.Database;
import com.example.note_and_todo_app.database.task.Task;
import com.example.note_and_todo_app.database.task.TaskRepository;
import com.example.note_and_todo_app.utils.Constants;

import java.util.*;

public class AllTaskViewModel extends ViewModel {
    MutableLiveData<List<TasksWithTitle>> allTaskLiveData = new MutableLiveData<>();
    private final TaskRepository repository;

    public AllTaskViewModel(Context context) {
        repository = TaskRepository.getInstance(context);
    }

    public void fetchItems(String type) {
        if (type.equals(Constants.ALL_TASK)) {
            fetchAllTasks();
        } else {
            fetchDataNext7Days();
        }
    }

    private void fetchAllTasks() {
        allTaskLiveData = repository.fetchAllTasks();
    }

    private void fetchDataNext7Days() {
        allTaskLiveData = repository.fetchTasks7Days();
    }

    public void updateDatabase(Task task, int action) {
        if (action == Database.ACTION_UPDATE) {
            repository.update(task);
        } else if (action == Database.ACTION_DELETE) {
            repository.delete(task);
        }
    }
}
