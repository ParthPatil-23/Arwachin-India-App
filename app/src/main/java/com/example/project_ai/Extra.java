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

import org.w3c.dom.Text;

public class Extra extends AppCompatActivity {

    ImageView back2;
    TextView activity1, activity2, activity3, activity4, activity5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extra);

        // Setting back buttton
        back2 = findViewById(R.id.back2);

        // database refrence
        DatabaseReference databaseReference;

        // Activities
        activity1 = findViewById(R.id.activity1);
        activity2 = findViewById(R.id.activity2);
        activity3 = findViewById(R.id.activity3);
        activity4 = findViewById(R.id.activity4);
        activity5 = findViewById(R.id.activity5);

        // Setting back click
        back2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Extra.this, ArwachinIndia.class);
                startActivity(intent);
            }
        });

        SharedPreferences sharedPreferences1 = getSharedPreferences(Student_Login.PREFS_NAME, 0);
        String Std_ID = sharedPreferences1.getString("stud_id", "");

        // Initializing Database
        databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://arwachin-india-3bb19-default-rtdb.firebaseio.com/");


        databaseReference.child("Student").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                final String act1 = snapshot.child(Std_ID).child("Extra curricular 1").getValue(String.class);
                final String act2 = snapshot.child(Std_ID).child("Extra curricular 2").getValue(String.class);
                final String act3 = snapshot.child(Std_ID).child("Extra curricular 3").getValue(String.class);
                final String act4 = snapshot.child(Std_ID).child("Extra curricular 4").getValue(String.class);
                final String act5 = snapshot.child(Std_ID).child("Extra curricular 5").getValue(String.class);

                activity1.setText(act1);
                activity2.setText(act2);
                activity3.setText(act3);
                activity4.setText(act4);
                activity5.setText(act5);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}