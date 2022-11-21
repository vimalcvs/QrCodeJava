package com.nvd.qrcode;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.MobileAds;

public class LaunchActivity extends AppCompatActivity {

    Boolean checkFirst;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        SharedPreferences spr = getSharedPreferences("myLanguage", MODE_PRIVATE);
        checkFirst = spr.getBoolean("flagFirst", false);


        MobileAds.initialize(this, initializationStatus -> {});

        new Handler().postDelayed(() -> {
                Intent i = new Intent(this, MainActivity.class);
                startActivity(i);
        }, 1000);
    }
}