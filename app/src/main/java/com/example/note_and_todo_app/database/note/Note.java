package com.example.note_and_todo_app.database.note;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;


import java.util.Date;

@Entity(tableName = "note")
public class Note {
	@PrimaryKey(autoGenerate = true)
	private Long id;

	public Note(){
	}
	@Ignore
	public Note(String title, String info, String date,String imagePath) {
		this.title = title;
		this.info = info;
		this.date = date;
		this.imagePath = imagePath;
	}
	@Ignore
	public Note(Long id, String title, String info, String date) {
		this.id = id;
		this.title = title;
		this.info = info;
		this.date = date;
	}
	@Ignore
	public Note(Long id, String title, String info, String date, String imagePath) {
		this.id = id;
		this.title = title;
		this.info = info;
		this.date = date;
		this.imagePath = imagePath;
	}
	@Ignore
	public Note(Long id,Long time, Boolean isTime) {
		this.id = id;
		this.timeSet = time;
		this.isTime = isTime;
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


	@ColumnInfo(name = "image_path")
	public String imagePath;

	@ColumnInfo(name = "time")
	public Long timeSet = 0L;

	@ColumnInfo(name = "isTime")
	public Boolean isTime = false;

	@TypeConverter
	public static Date time(Long value) {
		return value == null ? null : new Date(value);
	}
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

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public Long getTime() {
		return timeSet;
	}

	public void setTime(Boolean time) {
		isTime = time;
	}

	public void setTime(Long time) {
		this.timeSet = time;
	}

}
