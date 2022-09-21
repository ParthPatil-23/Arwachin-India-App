package com.example.project_ai;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class Logo extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 3000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logo);


        // Checking if the device is connected to the internet

        if (!isConnected(Logo.this)){
            Intent intent3 = new Intent(Logo.this,No_Internet.class);
            startActivity(intent3);
        }else{
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {



                    // Checking if the student have already login or not
                    SharedPreferences sharedPreferences1 = getSharedPreferences(Student_Login.PREFS_NAME,0);
                    boolean hasLoggedIn1 = sharedPreferences1.getBoolean("hasLoggedIn1",false);
                    String Std_ID = sharedPreferences1.getString("stu_id","");


                    // Checking if the Teacher have already login or not
                    SharedPreferences sharedPreferences2 = getSharedPreferences(Teacher_Login.PREFS_NAME,0);
                    boolean hasLoggedIn2 = sharedPreferences2.getBoolean("hasLoggedIn2",false);


                    if(hasLoggedIn1){
                        Intent intent = new Intent(Logo.this,Student_Nav.class);
                        startActivity(intent);
                        finish();

                    }else if(hasLoggedIn2){
                        Intent intent = new Intent(getApplicationContext(),Teacher_Nav.class);
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




    // Function to check if device is connected to internet
    private boolean isConnected(Logo logo) {

        ConnectivityManager connectivityManager = (ConnectivityManager) logo.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo wificonn = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobileconn = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if((wificonn != null && wificonn.isConnected()) || (mobileconn != null && mobileconn.isConnected())){
            return true;
        }else{
            return false;
        }
    }
}