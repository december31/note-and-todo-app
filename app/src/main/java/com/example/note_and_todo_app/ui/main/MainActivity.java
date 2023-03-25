package com.example.note_and_todo_app.ui.main;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.*;
import android.widget.EditText;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;

import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.lifecycle.MutableLiveData;
import com.example.note_and_todo_app.BuildConfig;
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
import com.google.android.gms.tasks.Task;
import com.google.android.play.core.review.ReviewInfo;
import com.google.android.play.core.review.ReviewManager;
import com.google.android.play.core.review.ReviewManagerFactory;

import java.util.Calendar;

@SuppressWarnings("deprecation")
public class MainActivity extends AppCompatActivity {
    private final String TAG = MainActivity.class.toString();
    private ActivityMainBinding binding;
    private Intent intentService;
    private static final int DRAW_OVERLAY_REQUEST_CODE = 1234;
    public static final MutableLiveData<Boolean> isShowNotification = new MutableLiveData<>(true);

    private static boolean check = true;

    @Override
    @SuppressWarnings("deprecation")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(LayoutInflater.from(this), null, false);

        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.O) {
            supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }

        setContentView(binding.getRoot());
        hideSystemUI();
        generateDefaultCategory();
        configBannerAds();
    }

    @Override
    protected void onResume() {
        super.onResume();
        View mDecorView = getWindow().getDecorView();
        mDecorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                        | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }

    public void startAppService() {
        if (requestDrawOverlay()) {
            if (intentService == null) {
                intentService = new Intent(this, TodoListServices.class);
            }
            startForegroundService(intentService);
        }
    }

    public void stopAppService() {
        if (intentService == null) return;
        stopService(intentService);
    }

    private boolean requestDrawOverlay() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q && !Settings.canDrawOverlays(getApplicationContext())) {
            AlertDialog alertDialog = new AlertDialog.Builder(this).create();

            alertDialog.setTitle(getString(R.string.permission_required));
            alertDialog.setMessage(getString(R.string.request_draw_overlay));
            alertDialog.setIcon(R.drawable.ic_check_blue);

            alertDialog.setButton(Dialog.BUTTON_POSITIVE, "OK", (dialog, which) -> {
                check = false;
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
                intent.setData(Uri.parse("package:" + getPackageName()));
                startActivityForResult(intent, DRAW_OVERLAY_REQUEST_CODE);
            });
            alertDialog.setButton(Dialog.BUTTON_NEGATIVE, "Cancel", (dialog, which) -> alertDialog.dismiss());
            alertDialog.setOnDismissListener(dialog -> {
                if (check) {
                    isShowNotification.postValue(false);
                }
                check = true;
            });
            alertDialog.show();
            return false;
        } else {
            return true;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == DRAW_OVERLAY_REQUEST_CODE) {
            if (Settings.canDrawOverlays(getApplicationContext())) {
                startAppService();
                isShowNotification.postValue(true);
            } else {
                isShowNotification.postValue(false);
            }
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
        adView.setAdUnitId(BuildConfig.BANNER_ADS_ID);
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
            if (v instanceof EditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int) event.getRawX(), (int) event.getRawY())) {
                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        }
        return super.dispatchTouchEvent(event);
    }

    public void inAppReview() {
        ReviewManager manager = ReviewManagerFactory.create(getApplicationContext());
        Task<ReviewInfo> request = manager.requestReviewFlow();
        request.addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                ReviewInfo reviewInfo = task.getResult();
                manager.launchReviewFlow(this, reviewInfo);
            }
        });
    }

    public void shareApp() {
        try {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "My application name");
            String shareMessage = "\nLet me recommend you this application: ";
            shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID + "\n\n";
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
            startActivity(Intent.createChooser(shareIntent, "choose one"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void rateApp() {
        try {
            Intent rateIntent = rateIntentForUrl("market://details");
            startActivity(rateIntent);
        } catch (ActivityNotFoundException e) {
            Intent rateIntent = rateIntentForUrl("https://play.google.com/store/apps/details");
            startActivity(rateIntent);
        }
    }

    private Intent rateIntentForUrl(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(String.format("%s?id=%s", url, getPackageName())));
        int flags = Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_MULTIPLE_TASK;
        flags |= Intent.FLAG_ACTIVITY_NEW_DOCUMENT;
        intent.addFlags(flags);
        return intent;
    }

    public void reportBugs() {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:anpytn30122002@gmail.com")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "Hey, i found a bug on your todo list");
        startActivity(intent);
    }
}