package com.example.project_ai;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class Upcoming_event extends AppCompatActivity {

    ImageView back3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upcoming_event);

        // Setting back buttton
        back3 = findViewById(R.id.back3);

        // Setting back click
        back3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Upcoming_event.this,ArwachinIndia.class);
                startActivity(intent);
            }
        });
    }
}