package com.example.note_and_todo_app.database.task;

import androidx.room.*;

import java.util.List;

@Dao
public interface TaskDao {
	@Query("select * from task")
	List<Task> getAll();
	@Update
	void update(Task... tasks);
	@Update
	void update(TaskCategory... categories);
	@Delete
	void delete(Task... tasks);
	@Insert
	void insert(Task... tasks);
	@Insert
	void insertCategory(TaskCategory... categories);
	@Transaction
	@Query("select * from task where task.category_id = :id")
	List<Task> getTaskByCategory(Long id);

	@Query("select * from task_category order by id asc")
	List<TaskCategory> getAllCategory();

	@Query("select exists(select * from task_category where id = :categoryId)")
	Boolean isCategoryExists(Long categoryId);

	@Query("select count(*) from task where task.category_id = :categoryId")
	Integer getNumberOfTask(Long categoryId);

	@Query("select * from task where title like :title||'%'")
	List<Task> getTaskByTitle(String title);

	@Query("select exists(select * from task where state = :state)")
	Boolean isHavingTaskWithState(TaskState state);
}
