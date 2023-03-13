package com.example.note_and_todo_app.database.task;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Relation;

import java.util.List;

@Entity(
		tableName = "task_with_category",
		primaryKeys = {"id", "category_id"}
)
public class TaskWithCategory {
	@Embedded public TaskCategory taskCategory;
	@Relation(parentColumn = "id", entityColumn = "id")
	public List<Task> tasks;
}
