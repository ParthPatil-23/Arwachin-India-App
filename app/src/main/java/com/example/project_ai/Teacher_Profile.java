package com.example.project_ai;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class Teacher_Profile extends AppCompatActivity {

    TextView designation,name,id,subject,fullname;
    ImageView back6;

    // Database reference
    private DatabaseReference databaseReference;

    // If already Logged In don't have to log again
    public static String PREFS_NAME = "MyPrefsFile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_profile);

        designation = findViewById(R.id.designation);
        name = findViewById(R.id.T_Name);
        id = findViewById(R.id.id);
        subject = findViewById(R.id.subject);
        fullname = findViewById(R.id.fullname);
        back6 = findViewById(R.id.back6);

        // Initializing Database
        databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://arwachin-india-3bb19-default-rtdb.firebaseio.com/");

        databaseReference.child("Teachers").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                SharedPreferences sharedPreferences1 = getSharedPreferences(Student_Login.PREFS_NAME, 0);
                String Tech_ID = sharedPreferences1.getString("teacher_id", "");

                final String Teacher_Name = snapshot.child(Tech_ID).child("Name").getValue(String.class);
                final String t_id = snapshot.child(Tech_ID).child("I D").getValue(String.class);
                final String Subject = snapshot.child(Tech_ID).child("Subject").getValue(String.class);
                final String Designation = snapshot.child(Tech_ID).child("Desination").getValue(String.class);

                fullname.setText(Teacher_Name);
                name.setText(Teacher_Name);
                id.setText(t_id);
                subject.setText(Subject);
                designation.setText(Designation);



            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        back6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Teacher_Profile.this,Teacher_Nav.class);
                startActivity(intent);
            }
        });

    }
}