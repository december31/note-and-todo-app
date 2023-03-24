package com.example.note_and_todo_app.database.task;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import com.example.note_and_todo_app.ui.base.BaseItem;

@Entity(tableName = "task_category")
public class TaskCategory extends BaseItem {
	@PrimaryKey(autoGenerate = true)
	private Long id;
	private String title;
	@ColumnInfo(name = "create_at")
	private Long createAt;
	@Ignore
	private Integer numberOfItem;
	@Ignore
	public TaskCategory(Long id, String title, Long createAt) {
		this.id = id;
		this.title = title;
		this.createAt = createAt;
	}

	@Ignore
	public TaskCategory(String title, Long createAt) {
		this.title = title;
		this.createAt = createAt;
	}

	public TaskCategory() {}

	@Override
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Long getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Long createAt) {
		this.createAt = createAt;
	}

	public Integer getNumberOfItem() {
		return numberOfItem;
	}

	public void setNumberOfItem(Integer numberOfItem) {
		this.numberOfItem = numberOfItem;
	}

	@Ignore
	@Override
	public int compareTo(BaseItem o) {
		TaskCategory that = (TaskCategory) o;
		if (
				this.id.equals(that.id) &&
				this.title.equals(that.title) &&
				this.createAt.equals(that.createAt) &&
				this.numberOfItem.equals(that.numberOfItem)
		) {
			return 0;
		} else if (this.id < that.id) {
			return -1;
		}
		return 1;
	}
}
