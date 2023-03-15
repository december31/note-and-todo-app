package com.example.note_and_todo_app.database.task;

import androidx.room.*;
import com.example.note_and_todo_app.base.BaseItem;

@Entity(tableName = "task")
public class Task extends BaseItem {
    @PrimaryKey(autoGenerate = true)
    private Long id;

    @ColumnInfo(name = "category_id")
    private Long categoryId;
    @ColumnInfo(name = "create_at")
    private Long createdAt;
    private String title;
    private TaskState state;
    private Long dueDate;

    @Ignore
    public Task(Long id, Long categoryId, Long createdAt, String title, TaskState state, Long dueDate) {
        this.id = id;
        this.categoryId = categoryId;
        this.createdAt = createdAt;
        this.title = title;
        this.state = state;
        this.dueDate = dueDate;
    }

    @Ignore
    public Task(Long categoryId, Long createdAt, String title, TaskState state, Long dueDate) {
        this.categoryId = categoryId;
        this.createdAt = createdAt;
        this.title = title;
        this.state = state;
        this.dueDate = dueDate;
    }


    public Task() {
    }

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public TaskState getState() {
        return state;
    }

    public void setState(TaskState state) {
        this.state = state;
    }

    public Long getDueDate() {
        return dueDate;
    }

    public void setDueDate(Long dueDate) {
        this.dueDate = dueDate;
    }

    @Override
    public int compareTo(BaseItem o) {
        Task that = (Task) o;
        if (
                this.id.equals(that.id) &&
                this.categoryId.equals(that.categoryId) &&
                this.createdAt.equals(that.createdAt) &&
                this.title.equals(that.title) &&
                this.state.equals(that.state) &&
                this.dueDate.equals(that.dueDate)
        ) {
            return 0;
        } else if (this.id < that.id) {
            return -1;
        }
        return 1;
    }
}
