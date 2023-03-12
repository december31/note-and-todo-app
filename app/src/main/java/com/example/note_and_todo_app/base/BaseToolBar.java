package com.example.note_and_todo_app.base;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.LinearLayoutCompat;
import com.example.note_and_todo_app.R;
import org.jetbrains.annotations.NotNull;

public class BaseToolBar extends LinearLayoutCompat {
	private String TAG = this.toString();
	public BaseToolBar(@NonNull @NotNull Context context) {
		super(context);
		init();
	}

	public BaseToolBar(@NonNull @NotNull Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public BaseToolBar(@NonNull @NotNull Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init();
	}

	public TextView title;
	public ImageView icLeft;
	public ImageView icRight;

	private void init() {
		View view = LayoutInflater.from(getContext()).inflate(R.layout.layout_custom_tool_bar, null);
		addView(view);
		title = view.findViewById(R.id.title);
		icLeft = view.findViewById(R.id.ic_left);
		icRight = view.findViewById(R.id.ic_right);
	}
}
