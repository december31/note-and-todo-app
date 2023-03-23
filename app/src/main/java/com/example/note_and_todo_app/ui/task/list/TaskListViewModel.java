package com.example.note_and_todo_app.ui.task.list;

import android.content.Context;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.note_and_todo_app.database.Database;
import com.example.note_and_todo_app.database.task.Task;
import com.example.note_and_todo_app.database.task.TaskCategory;
import com.example.note_and_todo_app.database.task.TaskRepository;

import java.util.List;

public class TaskListViewModel extends ViewModel {

    private final TaskRepository repository;
    TaskCategory category;
    public MutableLiveData<List<Task>> tasksListLiveData = new MutableLiveData<>();
    public TaskListViewModel(Context context) {
        repository = TaskRepository.getInstance(context);
    }

    public void fetchItemsByCategory() {
        tasksListLiveData = repository.fetchTaskByCategory(category.getId());
    }

    public void updateDatabase(Task task, int action) {
        if (action == Database.ACTION_UPDATE) {
            repository.update(task);
        } else if(action == Database.ACTION_DELETE) {
            repository.delete(task);
        }
    }
}
