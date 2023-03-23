package com.example.note_and_todo_app.ui.main;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.view.*;
import android.widget.EditText;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;

import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;
import com.example.note_and_todo_app.R;
import com.example.note_and_todo_app.database.Database;
import com.example.note_and_todo_app.database.task.TaskCategory;
import com.example.note_and_todo_app.database.task.TaskDao;
import com.example.note_and_todo_app.databinding.ActivityMainBinding;
import com.example.note_and_todo_app.services.TodoListServices;
import com.example.note_and_todo_app.utils.Constants;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.lang.reflect.Executable;
import java.util.Calendar;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {
    private final String TAG = MainActivity.class.toString();
    private ActivityMainBinding binding;
    private Intent intentService;
    private Handler mainThread;

    @Override
    @SuppressWarnings("deprecation")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(LayoutInflater.from(this), null, false);

        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.O) {
            supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
            this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }

        binding.getRoot().setOnClickListener(v -> {
            closeKeyboard();
        });

        setContentView(binding.getRoot());
        hideSystemUI();
        generateDefaultCategory();
        configBannerAds();
        startAppService();
        requestDrawOverlay();
    }

    private void startAppService() {
        if (intentService == null) {
            intentService = new Intent(this, TodoListServices.class);
        }
        startForegroundService(intentService);
    }

    private void stopAppService() {
        if (intentService == null) return;
        stopService(intentService);
    }

    private void requestDrawOverlay() {
        if (!Settings.canDrawOverlays(getApplicationContext())) {
            AlertDialog alertDialog = new AlertDialog.Builder(this).create();

            alertDialog.setTitle(getString(R.string.permission_required));
            alertDialog.setMessage(getString(R.string.request_draw_overlay));
            alertDialog.setIcon(R.drawable.ic_check_blue);

            alertDialog.setButton(Dialog.BUTTON_POSITIVE, "OK", (dialog, which) -> {
                    Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
                    intent.setData(Uri.parse("package:" + getPackageName()));
                    startActivity(intent);
            });

            alertDialog.setButton(Dialog.BUTTON_NEGATIVE, "Cancel", (dialog, which) -> alertDialog.dismiss());
            alertDialog.show();
        }
    }

    private void generateDefaultCategory() {
        TaskDao taskDao = Database.getInstance(this).taskDao();
        if (!taskDao.isCategoryExists(Constants.DEFAULT_CATEGORY_ID)) {
            taskDao.insertCategory(new TaskCategory(Constants.DEFAULT_CATEGORY_ID, "Personal", Calendar.getInstance().getTimeInMillis()));
        }
    }

    private void hideSystemUI() {
        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);
        WindowInsetsControllerCompat controller = new WindowInsetsControllerCompat(getWindow(), binding.getRoot());
        controller.hide(WindowInsetsCompat.Type.systemBars());
        controller.setSystemBarsBehavior(WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE);
    }

    private void configBannerAds() {
        MobileAds.initialize(this);
        AdView adView = new AdView(this);
        adView.setAdSize(new AdSize(displayWidth(), Constants.BANNER_ADS_HEIGHT));
        adView.setAdUnitId(Constants.BANNER_ADS_ID);
        binding.adViewContainer.addView(adView);

        AdRequest request = new AdRequest.Builder().build();
        adView.loadAd(request);
    }

    public void closeKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            manager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private int displayWidth() {
        DisplayMetrics displayMetrics = this.getResources().getDisplayMetrics();
        return (int) (displayMetrics.widthPixels / displayMetrics.density);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if ( v instanceof EditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int)event.getRawX(), (int)event.getRawY())) {
                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        }
        return super.dispatchTouchEvent(event);
    }
}