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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TaskCategoryAdapter extends RecyclerView.Adapter<TaskCategoryAdapter.TaskCategoryViewHolder> {

	private final ArrayList<TaskCategory> items = new ArrayList<>();
	private final OnTaskCategoryItemClickListener listener;
	private final Map<Long, Boolean> hasAnimated = new HashMap<>();

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
		TaskCategory item = position < items.size() ? items.get(position) : null;
		holder.bind(item, position);
	}

	@Override
	public int getItemCount() {
		return items.size() + 1;
	}

	public void updateItem(List<TaskCategory> categories) {
		DiffUtil.DiffResult result = DiffUtil.calculateDiff(new CustomDiffUtilsCallback<>(items, categories));
		items.clear();
		items.addAll(categories);
		result.dispatchUpdatesTo(this);
	}

	private void setAnimation(View view, int position) {
		Animation animation;
		if (position % 2 == 0) {
			animation = AnimationUtils.loadAnimation(view.getContext(), R.anim.item_animation_slide_from_left);
		} else {
			animation = AnimationUtils.loadAnimation(view.getContext(), R.anim.item_animation_slide_from_right);
		}
		animation.setStartOffset(position * Constants.ANIMATION_OFFSET);
		animation.setDuration(Constants.ANIMATION_DURATION);
		view.startAnimation(animation);
	}

	protected class TaskCategoryViewHolder extends RecyclerView.ViewHolder {
		private final LayoutTaskCategoryItemBinding binding;
		public TaskCategoryViewHolder(@NonNull @NotNull LayoutTaskCategoryItemBinding binding) {
			super(binding.getRoot());
			this.binding = binding;
		}

		void bind(TaskCategory item, int position) {
			binding.setData(item);
			binding.getRoot().setOnClickListener(v -> listener.onItemClick(item));
			if (item != null) {
				if (!Boolean.TRUE.equals(hasAnimated.get(item.getId()))) {
					setAnimation(binding.getRoot(), position);
					hasAnimated.put(item.getId(), true);
				}
			}
		}
	}

	public interface OnTaskCategoryItemClickListener {
		void onItemClick(@Nullable TaskCategory data);
	}
}
