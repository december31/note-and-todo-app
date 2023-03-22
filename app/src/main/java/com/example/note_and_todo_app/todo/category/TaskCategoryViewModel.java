package com.example.note_and_todo_app.todo.category;

import android.annotation.SuppressLint;
import android.content.Context;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.note_and_todo_app.database.Database;
import com.example.note_and_todo_app.database.task.Task;
import com.example.note_and_todo_app.database.task.TaskCategory;
import com.example.note_and_todo_app.database.task.TaskDao;
import com.example.note_and_todo_app.database.task.TaskRepository;

import java.util.List;

public class TaskCategoryViewModel extends ViewModel {
	private final TaskRepository repository;
	public MutableLiveData<List<TaskCategory>> categoriesLiveData = new MutableLiveData<>();
	public MutableLiveData<List<Task>> tasksListLiveData = new MutableLiveData<>();

	public TaskCategoryViewModel(Context context) {
		repository = TaskRepository.getInstance(context);
	}

	public void fetchAllCategories() {
		categoriesLiveData = repository.fetchCategories();
	}

	public void fetchTaskByTitle() {
		fetchTaskByTitle("");
	}

	public void fetchTaskByTitle(String title) {
		tasksListLiveData = repository.fetchTasksByTitle(title);
	}

	public void updateDatabase(Task task, int action) {
		if (action == Database.ACTION_UPDATE) {
			repository.updateTask(task);
		} else if (action == Database.ACTION_DELETE) {
			repository.deleteTask(task);
		}
	}
}
