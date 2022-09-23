package com.example.project_ai;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    // Importing Buttons for Student and Teacher Login
    Button student_btn, teacher_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialising Buttons
        student_btn = (Button) findViewById(R.id.student_btn);
        teacher_btn = (Button) findViewById(R.id.teacher_btn);


        // Adding Functions to the Student Button
        student_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, Student_Login.class));
            }
        });
        // Adding Function to the Teacher Button
        teacher_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, Teacher_Login.class));
            }
        });

    }
}