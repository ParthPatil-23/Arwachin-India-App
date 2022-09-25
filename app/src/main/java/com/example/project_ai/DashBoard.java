package com.example.project_ai;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DashBoard extends AppCompatActivity {

    TextView Science1,English1,Maths1,SST1,Hindi1,date;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);

        // Setting Variables
        Science1 = findViewById(R.id.Science1);
        English1 = findViewById(R.id.English1);
        Maths1 = findViewById(R.id.Maths1);
        SST1 = findViewById(R.id.SST1);
        Hindi1 = findViewById(R.id.Hindi1);
        date = findViewById(R.id.date);



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

        loadData();
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

    private void loadData(){
        // Initializing Database
        databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://arwachin-india-3bb19-default-rtdb.firebaseio.com/");


        databaseReference.child("class").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                final String hw_maths = snapshot.child("HW Maths").getValue(String.class);
                final String hw_english = snapshot.child("HW English").getValue(String.class);
                final String hw_science = snapshot.child("HW Science").getValue(String.class);
                final String hw_sst = snapshot.child("HW SST").getValue(String.class);
                final String hw_hindi = snapshot.child("HW Hindi").getValue(String.class);
                final String Date = snapshot.child("Date").getValue(String.class);


                Science1.setText(hw_science);
                English1.setText(hw_english);
                Maths1.setText(hw_maths);
                SST1.setText(hw_sst);
                Hindi1.setText(hw_hindi);
                date.setText(Date);



            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}