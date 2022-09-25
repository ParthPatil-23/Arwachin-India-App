package com.example.project_ai;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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


public class Teacher_Login extends AppCompatActivity {

    // If already Logged In don't have to log again
    public static String PREFS_NAME = "MyPrefsFile";

    // Importing Button, EditText, Progress Dialog
    Button Go_back, Teacher_login;
    EditText Teacher_ID, Teacher_Password;
    ProgressDialog progressDialog;

    private FirebaseDatabase database;
    private DatabaseReference databaseReference;

    // Firebase mAuth and mUSer
    FirebaseAuth mAuth;
    FirebaseUser mUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_login);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        // initializing Button
        Go_back = (Button) findViewById(R.id.Go_back);
        Teacher_login = (Button) findViewById(R.id.Teacher_login);

        // Initializing Input field Variables
        Teacher_ID = findViewById(R.id.Teacher_ID);
        Teacher_Password = findViewById(R.id.Teacher_Password);

        // Initializing Database
        databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://arwachin-india-3bb19-default-rtdb.firebaseio.com/");


        Go_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Teacher_Login.this, MainActivity.class));

            }
        });


        // Adding function to the Student Login Button
        Teacher_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PerformLogin();

            }

            // Making a function to Perform Login
            private void PerformLogin() {
                String Teach_ID = Teacher_ID.getText().toString();
                String Teach_pass = Teacher_Password.getText().toString();


                // Checking if the Edit Text is empty or incorrect
                if (Teach_ID.isEmpty()) {
                    Teacher_ID.setError("Incorrect Teacher I.D");
                    Teacher_ID.requestFocus();

                } else if (Teach_pass.isEmpty()) {
                    Teacher_Password.setError("Incorrect Password");
                    Teacher_Password.requestFocus();

                } else if (Teach_pass.length() < 6) {
                    Teacher_Password.setError("Incorrect Password");
                    Teacher_Password.requestFocus();

                } else {
                    databaseReference.child("Teachers").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            // Check if Teacher ID exists in firebase realtime database
                            if (snapshot.hasChild(Teach_ID)) {

                                // Teacher ID exists
                                // checking if the user entered the correct password
                                final String getPassword = snapshot.child(Teach_ID).child("Password").getValue(String.class);

                                if (getPassword.equals(Teach_pass)) {


                                    // Trying to not show login again after once Loged In
                                    SharedPreferences sharedPreferences1 = getSharedPreferences(Student_Login.PREFS_NAME, Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor1 = sharedPreferences1.edit();

                                    editor1.putString("teacher_id", Teacher_ID.getText().toString());
                                    editor1.commit();








                                    Toast.makeText(Teacher_Login.this, "Login Successfull", Toast.LENGTH_SHORT).show();

                                    // Opening Teacher Navigation
                                    startActivity(new Intent(Teacher_Login.this, Teacher_Nav.class));

                                    // Trying to not show login again after once Loged In
                                    SharedPreferences sharedPreferences2 = getSharedPreferences(Teacher_Login.PREFS_NAME, 0);
                                    SharedPreferences.Editor editor = sharedPreferences2.edit();

                                    editor.putBoolean("hasLoggedIn2", true);
                                    editor.commit();

                                    startActivity(new Intent(Teacher_Login.this, Teacher_Nav.class));
                                    finish();
                                } else {
                                    Teacher_Password.setError("Incorrect Password");
                                    Teacher_Password.requestFocus();
                                }
                            } else {
                                Teacher_ID.setError("Incorrect Teacher I.D");
                                Teacher_ID.requestFocus();
                                Toast.makeText(Teacher_Login.this, "Teacher I.D Doesn't Exist", Toast.LENGTH_SHORT).show();
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