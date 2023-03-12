package com.example.note_and_todo_app.database.note;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Note {
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@PrimaryKey(autoGenerate = true)
	private Long id;
}
