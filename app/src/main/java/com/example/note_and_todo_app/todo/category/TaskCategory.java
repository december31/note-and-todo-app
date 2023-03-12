package com.example.note_and_todo_app.todo.category;

public class TaskCategory {
	private String title;
	private String numberOfTasks;

	public TaskCategory() {}

	public TaskCategory(String title, String numberOfTasks) {
		this.title = title;
		this.numberOfTasks = numberOfTasks;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getNumberOfTasks() {
		return numberOfTasks;
	}

	public void setNumberOfTasks(String numberOfTasks) {
		this.numberOfTasks = numberOfTasks;
	}
}
