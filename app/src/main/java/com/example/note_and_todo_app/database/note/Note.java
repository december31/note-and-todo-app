package com.example.note_and_todo_app.database.note;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;


import java.util.Date;

@Entity(tableName = "note")
public class Note {
	@PrimaryKey(autoGenerate = true)
	private Long id;

	public Note(){
	}
	@Ignore
	public Note(String title, String info, String date) {
		this.title = title;
		this.info = info;
		this.date = date;
	}
	@Ignore
	public Note(Long id, String title, String info, String date) {
		this.id = id;
		this.title = title;
		this.info = info;
		this.date = date;
	}
	@Ignore
	public Note(Long id, String title, String info, String date, Boolean check) {
		this.id = id;
		this.title = title;
		this.info = info;
		this.date = date;
		this.check = check;
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

	@ColumnInfo(name = "chek")
	public Boolean check = false;

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

	public Boolean getCheck() {
		return check;
	}

	public void setCheck(Boolean check) {
		this.check = check;
	}
}
