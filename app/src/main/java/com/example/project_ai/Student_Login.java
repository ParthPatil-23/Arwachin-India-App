package com.example.project_ai;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class Student_Login extends AppCompatActivity {

    // If already Logged In don't have to log again
    public static String PREFS_NAME = "MyPrefsFile";

    // Importing Button, EditText, Progress Dialog
    Button Go_back, Student_login;
    EditText Student_ID, Student_Password;
    ProgressDialog progressDialog;

    // Importing private firebase variables
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;

    // Firebase mAuth and mUSer for Login Auth
    FirebaseAuth mAuth;
    FirebaseUser mUser;

    // Lottie animation
    LottieAnimationView success, confetti1, confetti2, confetti3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_login);

        // Initializing Animation
        success = findViewById(R.id.success);
        confetti1 = findViewById(R.id.confetti1);
        confetti2 = findViewById(R.id.confetti2);
        confetti3 = findViewById(R.id.confetti3);

        // Initializing Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        // Initializing Button
        Go_back = (Button) findViewById(R.id.Go_back);
        Student_login = (Button) findViewById(R.id.Student_login);

        // Initializing Input field Variables
        Student_ID = findViewById(R.id.Student_ID);
        Student_Password = findViewById(R.id.Student_Password);
        Intent intent = (new Intent(Student_Login.this, Student_Nav.class));
        intent.putExtra("s", String.valueOf(Student_ID));

        // Initializing Database
        databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://arwachin-india-3bb19-default-rtdb.firebaseio.com/");


        Go_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Student_Login.this, MainActivity.class));
            }
        });


        // Adding function to the Student Login Button
        Student_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PerformLogin();

            }

            // Making a function to Perform Login
            private void PerformLogin() {
                String Std_ID = Student_ID.getText().toString();
                String Std_pass = Student_Password.getText().toString();


                // Checking if the Edit Text is empty or incorrect
                if (Std_ID.isEmpty()) {
                    Student_ID.setError("Incorrect Student I.D");
                    Student_ID.requestFocus();

                } else if (Std_pass.isEmpty()) {
                    Student_Password.setError("Incorrect Password");
                    Student_Password.requestFocus();

                } else if (Std_pass.length() < 6) {
                    Student_Password.setError("Incorrect Password");
                    Student_Password.requestFocus();

                } else {

                    databaseReference.child("Student").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            // Check if Student ID exists in firebase realtime datatbase
                            if (snapshot.hasChild(Std_ID)) {

                                // Student ID exists
                                // checking if the user entered the correct password
                                final String getPassword = snapshot.child(Std_ID).child("Password").getValue(String.class);

                                if (getPassword.equals(Std_pass)) {
                                    success.playAnimation();
                                    confetti1.playAnimation();
                                    confetti2.playAnimation();
                                    confetti3.playAnimation();


                                    // Setting the delay to play animation
                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {


                                            // Trying to not show login again after once Loged In
                                            SharedPreferences sharedPreferences1 = getSharedPreferences(Student_Login.PREFS_NAME, Context.MODE_PRIVATE);
                                            SharedPreferences.Editor editor = sharedPreferences1.edit();

                                            editor.putString("stud_id", Student_ID.getText().toString());
                                            editor.commit();
//                                            Toast.makeText(Student_Login.this, "ID Password Saved!!!", Toast.LENGTH_SHORT).show();

                                            editor.putBoolean("hasLoggedIn1", true);
                                            editor.commit();
                                            editor.apply();

                                            // Intent to profile activity
                                            Intent intent1 = (new Intent(Student_Login.this, loader.class));
                                            startActivity(intent1);
                                            finish();
                                            Toast.makeText(Student_Login.this, "Login Successfull", Toast.LENGTH_SHORT).show();


                                            new Handler().postDelayed(new Runnable() {
                                                @Override
                                                public void run() {
                                                    success.setVisibility(View.GONE);
                                                    confetti1.setVisibility(View.GONE);
                                                    confetti2.setVisibility(View.GONE);
                                                    confetti3.setVisibility(View.GONE);
                                                }
                                            }, 3500);


                                        }
                                    }, 5000);


                                } else {
                                    Student_Password.setError("Incorrect Password");
                                    Student_Password.requestFocus();
                                }
                            } else {
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