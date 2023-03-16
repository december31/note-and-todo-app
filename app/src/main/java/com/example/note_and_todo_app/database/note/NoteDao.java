package com.example.note_and_todo_app.database.note;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface NoteDao {
    @Query("SELECT * FROM note")
    LiveData<List<Note>> getAllNote();


    @Query("SELECT * FROM note WHERE id IN (:userIds)")
    List<Note> loadAllByIds(int[] userIds);

    @Query("SELECT * FROM note WHERE title LIKE :first AND " +
            "info LIKE :last LIMIT 1")
    Note findByName(String first, String last);

    @Insert
    void insertAll(Note... users);
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Note note);
    @Delete
    void delete(Note note);
}
