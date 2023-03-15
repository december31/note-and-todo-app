package com.example.note_and_todo_app.todo.list;

import android.annotation.SuppressLint;
import android.content.Context;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.note_and_todo_app.database.Database;
import com.example.note_and_todo_app.database.task.Task;
import com.example.note_and_todo_app.database.task.TaskDao;

import java.util.List;

public class TaskListViewModel extends ViewModel {

    Long categoryId;
    @SuppressLint("StaticFieldLeak")
    private final Context context;
    public MutableLiveData<List<Task>> tasksListLiveData = new MutableLiveData<>();
    public TaskListViewModel(Context context) {
        this.context = context;
    }

    public void fetchItems() {
        TaskDao taskDao = Database.getInstance(context).taskDao();
        List<Task> tasks = taskDao.getTaskByCategory(categoryId);
        tasksListLiveData.postValue(tasks);
    }
}
