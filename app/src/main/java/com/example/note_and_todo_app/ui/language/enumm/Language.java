package com.example.note_and_todo_app.ui.language.enumm;

import androidx.recyclerview.widget.RecyclerView;

import com.example.note_and_todo_app.ui.language.LanguageFragment;

public class Language {
    private String codeLanguage;
    private int nameLanguageRes;
    private int imageRes;
    private Boolean isSelected = false;

    public Language() {

    }

    public Language(String codeLanguage, int nameLanguageRes, int imageRes, Boolean isSelected) {
        this.codeLanguage = codeLanguage;
        this.nameLanguageRes = nameLanguageRes;
        this.imageRes = imageRes;
        this.isSelected = isSelected;
    }

    public Language(String codeLanguage, int nameLanguageRes, int imageRes) {
        this.codeLanguage = codeLanguage;
        this.nameLanguageRes = nameLanguageRes;
        this.imageRes = imageRes;
    }

    public String getCodeLanguage() {
        return codeLanguage;
    }

    public void setCodeLanguage(String codeLanguage) {
        this.codeLanguage = codeLanguage;
    }

    public int getNameLanguageRes() {
        return nameLanguageRes;
    }

    public void setNameLanguageRes(int nameLanguageRes) {
        this.nameLanguageRes = nameLanguageRes;
    }

    public int getImageRes() {
        return imageRes;
    }

    public void setImageRes(int imageRes) {
        this.imageRes = imageRes;
    }

    public Boolean getSelected() {
        return isSelected;
    }

    public void setSelected(Boolean selected) {
        isSelected = selected;
    }
}
