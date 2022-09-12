package com.example.project_ai;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

public class Logo extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 3000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logo);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                // Student
                SharedPreferences sharedPreferences1 = getSharedPreferences(Student_Login.PREFS_NAME,0);
                boolean hasLoggedIn1 = sharedPreferences1.getBoolean("hasLoggedIn",false);

                // Teacher
                SharedPreferences sharedPreferences2 = getSharedPreferences(Teacher_Login.PREFS_NAME,0);
                boolean hasLoggedIn2 = sharedPreferences2.getBoolean("hasLoggedIn",false);

                if(hasLoggedIn1){
                    Intent intent = new Intent(Logo.this,Student_Nav.class);
                    startActivity(intent);
                    finish();
                }if(hasLoggedIn2){
                    Intent intent = new Intent(Logo.this,Teacher_Nav.class);
                    startActivity(intent);
                    finish();
                }else{
                    Intent intent = new Intent(Logo.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }

            }
        },SPLASH_TIME_OUT);
    }
}