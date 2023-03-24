package com.example.note_and_todo_app.database.note;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class NoteViewModel extends AndroidViewModel {
    private NoteRepository noteRepository;
    private final LiveData<List<Note>> allNote;
//    private final LiveData<List<Note>> queryId;
    public NoteViewModel(@NonNull Application application) {
        super(application);
        noteRepository = new NoteRepository(application);
        allNote = noteRepository.getAllNote();

    }
    public LiveData<List<Note>> getAllNote(){
        return allNote;
    }
    public  void  insert(Note note){
        noteRepository.insert(note);
    }
    public  long addNote(Note note){
        return noteRepository.add(note);
    }
    public  void update(Note note){
        noteRepository.update(note);
    }
    public void delete(Note note){
        noteRepository.delete(note);
    }
    public void deleteAll(){noteRepository.deleteAll();}
}
