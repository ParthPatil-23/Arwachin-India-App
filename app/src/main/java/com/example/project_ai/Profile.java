package com.example.project_ai;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Profile extends AppCompatActivity {

    // Importing Text
    EditText full_name, dob, roll_no, father_name, mother_name, class1, class_tech, mess_fees, bus_fees, tot_sch_fees, tot_sch_fees_paid, tot_sch_fees_left, Student_ID;
    TextView fullname_field, username_field;

    // Importing private firebase variables
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;

    // Firebase mAuth and mUSer for Login Auth
    FirebaseAuth mAuth;
    FirebaseUser mUser;

    //Saving Variable Std_ID
    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String STUD_ID = "Stud_ID";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        // Initialzing Variables for content
        fullname_field = (TextView) findViewById(R.id.fullname);
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

        // Initializing Database
        databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://arwachin-india-3bb19-default-rtdb.firebaseio.com/");


        // Checking if the device is connected to internet
        if (!isConnected(Profile.this)) {
            Intent intent3 = new Intent(Profile.this, No_Internet.class);
            startActivity(intent3);
        } else {
            // Checking if the student have already login or not
            SharedPreferences sharedPreferences1 = getSharedPreferences(Student_Login.PREFS_NAME, 0);
            String Std_ID = sharedPreferences1.getString("stud_id", "");

            String Name = sharedPreferences1.getString("Name", null);
            String DOB = sharedPreferences1.getString("DOB", null);
            String ID_NUM = sharedPreferences1.getString("ID_NUM", null);
            String F_Name = sharedPreferences1.getString("F_Name", null);
            String M_Name = sharedPreferences1.getString("M_Name", null);
            String Class1 = sharedPreferences1.getString("Class1", null);
            String Class_Tech = sharedPreferences1.getString("Class_Tech", null);
            String Mess_Fess = sharedPreferences1.getString("Mess_Fess", null);
            String Bus_Fees = sharedPreferences1.getString("Bus_Fees", null);
            String School_Fees = sharedPreferences1.getString("School_Fees", null);
            String School_Fees_Left = sharedPreferences1.getString("School_Fees_Left", null);
            String School_Fees_Paid = sharedPreferences1.getString("School_Fees_Paid", null);

            // Putting Data
            fullname_field.setText(Name + " kolder");
            username_field.setText(Std_ID);
            full_name.setText(Name);
            dob.setText(DOB);
            roll_no.setText(ID_NUM);
            father_name.setText(F_Name);
            mother_name.setText(M_Name);
            class1.setText(Class1);
            class_tech.setText(Class_Tech);
            mess_fees.setText(Mess_Fess);
            bus_fees.setText(Bus_Fees);
            tot_sch_fees.setText(School_Fees);
            tot_sch_fees_left.setText(School_Fees_Left);
            tot_sch_fees_paid.setText(School_Fees_Paid);




            // Performing Item Selected listerner
            bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {

                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    switch (menuItem.getItemId()) {
                        case R.id.Planner:
                            startActivity(new Intent(getApplicationContext()
                                    , Planner.class));
                            overridePendingTransition(0, 0);
                            return true;

                        case R.id.Arwachin:
                            startActivity(new Intent(getApplicationContext()
                                    , ArwachinIndia.class));
                            overridePendingTransition(0, 0);
                            return true;

                        case R.id.DashBoard:
                            startActivity(new Intent(getApplicationContext()
                                    , DashBoard.class));
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
    private boolean isConnected(Profile profile) {

        ConnectivityManager connectivityManager = (ConnectivityManager) profile.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo wificonn = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobileconn = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if ((wificonn != null && wificonn.isConnected()) || (mobileconn != null && mobileconn.isConnected())) {
            return true;
        } else {
            return false;
        }
    }


}