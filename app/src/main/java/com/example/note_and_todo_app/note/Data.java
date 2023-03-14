package com.example.note_and_todo_app.note;

import java.sql.Date;

public class Data {
    String title="";
    String info="";
    Date date = null;

    public Data(String title, String info, Date date) {
        this.title = title;
        this.info = info;
        this.date = date;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
