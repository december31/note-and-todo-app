package com.example.note_and_todo_app;

import android.util.Log;
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
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {
	private final String TAG = MainActivity.class.toString();
	private ActivityMainBinding binding;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		binding = ActivityMainBinding.inflate(LayoutInflater.from(this), null, false);
		setContentView(binding.getRoot());
		hideSystemUI();

		setUpNavigation();
		configBannerAds();
	}

	private void setUpNavigation() {
	}

	private void hideSystemUI() {
		WindowCompat.setDecorFitsSystemWindows(getWindow(), false);
		WindowInsetsControllerCompat controller = new WindowInsetsControllerCompat(getWindow(), binding.getRoot());
		controller.hide(WindowInsetsCompat.Type.systemBars());
		controller.setSystemBarsBehavior(WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE);
	}

	private void configBannerAds() {
		MobileAds.initialize(this);
		AdRequest request = new AdRequest.Builder().build();
		binding.adView.loadAd(request);
	}
}