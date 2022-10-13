package com.example.project_ai;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.style.BulletSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONObject;

import java.util.HashMap;

public class Homework extends AppCompatActivity {

    EditText maths_et, eng_et, hindi_et, sst_et, science_et, date_et;
    Button upload;
    ImageView back4;

    // Database reference
    private DatabaseReference databaseReference;
    FirebaseMessaging messaging;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homework);


        maths_et = findViewById(R.id.maths_et);
        eng_et = findViewById(R.id.eng_et);
        hindi_et = findViewById(R.id.hindi_et);
        sst_et = findViewById(R.id.sst_et);
        science_et = findViewById(R.id.hw_science_et);
        date_et = findViewById(R.id.date_et);
        upload = findViewById(R.id.upload);
        back4 = findViewById(R.id.back4);

        // Initializing Database
        databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://arwachin-india-3bb19-default-rtdb.firebaseio.com/");

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertData();
                Intent intent = new Intent(Homework.this, Teacher_Nav.class);
                startActivity(intent);

            }
        });
        back4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Homework.this, Teacher_Nav.class);
                startActivity(intent);
            }
        });


    }

    private void insertData() {

        String Maths = maths_et.getText().toString();
        String English = eng_et.getText().toString();
        String Hindi = hindi_et.getText().toString();
        String SST = sst_et.getText().toString();
        String Science = science_et.getText().toString();
        String Date = date_et.getText().toString();


        HashMap hw = new HashMap();
        hw.put("HW Maths", Maths);
        hw.put("HW English", English);
        hw.put("HW SST", SST);
        hw.put("HW Science", Science);
        hw.put("HW Hindi", Hindi);
        hw.put("Date", Date);
        databaseReference.child("class").updateChildren(hw);

        FirebaseMessaging.getInstance().subscribeToTopic("Testing");


        Toast.makeText(this, "Home Work Uploaded", Toast.LENGTH_SHORT).show();
    }

}