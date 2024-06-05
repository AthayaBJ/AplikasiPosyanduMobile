package com.example.aplikasiposyandu_mobile;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.aplikasiposyandu_mobile.R; // pastikan ini sesuai dengan package yang benar
import com.google.firebase.auth.FirebaseAuth;

public class SplashScreenActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        mAuth = FirebaseAuth.getInstance();

        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences sharedPreferences = getSharedPreferences(LoginActivity.SHARED_PREF_NAME, 0);
                boolean logged = sharedPreferences.getBoolean("masuk", false);

                try {
                    if (logged) {
                        startActivity(new Intent(SplashScreenActivity.this, MainActivity.class));
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                        finish();
                    } else {
                        startActivity(new Intent(SplashScreenActivity.this, LandingActivity.class));
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                        finish();
                    }
                } catch (Exception e) {
                    Log.e("Splash Error", e.toString());
                }
            }
        }, 3000);
    }
}
