package com.example.project_ai;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class Logo extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 3000;

    // Database reference
    private DatabaseReference databaseReference;

    // Student Nav Pulling data
    TextView tv1, tv2, tv3, tv4, tv5, maths_m, eng_m, sci_m, hin_m, sst_m;

    // ArwachinIndiaActivity Pulling data
    String p_maths, p_english, p_sst, p_hindi, p_science;

    // DashBoard Pulling Data
    String Science1, English1, Maths1, SST1, Hindi1, date;

    // Profile pulling Data
    String full_name, dob, roll_no, father_name, mother_name, class1, class_tech, mess_fees, bus_fees, tot_sch_fees, tot_sch_fees_paid, tot_sch_fees_left, Student_ID;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logo);


        // Checking if the device is connected to the internet

        if (!isConnected(Logo.this)) {
            Intent intent3 = new Intent(Logo.this, No_Internet.class);
            startActivity(intent3);
        } else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {


                    // Checking if the student have already login or not
                    SharedPreferences sharedPreferences1 = getSharedPreferences(Student_Login.PREFS_NAME, 0);
                    boolean hasLoggedIn1 = sharedPreferences1.getBoolean("hasLoggedIn1", false);
                    String Std_ID = sharedPreferences1.getString("stu_id", "");


                    // Checking if the Teacher have already login or not
                    SharedPreferences sharedPreferences2 = getSharedPreferences(Teacher_Login.PREFS_NAME, 0);
                    boolean hasLoggedIn2 = sharedPreferences2.getBoolean("hasLoggedIn2", false);


                    if (hasLoggedIn1) {
                        Intent intent = new Intent(Logo.this, loader.class);
                        startActivity(intent);
                        finish();

                    } else if (hasLoggedIn2) {
                        Intent intent = new Intent(getApplicationContext(), Teacher_Nav.class);
                        startActivity(intent);
                        finish();

                    } else {
                        Intent intent = new Intent(Logo.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }

                }
            }, SPLASH_TIME_OUT);

        }
        // Variables to set attendence in the pie graphs
        SharedPreferences sharedPreferences2 = getSharedPreferences(Student_Nav.PREFS_NAME, Context.MODE_PRIVATE);
        String Std_ID2 = sharedPreferences2.getString("stud_id", "");

        if(Std_ID2 == null){
            Intent intent = new Intent(Logo.this, loader.class);
            startActivity(intent);


        }else{

            // trying to reteving all the data so that it won't need to pull everytime new activity is opened
            // Initializing Database
            databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://arwachin-india-3bb19-default-rtdb.firebaseio.com/");


            // Variables to set attendence in the pie graphs
            SharedPreferences sharedPreferences1 = getSharedPreferences(Student_Nav.PREFS_NAME, Context.MODE_PRIVATE);
            String Std_ID = sharedPreferences1.getString("stud_id", "");

            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    // Arwachin India
                    final String P_Maths = snapshot.child("class").child("Maths").getValue(String.class);
                    final String P_Sst = snapshot.child("class").child("SST").getValue(String.class);
                    final String P_Science = snapshot.child("class").child("Science").getValue(String.class);
                    final String P_Hindi = snapshot.child("class").child("Hindi").getValue(String.class);
                    final String P_Eng = snapshot.child("class").child("English").getValue(String.class);
                    // Home
                    final String t_days = snapshot.child("Student").child(Std_ID).child("Total days").getValue(String.class);
                    final String left_days = snapshot.child("Student").child(Std_ID).child("Days Left").getValue(String.class);
                    final String p_days = snapshot.child("Student").child(Std_ID).child("Present").getValue(String.class);
                    final String a_days = snapshot.child("Student").child(Std_ID).child("Absents").getValue(String.class);
                    final String m_maths = snapshot.child("Student").child(Std_ID).child("Maths").getValue(String.class);
                    final String m_english = snapshot.child("Student").child(Std_ID).child("English").getValue(String.class);
                    final String m_hindi = snapshot.child("Student").child(Std_ID).child("Hindi").getValue(String.class);
                    final String m_sst = snapshot.child("Student").child(Std_ID).child("Social Studies").getValue(String.class);
                    final String m_science = snapshot.child("Student").child(Std_ID).child("Science").getValue(String.class);
                    // Profile
                    final String Name = snapshot.child("Student").child(Std_ID).child("Name").getValue(String.class);
                    final String DOB = snapshot.child("Student").child(Std_ID).child("DOB").getValue(String.class);
                    final String ID_NUM = snapshot.child("Student").child(Std_ID).child("I D Number").getValue(String.class);
                    final String F_Name = snapshot.child("Student").child(Std_ID).child("Father's Name").getValue(String.class);
                    final String M_Name = snapshot.child("Student").child(Std_ID).child("Mother's Name").getValue(String.class);
                    final String Class1 = snapshot.child("Student").child(Std_ID).child("Class").getValue(String.class);
                    final String Class_Tech = snapshot.child("Student").child(Std_ID).child("Class Teacher").getValue(String.class);
                    final String Mess_Fess = snapshot.child("Student").child(Std_ID).child("Mess fees").getValue(String.class);
                    final String Bus_Fees = snapshot.child("Student").child(Std_ID).child("Bus fees").getValue(String.class);
                    final String School_Fees = snapshot.child("Student").child(Std_ID).child("Total Fees").getValue(String.class);
                    final String School_Fees_Left = snapshot.child("Student").child(Std_ID).child("Remaining").getValue(String.class);
                    final String School_Fees_Paid = snapshot.child("Student").child(Std_ID).child("Paid").getValue(String.class);
                    // Homework
                    final String hw_maths = snapshot.child("class").child("HW Maths").getValue(String.class);
                    final String hw_english = snapshot.child("class").child("HW English").getValue(String.class);
                    final String hw_science = snapshot.child("class").child("HW Science").getValue(String.class);
                    final String hw_sst = snapshot.child("class").child("HW SST").getValue(String.class);
                    final String hw_hindi = snapshot.child("class").child("HW Hindi").getValue(String.class);
                    final String Date = snapshot.child("class").child("Date").getValue(String.class);

                    // Saving Data
                    SharedPreferences sharedPreferences1 = getSharedPreferences(Student_Login.PREFS_NAME, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences1.edit();

                    // Student ID
                    editor.putString("stud_id", Std_ID);
                    // Arwachin India
                    editor.putString("P_Maths", P_Maths);
                    editor.putString("P_Sst", P_Sst);
                    editor.putString("P_Science", P_Science);
                    editor.putString("P_Hindi", P_Hindi);
                    editor.putString("P_Eng", P_Eng);
                    // Home
                    editor.putString("t_days", t_days);
                    editor.putString("left_days", left_days);
                    editor.putString("p_days", p_days);
                    editor.putString("a_days", a_days);
                    editor.putString("m_maths", m_maths);
                    editor.putString("m_english", m_english);
                    editor.putString("m_hindi", m_hindi);
                    editor.putString("m_sst", m_sst);
                    editor.putString("m_science", m_science);
                    // Profile
                    editor.putString("Name", Name);
                    editor.putString("DOB", DOB);
                    editor.putString("ID_NUM", ID_NUM);
                    editor.putString("F_Name", F_Name);
                    editor.putString("M_Name", M_Name);
                    editor.putString("Class1", Class1);
                    editor.putString("Class_Tech", Class_Tech);
                    editor.putString("Mess_Fess", Mess_Fess);
                    editor.putString("Bus_Fees", Bus_Fees);
                    editor.putString("School_Fees", School_Fees);
                    editor.putString("School_Fees_Left", School_Fees_Left);
                    editor.putString("School_Fees_Paid", School_Fees_Paid);
                    // Homework
                    editor.putString("hw_maths", hw_maths);
                    editor.putString("hw_english", hw_english);
                    editor.putString("hw_science", hw_science);
                    editor.putString("hw_sst", hw_sst);
                    editor.putString("hw_hindi", hw_hindi);
                    editor.putString("Date", Date);
                    editor.commit();

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }
        }







    // Function to check if device is connected to internet
    private boolean isConnected(Logo logo) {

        ConnectivityManager connectivityManager = (ConnectivityManager) logo.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo wificonn = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobileconn = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if ((wificonn != null && wificonn.isConnected()) || (mobileconn != null && mobileconn.isConnected())) {
            return true;
        } else {
            return false;
        }
    }
}