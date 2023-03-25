package com.example.note_and_todo_app.ui.setting;

public class SettingItem {
    private int titleRes;
    private Type type;
    private boolean hasSwitch;

    private boolean checked = true;

    public SettingItem(int titleRes, Type type, boolean hasSwitch) {
        this.titleRes = titleRes;
        this.type = type;
        this.hasSwitch = hasSwitch;
    }
    public SettingItem(int titleRes, Type type, boolean hasSwitch, boolean checked) {
        this.titleRes = titleRes;
        this.type = type;
        this.hasSwitch = hasSwitch;
        this.checked = checked;
    }

    public int getTitleRes() {
        return titleRes;
    }

    public void setTitleRes(int titleRes) {
        this.titleRes = titleRes;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public boolean isHasSwitch() {
        return hasSwitch;
    }

    public void setHasSwitch(boolean hasSwitch) {
        this.hasSwitch = hasSwitch;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public enum Type {
        LANGUAGE,
        NOTIFICATION,
        REPORT_BUGS,
        SHARE,
        RATE,
        SHOW_COMPLETED_TASK,
        CATEGORY
    }

}
