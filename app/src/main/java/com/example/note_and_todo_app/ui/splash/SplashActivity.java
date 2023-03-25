package com.example.note_and_todo_app.ui.splash;

import android.annotation.SuppressLint;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.Window;
import android.view.WindowManager;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.navigation.Navigation;
import androidx.work.impl.model.Preference;
import com.example.note_and_todo_app.BuildConfig;
import com.example.note_and_todo_app.R;
import com.example.note_and_todo_app.databinding.ActivitySplashBinding;
import com.example.note_and_todo_app.preferences.Preferences;
import com.google.android.gms.ads.*;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import org.jetbrains.annotations.NotNull;

@SuppressLint("CustomSplashScreen")
public class SplashActivity extends AppCompatActivity {

    private ActivitySplashBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySplashBinding.inflate(LayoutInflater.from(this), null, false);
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.O) {
            supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        }
        setContentView(binding.getRoot());
        hideSystemUI();
        configInterAds();
        Preferences.initPref(getApplicationContext());
    }
    private void hideSystemUI() {
        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);
        WindowInsetsControllerCompat controller = new WindowInsetsControllerCompat(getWindow(), binding.getRoot());
        controller.hide(WindowInsetsCompat.Type.systemBars());
        controller.setSystemBarsBehavior(WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE);
    }

    void configInterAds() {
        loadInterAds();
    }

    private void loadInterAds() {
        AdRequest adRequest = new AdRequest.Builder().build();
        InterstitialAd.load(this, BuildConfig.INTER_SPLASH_ADS_ID, adRequest, new InterstitialAdLoadCallback() {
            @Override
            public void onAdFailedToLoad(@NonNull @NotNull LoadAdError loadAdError) {
                startMainActivity();
            }

            @Override
            public void onAdLoaded(@NonNull @NotNull InterstitialAd interstitialAd) {
                startMainActivity();
                interstitialAd.show(SplashActivity.this);
            }
        });
    }

    private void startMainActivity() {
        Navigation.findNavController(binding.navHost).navigate(R.id.action_fragment_nav_splash_to_activity_main);
    }
}