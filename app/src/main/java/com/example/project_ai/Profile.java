package com.example.project_ai;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.wifi.hotspot2.pps.HomeSp;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Profile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Initialzing Bottom Nav Bar
        BottomNavigationView bottomNavigationView = findViewById(R.id.BottomNav);

        // setting home selected
        bottomNavigationView.setSelectedItemId(R.id.Profile);

        // Performing Item Selected listerner
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.Planner:
                        startActivity(new Intent(getApplicationContext()
                                ,Planner.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.Arwachin:
                        startActivity(new Intent(getApplicationContext()
                                ,ArwachinIndia.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.DashBoard:
                        startActivity(new Intent(getApplicationContext()
                                ,DashBoard.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.Home:
                        startActivity(new Intent(getApplicationContext()
                                , Student_Nav.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });
    }
}