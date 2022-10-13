package com.example.project_ai;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class Student_Nav extends AppCompatActivity {

    public static String PREFS_NAME = "MyPrefsFile";

    Button add, delete;
    TextView tv1, tv2, tv3, tv4, tv5, maths_m, eng_m, sci_m, hin_m, sst_m;
    PieChart pieChart1;
    LottieAnimationView conf1, conf2, conf3, task;


    // Database reference
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_nav);

        // Animation
        task = findViewById(R.id.task);
        conf1 = findViewById(R.id.conf1);
        conf2 = findViewById(R.id.conf2);
        conf3 = findViewById(R.id.conf3);


        // Marks Variables
        maths_m = findViewById(R.id.maths_m);
        eng_m = findViewById(R.id.english_m);
        sci_m = findViewById(R.id.science_m);
        hin_m = findViewById(R.id.hindi_m);
        sst_m = findViewById(R.id.sst_m);

        // Initialzing Bottom Nav Bar
        BottomNavigationView bottomNavigationView = findViewById(R.id.BottomNav);

        // setting home selected
        bottomNavigationView.setSelectedItemId(R.id.Home);

        // To-Do List Variables
        add = findViewById(R.id.add);
        delete = findViewById(R.id.delete);
        tv1 = findViewById(R.id.tv1);
        tv2 = findViewById(R.id.tv2);
        tv3 = findViewById(R.id.tv3);
        tv4 = findViewById(R.id.tv4);
        tv5 = findViewById(R.id.tv5);
        pieChart1 = findViewById(R.id.piechart1);


        // Calling function to check if we had to do elements before
        adding1();
        // Calling charts
        setupPieChart();
        loadpiechart();


        // Checking if the device is connected to internet
        if (!isConnected(Student_Nav.this)) {
            Intent intent3 = new Intent(Student_Nav.this, No_Internet.class);
            startActivity(intent3);
        } else {


            add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(), To_Do.class);

                    startActivity(intent);

                }
            });
            // Calling function to check if To-Do list had values and to add elements to the list.
            adding2();


            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    SharedPreferences sharedPreferences2 = getSharedPreferences(Student_Nav.PREFS_NAME, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor2 = sharedPreferences2.edit();


                    tv1.setText(null);
                    editor2.putString("add1", null);
                    editor2.apply();
                    tv2.setText(null);
                    editor2.putString("add2", null);
                    editor2.apply();
                    tv3.setText(null);
                    editor2.putString("add3", null);
                    editor2.apply();
                    tv4.setText(null);
                    editor2.putString("add4", null);
                    editor2.apply();
                    tv5.setText(null);
                    editor2.putString("add5", null);
                    editor2.apply();

                    task.playAnimation();
                    conf1.playAnimation();
                    conf2.playAnimation();
                    conf3.playAnimation();

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            task.setVisibility(View.GONE);
                            conf1.setVisibility(View.GONE);
                            conf2.setVisibility(View.GONE);
                            conf3.setVisibility(View.GONE);
                        }
                    }, 2700);

                }
            });


            // Performing Item Selected listerner
            bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    switch (menuItem.getItemId()) {
                        case R.id.Planner:
                            startActivity(new Intent(getApplicationContext()
                                    , Planner.class));
                            overridePendingTransition(0, 0);
                            return true;

                        case R.id.Arwachin:
                            startActivity(new Intent(getApplicationContext()
                                    , ArwachinIndia.class));
                            overridePendingTransition(0, 0);
                            return true;

                        case R.id.DashBoard:
                            startActivity(new Intent(getApplicationContext()
                                    , DashBoard.class));
                            overridePendingTransition(0, 0);
                            return true;

                        case R.id.Profile:
                            startActivity(new Intent(getApplicationContext()
                                    , Profile.class));
                            overridePendingTransition(0, 0);
                            return true;
                    }
                    return false;
                }
            });
            bottomNavigationView.setItemIconTintList(null);
        }

    }


    // Setting so that the on double back press app quits.
    int counter = 0;

    // Function to go back when Clicked the back button twice
    @Override
    public void onBackPressed() {

        counter++;
        if (counter == 2) {
            finishAffinity();
        }
    }


    // Function to check if device is connected to internet
    private boolean isConnected(Student_Nav student_nav) {

        ConnectivityManager connectivityManager = (ConnectivityManager) student_nav.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo wificonn = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobileconn = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if ((wificonn != null && wificonn.isConnected()) || (mobileconn != null && mobileconn.isConnected())) {
            return true;
        } else {
            return false;
        }


    }


    protected void adding1() {

        SharedPreferences sharedPreferences1 = getSharedPreferences(Student_Nav.PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences1.edit();


        if (sharedPreferences1.getString("add1", "").isEmpty()) {
            return;
        } else {
            tv1.setText(sharedPreferences1.getString("add1", ""));
            if (sharedPreferences1.getString("add2", "").isEmpty()) {
                return;
            } else {
                tv2.setText(sharedPreferences1.getString("add2", ""));
                if (sharedPreferences1.getString("add3", "").isEmpty()) {
                    return;
                } else {
                    tv3.setText(sharedPreferences1.getString("add3", ""));
                    if (sharedPreferences1.getString("add4", "").isEmpty()) {
                        return;
                    } else {
                        tv4.setText(sharedPreferences1.getString("add4", ""));
                        if (sharedPreferences1.getString("add5", "").isEmpty()) {
                            return;
                        } else {
                            tv5.setText(sharedPreferences1.getString("add5", ""));
                        }
                    }
                }
            }
        }

    }

    protected void adding2() {
        Bundle a = getIntent().getExtras();

        SharedPreferences sharedPreferences1 = getSharedPreferences(Student_Nav.PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences1.edit();

        if (a != null) {
            if (sharedPreferences1.getString("add1", "").isEmpty()) {
                if (tv1.getText().toString().isEmpty()) {
                    tv1.setText("-> " + a.getString(("add")));
                    editor.putString("add1", tv1.getText().toString());
                    editor.commit();
                } else {
                    return;
                }
            } else {
                tv1.setText(sharedPreferences1.getString("add1", ""));
                if (sharedPreferences1.getString("add2", "").isEmpty()) {
                    if (tv2.getText().toString().isEmpty()) {
                        tv2.setText("-> " + a.getString("add"));
                        editor.putString("add2", tv2.getText().toString());
                        editor.commit();
                    } else {
                        return;
                    }
                } else {
                    tv2.setText(sharedPreferences1.getString("add2", ""));
                    if (sharedPreferences1.getString("add3", "").isEmpty()) {
                        if (tv3.getText().toString().isEmpty()) {
                            tv3.setText("-> " + a.getString("add"));
                            editor.putString("add3", tv3.getText().toString());
                            editor.commit();
                        } else {
                            return;
                        }
                    } else {
                        tv3.setText(sharedPreferences1.getString("add3", ""));
                        if (sharedPreferences1.getString("add4", "").isEmpty()) {
                            if (tv4.getText().toString().isEmpty()) {
                                tv4.setText("-> " + a.getString("add"));
                                editor.putString("add4", tv4.getText().toString());
                                editor.commit();
                            } else {
                                return;
                            }
                        } else {
                            tv4.setText(sharedPreferences1.getString("add4", ""));
                            if (sharedPreferences1.getString("add5", "").isEmpty()) {
                                if (tv5.getText().toString().isEmpty()) {
                                    tv5.setText("-> " + a.getString("add"));
                                    editor.putString("add5", tv5.getText().toString());
                                    editor.commit();
                                } else {
                                    tv5.setText(sharedPreferences1.getString("add5", ""));
                                }
                            } else {
                                tv5.setText(sharedPreferences1.getString("add5", ""));
                                Toast.makeText(this, "To-Do List Full", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }
            }
        } else {
            return;
        }
    }

    // Function
    private void setupPieChart() {
        pieChart1.setDrawHoleEnabled(true);
        pieChart1.setUsePercentValues(true);
        pieChart1.setEntryLabelTextSize(8);
        pieChart1.setEntryLabelColor(Color.WHITE);
        pieChart1.setCenterText("Attendence");
        pieChart1.setCenterTextSize(12);
        pieChart1.getDescription().setEnabled(false);

        Legend l = pieChart1.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setEnabled(true);
    }

    // Function Loading the Pie chart values
    private void loadpiechart() {


        // Variables to set attendence in the pie graphs
        SharedPreferences sharedPreferences1 = getSharedPreferences(Student_Nav.PREFS_NAME, Context.MODE_PRIVATE);
        String Std_ID = sharedPreferences1.getString("stud_id", "");

        String t_days = sharedPreferences1.getString("t_days", null);
        String left_days = sharedPreferences1.getString("left_days", null);
        String p_days = sharedPreferences1.getString("p_days", null);
        String a_days = sharedPreferences1.getString("a_days", null);
        String m_maths = sharedPreferences1.getString("m_maths", null);
        String m_english = sharedPreferences1.getString("m_english", null);
        String m_hindi = sharedPreferences1.getString("m_hindi", null);
        String m_sst = sharedPreferences1.getString("m_sst", null);
        String m_science = sharedPreferences1.getString("m_science", null);

        int T_Days = Integer.parseInt(t_days);
        int P_Days = Integer.parseInt(p_days);
        int A_Days = Integer.parseInt(a_days);
        int L_Days = Integer.parseInt(left_days);



        // Setting marks
        maths_m.setText(m_maths);
        eng_m.setText(m_english);
        hin_m.setText(m_hindi);
        sst_m.setText(m_sst);
        sci_m.setText(m_science);


//                int T_Marks = Integer.parseInt(t_marks);

        int holidays = 365 - T_Days;
        float Holidays = (holidays / 365f);
        float pie_t_days = (T_Days / 365f);
        float pie_left_days = (L_Days / 365f);
        float pie_p_days = (P_Days / 365f);
        float pie_a_days = (A_Days / 365f);


        ArrayList<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(pie_p_days, "Present"));
        entries.add(new PieEntry(pie_a_days, "Absent"));
        entries.add(new PieEntry(Holidays, "School Holidays"));
        entries.add(new PieEntry(pie_left_days, "School Days left"));

        ArrayList<Integer> colors = new ArrayList<>();
        for (int color : ColorTemplate.MATERIAL_COLORS) {
            colors.add(color);
        }
        for (int color : ColorTemplate.VORDIPLOM_COLORS) {
            colors.add(color);
        }
        PieDataSet dataSet = new PieDataSet(entries, "Attendence");
        dataSet.setColors(colors);

        PieData data = new PieData(dataSet);
        data.setDrawValues(true);
        data.setValueFormatter(new PercentFormatter((pieChart1)));
        data.setValueTextSize(12f);
        data.setValueTextColor(android.R.color.white);


        pieChart1.setData(data);
        pieChart1.invalidate();

        pieChart1.animateY(2500, Easing.EaseInOutQuad);


    }

}