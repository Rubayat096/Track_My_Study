package com.example.trackmystudy;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {
    // Duration of splash screen in milliseconds
    private static final int SPLASH_DURATION = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);



        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Start the main menu (MainActivity)
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                // Close SplashActivity so user can't go back to it
                finish();
            }
        }, SPLASH_DURATION);
    }
}
