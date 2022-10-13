package com.example.project_ai;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class ClassProgress extends AppCompatActivity {

    EditText maths_et, eng_et, hindi_et, sst_et, science_et, date_et;
    Button upload;
    ImageView back5;

    // Database reference
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_progress);

        maths_et = findViewById(R.id.maths_et);
        eng_et = findViewById(R.id.eng_et);
        hindi_et = findViewById(R.id.hindi_et);
        sst_et = findViewById(R.id.sst_et);
        science_et = findViewById(R.id.hw_science_et);
        upload = findViewById(R.id.upload);
        back5 = findViewById(R.id.back5);


        // Initializing Database
        databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://arwachin-india-3bb19-default-rtdb.firebaseio.com/");

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertData();

                Intent intent = new Intent(ClassProgress.this, Teacher_Nav.class);
                startActivity(intent);

            }
        });

        back5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ClassProgress.this, Teacher_Nav.class);
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


        HashMap hw = new HashMap();
        hw.put("Maths", Maths);
        hw.put("English", English);
        hw.put("SST", SST);
        hw.put("Science", Science);
        hw.put("Hindi", Hindi);
        databaseReference.child("class").updateChildren(hw);


        Toast.makeText(this, "Home Work Uploaded", Toast.LENGTH_SHORT).show();
    }
}