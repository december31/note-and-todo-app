package com.example.note_and_todo_app.database.task;

import androidx.annotation.NonNull;
import androidx.room.*;

import java.util.Date;

@Entity(tableName = "task")
public class Task {
	@PrimaryKey(autoGenerate = true)
	private Long id;

	@ColumnInfo(name = "category_id")
	private Long categoryId;
	@ColumnInfo(name = "create_at")
	private Long createdAt;
	private String title;
	private TaskState state;
	private Long dueDate;

	@Ignore
	public Task(Long id, Long categoryId, Long createdAt, String title, TaskState state, Long dueDate) {
		this.id = id;
		this.categoryId = categoryId;
		this.createdAt = createdAt;
		this.title = title;
		this.state = state;
		this.dueDate = dueDate;
	}

	@Ignore
	public Task(Long categoryId, Long createdAt, String title, TaskState state, Long dueDate) {
		this.categoryId = categoryId;
		this.createdAt = createdAt;
		this.title = title;
		this.state = state;
		this.dueDate = dueDate;
	}


	public Task() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public Long getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Long createdAt) {
		this.createdAt = createdAt;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public TaskState getState() {
		return state;
	}

	public void setState(TaskState state) {
		this.state = state;
	}

	public Long getDueDate() {
		return dueDate;
	}

	public void setDueDate(Long dueDate) {
		this.dueDate = dueDate;
	}
}
