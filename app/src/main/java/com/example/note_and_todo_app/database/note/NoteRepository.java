package com.example.note_and_todo_app.database.note;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.note_and_todo_app.database.Database;

import java.util.List;

public class NoteRepository {
    NoteDao noteDao;
    LiveData<List<Note>> readAllData ;
    LiveData<List<Note>> queryId ;

    public NoteRepository(Application application) {
        Database db = Database.getInstance(application);
        this.noteDao = db.noteDao();
        this.readAllData = this.noteDao.getAllNote();

    }
    LiveData<List<Note>> getAllNote() {
        return readAllData;
    }
    LiveData<List<Note>> getQueryId(){
        return queryId;
    }
    void insert(Note note) {
        Database.databaseWriteExecutor.execute(() -> {
            noteDao.insertAll(note);
        });
    }
    long add(Note note){
        return noteDao.insertNote(note);
    }
    void update(Note note){
            noteDao.update(note);
    }
    void delete(Note note){
        noteDao.delete(note);
    }
    void find(Note note){noteDao.findByTitle(note.getTitle());}

}
