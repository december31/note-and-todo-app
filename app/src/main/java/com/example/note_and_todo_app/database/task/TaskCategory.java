package com.example.note_and_todo_app.database.task;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class TaskCategory {
	@PrimaryKey(autoGenerate = true)
	private Long id;
	private String title;
	@ColumnInfo(name = "create_at")
	private Long createAt;

	@Ignore
	public TaskCategory(Long id, String title, Long createAt) {
		this.id = id;
		this.title = title;
		this.createAt = createAt;
	}

	@Ignore
	public TaskCategory(String title, Long createAt) {
		this.title = title;
		this.createAt = createAt;
	}

	public TaskCategory() {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Long getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Long createAt) {
		this.createAt = createAt;
	}
}
