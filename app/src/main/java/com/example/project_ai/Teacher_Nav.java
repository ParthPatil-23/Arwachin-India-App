package com.example.project_ai;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Teacher_Nav extends AppCompatActivity {

    ImageView progress, profile, homework;
    TextView teachername;

    // If already Logged In don't have to log again
    public static String PREFS_NAME = "MyPrefsFile";

    // Database reference
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_nav);

        progress = findViewById(R.id.progress);
        profile = findViewById(R.id.profile);
        homework = findViewById(R.id.homework);
        teachername = findViewById(R.id.teachername);

        loaddata();

        homework.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Teacher_Nav.this, Homework.class);
                startActivity(intent);

            }
        });

        progress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Teacher_Nav.this, ClassProgress.class);
                startActivity(intent);
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Teacher_Nav.this, Teacher_Profile.class);
                startActivity(intent);
            }
        });


    }

    private void loaddata() {
        // Initializing Database
        databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://arwachin-india-3bb19-default-rtdb.firebaseio.com/");


        databaseReference.child("Teachers").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // Checking if the student have already login or not
                SharedPreferences sharedPreferences1 = getSharedPreferences(Student_Login.PREFS_NAME, 0);
                String Tech_ID = sharedPreferences1.getString("teacher_id", "");

                final String Teacher_Name = snapshot.child(Tech_ID).child("Name").getValue(String.class);

                teachername.setText(Teacher_Name);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


}