package com.example.note_and_todo_app.todo.category;

import android.annotation.SuppressLint;
import android.content.Context;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.note_and_todo_app.database.Database;
import com.example.note_and_todo_app.database.task.TaskCategory;
import com.example.note_and_todo_app.database.task.TaskDao;

import java.util.List;

public class TaskCategoryViewModel extends ViewModel {

	@SuppressLint("StaticFieldLeak")
	private final Context context;

	public MutableLiveData<List<TaskCategory>> categoriesLiveData = new MutableLiveData<>();

	public TaskCategoryViewModel(Context context) {
		this.context = context;
	}

	public void fetchAllCategories() {
		TaskDao taskDao = Database.getInstance(context).taskDao();
		List<TaskCategory> categories = taskDao.getAllCategory();
		for (TaskCategory category : categories) {
			category.setNumberOfItem(taskDao.getNumberOfTask(category.getId()));
		}
		categoriesLiveData.postValue(categories);
	}
}
