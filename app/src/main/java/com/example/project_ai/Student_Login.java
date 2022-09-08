package com.example.project_ai;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Student_Login extends AppCompatActivity {

    Button Go_back,Student_login;
    EditText Student_ID, Student_Password;
    ProgressDialog progressDialog;

    private FirebaseDatabase database;
    private DatabaseReference databaseReference;

    // Firebase mAuth and mUSer
    FirebaseAuth mAuth;
    FirebaseUser mUser;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_login);
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        
        // initializing Button
        Go_back = (Button)findViewById(R.id.Go_back);
        Student_login = (Button)findViewById(R.id.Student_login);

        // Initializing Input field Variables
        Student_ID = findViewById(R.id.Student_ID);
        Student_Password = findViewById(R.id.Student_Password);

        // Initializing Database
        databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://arwachin-india-3bb19-default-rtdb.firebaseio.com/");





        Go_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Student_Login.this, MainActivity.class));
            }
        });


        Student_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PerformLogin();

            }

            private void PerformLogin() {
                String Std_ID = Student_ID.getText().toString();
                String Std_pass = Student_Password.getText().toString();


                if(Std_ID.isEmpty()){
                    Student_ID.setError("Incorrect Student I.D");
                    Student_ID.requestFocus();

                }else if(Std_pass.isEmpty()){
                    Student_Password.setError("Incorrect Password");
                    Student_Password.requestFocus();

                }else if(Std_pass.length()<6){
                    Student_Password.setError("Incorrect Password");
                    Student_Password.requestFocus();

                }else{
                    databaseReference.child("Student").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            // Check if Student ID exists in firebase realtime datatbase
                            if(snapshot.hasChild(Std_ID)){

                                // Student ID exists
                                // checking if the user entered the correct password
                                final String getPassword = snapshot.child(Std_ID).child("Password").getValue(String.class);

                                if(getPassword.equals(Std_pass)){
                                    Toast.makeText(Student_Login.this, "Login Successfull", Toast.LENGTH_SHORT).show();

                                    // Opening Student Navigation
                                    startActivity(new Intent(Student_Login.this,Student_Nav.class));
                                }
                                else{
                                    Student_Password.setError("Incorrect Password");
                                    Student_Password.requestFocus();
                                }
                            }
                            else{
                                Student_ID.setError("Incorrect Student I.D");
                                Student_ID.requestFocus();
                                Toast.makeText(Student_Login.this, "Student I.D Doesn't Exist", Toast.LENGTH_SHORT).show();
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }




            }
        });

    }
}