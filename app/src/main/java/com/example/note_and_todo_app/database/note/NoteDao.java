package com.example.note_and_todo_app.database.note;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface NoteDao {
    @Query("SELECT * FROM note")
    LiveData<List<Note>> getAllNote();


    @Query("SELECT * FROM note WHERE id = :noteId ")
    Note getNote(Long noteId);

    @Query("SELECT * FROM note WHERE title LIKE :title  LIMIT 1")
    Note findByTitle(String title);

    @Insert
    void insertAll(Note... users);
    @Insert
    long insertNote(Note note);
    @Delete
    void delete(Note note);
    @Update
    void update(Note note);
}
