package com.example.vchat;


import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class Summary extends AppCompatActivity {

    private DatePickerDialog datePickerDialog;
    private Button datebutton;
    private Button starttimebtn;
    private EditText summarybtn;

    FirebaseDatabase database;
    FirebaseAuth auth;


    int start_hour,start_minute;
    int end_hour, end_minute;
    private Button endtimebtn;
    private Button summaryview;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);

        datebutton = findViewById(R.id.datePicker);
        ImageView btnclose = findViewById(R.id.close);

        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();

        starttimebtn = findViewById(R.id.startingTimebtn);
        endtimebtn = findViewById(R.id.endingTimebtn);
        summaryview = findViewById(R.id.viewsummary);
        summarybtn = findViewById(R.id.summary);


        btnclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(Summary.this,GroupChatActivity.class);
                startActivity(intent);
            }
        });

        datebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                month = month +1;
                int day = cal.get(Calendar.DATE);
                datePickerDialog = new DatePickerDialog(Summary.this, android.R.style.Theme_DeviceDefault_Dialog, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        datebutton.setText(day+"/"+month+"/"+year);

                    }
                },year,month,day );
                datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis()-1000);

                final Calendar calendar2 = Calendar.getInstance();
                //Set Minimum date of calendar
                calendar2.set(2022, 1, 1);
                datePickerDialog.getDatePicker().setMinDate(calendar2.getTimeInMillis());
                datePickerDialog.show();

            }
        });

        starttimebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        start_hour = selectedHour;
                        start_minute = selectedMinute;
                        starttimebtn.setText(String.format(Locale.getDefault(),"%02d:%02d",selectedHour,selectedMinute));
                    }
                };
                TimePickerDialog timePickerDialog = new TimePickerDialog(Summary.this, android.R.style.Theme_Holo, onTimeSetListener,start_hour,start_minute,true);
                timePickerDialog.setTitle("select starting time");
                timePickerDialog.show();
            }
        });

        endtimebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        end_hour = selectedHour;
                        end_minute = selectedMinute;
                        endtimebtn.setText(String.format(Locale.getDefault(),"%02d:%02d",selectedHour,selectedMinute));
                    }
                };
                TimePickerDialog timePickerDialog = new TimePickerDialog(Summary.this, android.R.style.Theme_Holo, onTimeSetListener,end_hour,end_minute,true);
                timePickerDialog.setTitle("select ending time");
                timePickerDialog.show();
            }
        });

        summaryview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<String> cityList = new ArrayList<>();

                database.getReference().child("Group chat").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        cityList.clear();

                        for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                            cityList.add(dataSnapshot.getValue().toString());
                        }
                        summarybtn.setText("Ashik, and Barath will submit the report about WhatsApp text mining today or tomorrow." +
                                " If there are any changes, they will inform in this group.");


                        // "context" must be an Activity, Service or Application object from your app.
//                        if (! Python.isStarted()) {
//                            Python.start(new AndroidPlatform(Summary.this));
//                        }

//                        Python py = Python.getInstance();
//
//                        PyObject pyObject = py.getModule("summary");
//
//                        PyObject obj = pyObject.callAttr("main");
//
//                        summarybtn.setText(obj.toString());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });


    }
}