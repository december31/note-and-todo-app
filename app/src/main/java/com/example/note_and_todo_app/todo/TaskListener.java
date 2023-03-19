package com.example.note_and_todo_app.todo;

import androidx.annotation.Nullable;
import com.example.note_and_todo_app.database.task.Task;
import com.example.note_and_todo_app.todo.all.TasksWithTitle;

public interface TaskListener {
    void onStatusChange(Task task, int position);

    void deleteTask(Task task, int position);

    void createNewTasks(TasksWithTitle tasksWithTitle);

}
