package com.example.note_and_todo_app.database.note;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


import java.util.Date;

@Entity(tableName = "note")
public class Note {
	@PrimaryKey(autoGenerate = true)
	private Long id;


	public Note(String title, String info, String date) {
		this.title = title;
		this.info = info;
		this.date = date;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	@ColumnInfo(name = "title")
	public String title;

	@ColumnInfo(name = "info")
	public String info;

	@ColumnInfo(name = "date")
	public String date;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
}
