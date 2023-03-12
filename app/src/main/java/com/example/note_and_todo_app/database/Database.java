package com.example.note_and_todo_app.database;

import android.content.Context;
import androidx.room.Entity;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import com.example.note_and_todo_app.database.note.Note;
import com.example.note_and_todo_app.database.note.NoteDao;
import com.example.note_and_todo_app.database.task.Task;
import com.example.note_and_todo_app.database.task.TaskCategory;
import com.example.note_and_todo_app.database.task.TaskDao;

@androidx.room.Database(entities = {Task.class, TaskCategory.class, Note.class}, version = 1)
public abstract class Database extends RoomDatabase {
	public abstract TaskDao taskDao();
	public abstract NoteDao noteDao();
	private static Database INSTANCE;
	public static Database getInstance(Context context) {
		if (INSTANCE == null) {
			INSTANCE = Room.databaseBuilder(context, Database.class, "note_and_todo_list").build();
		}
		return INSTANCE;
	}
}
