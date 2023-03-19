package com.example.note_and_todo_app.todo.all;

import com.example.note_and_todo_app.database.task.Task;

import java.util.Calendar;
import java.util.List;

public class TasksWithTitle {
    private String title;
    private List<Task> tasks;
    boolean canShowRV = false;
    Calendar calendar = null;
    public TasksWithTitle(String title, List<Task> tasks) {
        this.title = title;
        this.tasks = tasks;
    }

    public TasksWithTitle(String title, List<Task> tasks, boolean canShowRV) {
        this.title = title;
        this.tasks = tasks;
        this.canShowRV = canShowRV;
    }
    public TasksWithTitle(String title, List<Task> tasks, boolean canShowRV, Calendar calendar) {
        this.title = title;
        this.tasks = tasks;
        this.canShowRV = canShowRV;
        this.calendar = calendar;
    }

    public void addTask(Task task) {
        tasks.add(task);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }
}
