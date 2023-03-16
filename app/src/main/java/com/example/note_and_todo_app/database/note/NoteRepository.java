package com.example.note_and_todo_app.database.note;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.note_and_todo_app.database.Database;

import java.util.List;

public class NoteRepository {
    NoteDao noteDao;
    LiveData<List<Note>> readAllData ;

    public NoteRepository(Application application) {
        Database db = Database.getInstance(application);
        this.noteDao = db.noteDao();
        this.readAllData = this.noteDao.getAllNote();
    }
    LiveData<List<Note>> getAllNote() {
        return readAllData;
    }
    void insert(Note note) {
        Database.databaseWriteExecutor.execute(() -> {
            noteDao.insertAll(note);
        });
    }

}
