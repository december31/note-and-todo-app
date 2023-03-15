package com.example.note_and_todo_app.todo.category;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.example.note_and_todo_app.R;
import com.example.note_and_todo_app.base.CustomDiffUtilsCallback;
import com.example.note_and_todo_app.database.task.TaskCategory;
import com.example.note_and_todo_app.databinding.LayoutTaskCategoryItemBinding;
import com.example.note_and_todo_app.utils.Constants;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class TaskCategoryAdapter extends RecyclerView.Adapter<TaskCategoryAdapter.TaskCategoryViewHolder> {

	private final ArrayList<TaskCategory> items = new ArrayList<>();
	private final OnTaskCategoryItemClickListener listener;

	TaskCategoryAdapter(OnTaskCategoryItemClickListener listener) {
		this.listener = listener;
	}

	@NonNull
	@NotNull
	@Override
	public TaskCategoryViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
		return new TaskCategoryViewHolder(
				LayoutTaskCategoryItemBinding.inflate(
						LayoutInflater.from(parent.getContext()),
						parent, false)
		);
	}

	@Override
	public void onBindViewHolder(@NonNull @NotNull TaskCategoryViewHolder holder, int position) {
		TaskCategory item;
		if (position < items.size()) {
			item = items.get(position);
			holder.binding.setData(items.get(position));
		} else {
			item = null;
		}
		holder.binding.getRoot().setOnClickListener(v -> listener.onItemClick(item));
		setAnimation(holder.binding.getRoot(), position);
	}

	@Override
	public int getItemCount() {
		return items.size() + 1;
	}

	@SuppressLint("NotifyDataSetChanged")
	public void updateItem(List<TaskCategory> categories) {
		DiffUtil.DiffResult result = DiffUtil.calculateDiff(new CustomDiffUtilsCallback<>(items, categories));
		items.clear();
		items.addAll(categories);
		result.dispatchUpdatesTo(this);
	}

	private void setAnimation(View view, int position) {
		Animation animation = AnimationUtils.loadAnimation(view.getContext(), R.anim.item_animation_fall_down);
		animation.setStartOffset(position * Constants.ANIMATION_OFFSET);
		animation.setDuration(Constants.ANIMATION_DURATION);
		view.startAnimation(animation);
	}

	protected static class TaskCategoryViewHolder extends RecyclerView.ViewHolder {
		public LayoutTaskCategoryItemBinding binding;
		public TaskCategoryViewHolder(@NonNull @NotNull LayoutTaskCategoryItemBinding binding) {
			super(binding.getRoot());
			this.binding = binding;
		}
	}

	public interface OnTaskCategoryItemClickListener {
		void onItemClick(@Nullable TaskCategory data);
	}
}
