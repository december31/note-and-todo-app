package com.example.note_and_todo_app.todo.list;

import android.content.Context;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.note_and_todo_app.database.Database;
import com.example.note_and_todo_app.database.task.Task;
import com.example.note_and_todo_app.database.task.TaskDao;

import java.util.List;

public class TaskListViewModel extends ViewModel {

    Long categoryId;
    public MutableLiveData<List<Task>> tasksListLiveData = new MutableLiveData<>();
    private final TaskDao taskDao;
    public TaskListViewModel(Context context) {
        taskDao = Database.getInstance(context).taskDao();
    }

    public void fetchItems() {
        List<Task> tasks = taskDao.getTaskByCategory(categoryId);
        tasksListLiveData.postValue(tasks);
    }

    public void updateDatabase(Task task, int action) {
        if (action == Database.ACTION_UPDATE) {
            taskDao.update(task);
        } else if(action == Database.ACTION_DELETE) {
            taskDao.delete(task);
        }
    }

    public void deleteTask(Task task) {
        taskDao.delete(task);
    }
}
