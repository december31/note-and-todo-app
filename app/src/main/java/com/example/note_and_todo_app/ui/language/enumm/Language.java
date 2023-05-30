package com.example.note_and_todo_app.ui.language.enumm;

import androidx.recyclerview.widget.RecyclerView;

import com.example.note_and_todo_app.ui.language.LanguageFragment;

public class Language{
    String codeLanguage;
    String nameLanguage;
    String image;
    public Language(){

    }

    public Language(String codeLanguage, String nameLanguage, String image) {
        this.codeLanguage = codeLanguage;
        this.nameLanguage = nameLanguage;
        this.image = image;
    }

    public String getCodeLanguage() {
        return codeLanguage;
    }

    public void setCodeLanguage(String codeLanguage) {
        this.codeLanguage = codeLanguage;
    }

    public String getNameLanguage() {
        return nameLanguage;
    }

    public void setNameLanguage(String nameLanguage) {
        this.nameLanguage = nameLanguage;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
