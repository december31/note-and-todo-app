package com.example.note_and_todo_app;

import android.view.LayoutInflater;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.navigation.NavController;
import androidx.navigation.NavHostController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;
import com.example.note_and_todo_app.database.Database;
import com.example.note_and_todo_app.database.task.TaskCategory;
import com.example.note_and_todo_app.database.task.TaskDao;
import com.example.note_and_todo_app.databinding.ActivityMainBinding;

import java.time.LocalDate;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

	private ActivityMainBinding binding;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		binding = ActivityMainBinding.inflate(LayoutInflater.from(this), null, false);
		setContentView(binding.getRoot());
		hideSystemUI();

		setUpNavigation();

	}

	private void setUpNavigation() {
	}

	private void hideSystemUI() {
		WindowCompat.setDecorFitsSystemWindows(getWindow(), false);
		WindowInsetsControllerCompat controller = new WindowInsetsControllerCompat(getWindow(), binding.getRoot());
		controller.hide(WindowInsetsCompat.Type.systemBars());
		controller.setSystemBarsBehavior(WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE);
	}
}