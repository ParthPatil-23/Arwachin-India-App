package com.example.project_ai;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class To_Do extends AppCompatActivity {

    EditText et;
    TextView tv;
    Button bt,back;
    public static String PREFS_NAME = "MyPrefsFile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do);

        // Variables to add To-Do elements
        et = findViewById(R.id.et);
        bt = findViewById(R.id.add2);
        back = findViewById(R.id.back);


        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String text = et.getText().toString();


                if(text.isEmpty()){
                    Toast.makeText(To_Do.this, "Please Enter a Task To add", Toast.LENGTH_SHORT).show();

                }else{

                    Intent intent = new Intent(To_Do.this, Student_Nav.class);
                    intent.putExtra("add",et.getText().toString());
                    startActivity(intent);

                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(To_Do.this, Student_Nav.class);
                startActivity(intent);
            }
        });
    }
}