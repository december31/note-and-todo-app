package com.example.note_and_todo_app.ui.base;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.example.note_and_todo_app.R;
import com.example.note_and_todo_app.database.note.NoteViewModel;

import org.jetbrains.annotations.NotNull;

public class BaseToolBar extends LinearLayoutCompat {
	private final String TAG = this.toString();
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
