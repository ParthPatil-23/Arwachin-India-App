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



public class Teacher_Login extends AppCompatActivity {

    Button Go_back,Teacher_login;
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
        Go_back = (Button)findViewById(R.id.Go_back);
        Teacher_login = (Button)findViewById(R.id.Teacher_login);

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


        Teacher_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PerformLogin();

            }

            private void PerformLogin() {
                String Teach_ID = Teacher_ID.getText().toString();
                String Teach_pass = Teacher_Password.getText().toString();


                if(Teach_ID.isEmpty()){
                    Teacher_ID.setError("Incorrect Teacher I.D");
                    Teacher_ID.requestFocus();

                }else if(Teach_pass.isEmpty()){
                    Teacher_Password.setError("Incorrect Password");
                    Teacher_Password.requestFocus();

                }else if(Teach_pass.length()<6){
                    Teacher_Password.setError("Incorrect Password");
                    Teacher_Password.requestFocus();

                }else{
                    databaseReference.child("Teacher").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            // Check if Teacher ID exists in firebase realtime database
                            if(snapshot.hasChild(Teach_ID)){

                                // Teacher ID exists
                                // checking if the user entered the correct password
                                final String getPassword = snapshot.child(Teach_ID).child("Password").getValue(String.class);

                                if(getPassword.equals(Teach_pass)){
                                    Toast.makeText(Teacher_Login.this, "Login Successfull", Toast.LENGTH_SHORT).show();

                                    // Opening Teacher Navigation
                                    startActivity(new Intent(Teacher_Login.this,Teacher_Nav.class));
                                }
                                else{
                                    Teacher_Password.setError("Incorrect Password");
                                    Teacher_Password.requestFocus();
                                }
                            }
                            else{
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