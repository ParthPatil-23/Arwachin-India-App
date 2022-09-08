package com.example.project_ai;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Planner extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planner);

        // Initialzing Bottom Nav Bar
        BottomNavigationView bottomNavigationView = findViewById(R.id.BottomNav);

        // setting home selected
        bottomNavigationView.setSelectedItemId(R.id.Planner);

        // Performing Item Selected listerner
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.Profile:
                        startActivity(new Intent(getApplicationContext()
                                ,Profile.class));
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