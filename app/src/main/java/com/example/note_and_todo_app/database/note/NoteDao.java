package com.example.note_and_todo_app.database.note;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.Date;
import java.util.List;

@Dao
public interface NoteDao {
    @Query("SELECT * FROM note")
    LiveData<List<Note>> getAllNote();


    @Query("SELECT * FROM note WHERE id = :noteId ")
    Note getNote(Long noteId);

    @Query("SELECT * FROM note WHERE title LIKE :title  LIMIT 1")
    Note findByTitle(String title);

    @Query("DELETE  FROM note")
    void deleteAll();

    @Query("SELECT time FROM note WHERE id = :noteID")
    Long getTimeNote(long noteID);
    @Query("SELECT isTime FROM note WHERE id = :noteID")
    Boolean getIsTimeNote(long noteID);
    @Query("SELECT * FROM note")
    List<Note> getAllTime();
    @Insert
    void insertAll(Note... users);
    @Insert
    long insertNote(Note note);
    @Delete
    void delete(Note note);
    @Update
    void update(Note note);
}
