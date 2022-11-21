package com.nvd.qrcode.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.nvd.qrcode.MainActivity;
import com.nvd.qrcode.R;

import java.util.Locale;

public class ActivityLanguage extends AppCompatActivity {

    Boolean first = false;
    CheckBox checkEnglish, checkSpain, checkIndo, checkFranch, checkPortugal, checkChina;
    ImageView imgNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language);

        imgNext = findViewById(R.id.img_next);
        checkEnglish = findViewById(R.id.checkEnglish);
        checkSpain = findViewById(R.id.checkSpain);
        checkPortugal = findViewById(R.id.checkPortugal);
        checkFranch = findViewById(R.id.checkFranch);
        checkIndo = findViewById(R.id.checkIndo);
        checkChina = findViewById(R.id.checkChina);

        checkEnglish.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked){

                checkSpain.setChecked(false);
                checkIndo.setChecked(false);
                checkFranch.setChecked(false);
                checkPortugal.setChecked(false);
                checkChina.setChecked(false);

                first = true;
                test();
            }
        });

        checkSpain.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked){
                checkIndo.setChecked(false);
                checkFranch.setChecked(false);
                checkPortugal.setChecked(false);
                checkChina.setChecked(false);
                checkEnglish.setChecked(false);

                first = true;
                test();
            }
        });

        checkIndo.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked){
                checkFranch.setChecked(false);
                checkPortugal.setChecked(false);
                checkChina.setChecked(false);
                checkEnglish.setChecked(false);
                checkSpain.setChecked(false);

                first = true;
                test();
            }
        });

        checkPortugal.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked){
                checkFranch.setChecked(false);
                checkChina.setChecked(false);
                checkEnglish.setChecked(false);
                checkSpain.setChecked(false);
                checkIndo.setChecked(false);

                first = true;
                test();
            }
        });

        checkFranch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked){
                checkChina.setChecked(false);
                checkEnglish.setChecked(false);
                checkSpain.setChecked(false);
                checkIndo.setChecked(false);
                checkPortugal.setChecked(false);

                first = true;
                test();
            }
        });

        checkChina.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked){
                checkEnglish.setChecked(false);
                checkSpain.setChecked(false);
                checkIndo.setChecked(false);
                checkPortugal.setChecked(false);
                checkFranch.setChecked(false);

                first = true;
                test();
            }
        });

        imgNext.setOnClickListener(v -> {

            Intent i = new Intent(ActivityLanguage.this, MainActivity.class);
            startActivity(i);
        });
    }

    public void language(String language){
        Locale locale = new Locale(language);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());


        // save language that you selected in sharePreferences

        SharedPreferences.Editor editor = getSharedPreferences("myLanguage", MODE_PRIVATE).edit();
        editor.putString("my_language", language);
        editor.putBoolean("flagFirst", first);
        editor.apply();
    }

    public void reLoadLanguage(){
        // after reload language (language which saved in sharePreferences)
        SharedPreferences spr = getSharedPreferences("myLanguage", MODE_PRIVATE);
        String lg = spr.getString("my_language", "");
        // gọi lại hàm set language
        language(lg);
    }

    private void test(){
        SharedPreferences.Editor editor = getSharedPreferences("myLanguage", MODE_PRIVATE).edit();
        editor.putBoolean("flagFirst", first);
        editor.apply();
    }

}
