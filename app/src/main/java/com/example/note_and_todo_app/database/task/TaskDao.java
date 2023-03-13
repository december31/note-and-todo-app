package com.example.note_and_todo_app.database.task;

import androidx.room.*;

import java.util.List;

@Dao
public interface TaskDao {
	@Query("select * from task")
	List<Task> getAll();
	@Update
	void update(Task... tasks);
	@Delete
	void delete(Task... tasks);
	@Insert
	void insert(Task... tasks);
	@Insert
	void insertCategory(TaskCategory... categories);
	@Transaction
	@Query("select * from task where task.category_id = :id")
	List<Task> getTaskByCategory(Long id);

	@Query("select * from task_category")
	List<TaskCategory> getAllCategory();

	@Query("select count(*) from task where task.id = :categoryId")
	Integer getNumberOfTask(Long categoryId);

}
