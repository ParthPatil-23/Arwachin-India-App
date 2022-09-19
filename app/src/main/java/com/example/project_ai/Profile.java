package com.example.project_ai;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.wifi.hotspot2.pps.HomeSp;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Profile extends AppCompatActivity {

    // Importing Text
    EditText full_name,dob,roll_no,father_name,mother_name,class1,class_tech,mess_fees,bus_fees,tot_sch_fees,tot_sch_fees_paid,tot_sch_fees_left,Student_ID;
    TextView fullname_field,username_field;

    // Importing private firebase variables
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;

    // Firebase mAuth and mUSer for Login Auth
    FirebaseAuth mAuth;
    FirebaseUser mUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Initialzing Variables for content
        fullname_field = findViewById(R.id.fullname_field);
        username_field = findViewById(R.id.username_field);
        full_name = findViewById(R.id.full_name);
        dob = findViewById(R.id.dob);
        roll_no = findViewById(R.id.roll_no);
        father_name = findViewById(R.id.father_name);
        mother_name = findViewById(R.id.mother_name);
        class1 = findViewById(R.id.class1);
        class_tech = findViewById(R.id.class_tech);
        mess_fees = findViewById(R.id.mess_fees);
        bus_fees = findViewById(R.id.bus_fees);
        tot_sch_fees = findViewById(R.id.tot_sch_fees);
        tot_sch_fees_paid = findViewById(R.id.tot_sch_fees_paid);
        tot_sch_fees_left = findViewById(R.id.tot_sch_fees_left);



        // Initialzing Bottom Nav Bar
        BottomNavigationView bottomNavigationView = findViewById(R.id.BottomNav);

        // setting home selected
        bottomNavigationView.setSelectedItemId(R.id.Profile);

        // Initializing Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        // Initializing Database
        databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://arwachin-india-3bb19-default-rtdb.firebaseio.com/");









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
        bottomNavigationView.setItemIconTintList(null);
    }
}