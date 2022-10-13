package com.example.project_ai;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ArwachinIndia extends AppCompatActivity {

    // Variables for class progress
    TextView p_maths, p_english, p_sst, p_hindi, p_science;
    ImageView desk, event, extracurricular;
    LottieAnimationView arrow;

    // Database reference
    private DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arwachin_india);

        // Animation
        arrow = findViewById(R.id.arrow);

        // Initialising variables for class progress
        p_maths = findViewById(R.id.p_maths);
        p_english = findViewById(R.id.p_english);
        p_sst = findViewById(R.id.p_sst);
        p_hindi = findViewById(R.id.p_hindi);
        p_science = findViewById(R.id.p_science);

        // ImageView
        desk = findViewById(R.id.principal);
        event = findViewById(R.id.event);
        extracurricular = findViewById(R.id.extracurricular);

        // On clicking event Image
        event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ArwachinIndia.this, Upcoming_event.class);
                startActivity(intent);
            }
        });

        // on click Desk
        desk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:pricipal@arwachininida.com")));
            }

            private void desk2() {
            }
        });


        // On clicking extra__ img
        extracurricular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(ArwachinIndia.this, Extra.class);
                startActivity(intent);



            }
        });


        // Checking if the device is connected to internet
        if (!isConnected(ArwachinIndia.this)) {
            Intent intent3 = new Intent(ArwachinIndia.this, No_Internet.class);
            startActivity(intent3);
        } else {
            // Initialzing Bottom Nav Bar
            BottomNavigationView bottomNavigationView = findViewById(R.id.BottomNav);

            // setting home selected
            bottomNavigationView.setSelectedItemId(R.id.Arwachin);

            // Loading Class progress
            loadprogress();

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

                        case R.id.DashBoard:
                            startActivity(new Intent(getApplicationContext()
                                    , DashBoard.class));
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
    private boolean isConnected(ArwachinIndia arwachinindia) {

        ConnectivityManager connectivityManager = (ConnectivityManager) arwachinindia.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo wificonn = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobileconn = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if ((wificonn != null && wificonn.isConnected()) || (mobileconn != null && mobileconn.isConnected())) {
            return true;
        } else {
            return false;
        }
    }

    private void loadprogress() {

        SharedPreferences sharedPreferences1 = getSharedPreferences(Student_Nav.PREFS_NAME, Context.MODE_PRIVATE);
        String Std_ID = sharedPreferences1.getString("stud_id", "");


        String P_Maths = sharedPreferences1.getString("P_Maths", null);
        String P_Sst = sharedPreferences1.getString("P_Sst", null);
        String P_Science = sharedPreferences1.getString("P_Science", null);
        String P_Hindi = sharedPreferences1.getString("P_Hindi", null);
        String P_Eng = sharedPreferences1.getString("P_Eng", null);


        p_maths.setText(P_Maths);
        p_english.setText(P_Science);
        p_hindi.setText(P_Hindi);
        p_science.setText(P_Science);
        p_sst.setText(P_Sst);

    }

}