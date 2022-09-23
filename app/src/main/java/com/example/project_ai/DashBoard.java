package com.example.project_ai;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class DashBoard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);


        // Checking if the device is connected to internet
        if (!isConnected(DashBoard.this)) {
            Intent intent3 = new Intent(DashBoard.this, No_Internet.class);
            startActivity(intent3);
        } else {
            // Initialzing Bottom Nav Bar
            BottomNavigationView bottomNavigationView = findViewById(R.id.BottomNav);

            // setting home selected
            bottomNavigationView.setSelectedItemId(R.id.DashBoard);

            // Performing Item Selected listerner
            bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    switch (menuItem.getItemId()) {
                        case R.id.Profile:
                            startActivity(new Intent(getApplicationContext()
                                    , Profile.class));
                            overridePendingTransition(0, 0);
                            return true;

                        case R.id.Arwachin:
                            startActivity(new Intent(getApplicationContext()
                                    , ArwachinIndia.class));
                            overridePendingTransition(0, 0);
                            return true;

                        case R.id.Planner:
                            startActivity(new Intent(getApplicationContext()
                                    , Planner.class));
                            overridePendingTransition(0, 0);
                            return true;

                        case R.id.Home:
                            startActivity(new Intent(getApplicationContext()
                                    , Student_Nav.class));
                            overridePendingTransition(0, 0);
                            return true;
                    }
                    return false;
                }
            });

            bottomNavigationView.setItemIconTintList(null);
        }
    }


    // Setting so that the on double back press app quits.
    int counter = 0;

    @Override
    public void onBackPressed() {

        counter++;
        if (counter == 2) {
            finishAffinity();
        }
    }

    // Function to check if device is connected to internet
    private boolean isConnected(DashBoard dashboard) {

        ConnectivityManager connectivityManager = (ConnectivityManager) dashboard.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo wificonn = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobileconn = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if ((wificonn != null && wificonn.isConnected()) || (mobileconn != null && mobileconn.isConnected())) {
            return true;
        } else {
            return false;
        }
    }
}