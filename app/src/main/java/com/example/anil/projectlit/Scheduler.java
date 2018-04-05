package com.example.anil.projectlit;

import android.app.TimePickerDialog;
import android.icu.util.Calendar;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TimePicker;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;

import static android.icu.util.Calendar.DATE;
import static android.icu.util.Calendar.getInstance;

public class Scheduler extends AppCompatActivity {

    DatabaseReference mrootref = FirebaseDatabase.getInstance().getReference();
    DatabaseReference House1 = mrootref.child("House1");
    DatabaseReference schedule = House1.child("Schedule");
    DatabaseReference switch1Start = schedule.child("device1").child("startTime");
    DatabaseReference switch1End = schedule.child("device1").child("endTime");
    DatabaseReference switch2Start = schedule.child("device2").child("startTime");
    DatabaseReference switch2End = schedule.child("device2").child("endTime");
    DatabaseReference switch3Start = schedule.child("switch1").child("startTime");
    DatabaseReference switch3End = schedule.child("switch1").child("endTime");
    DatabaseReference switch4Start = schedule.child("switch1").child("startTime");
    DatabaseReference switch4End = schedule.child("switch1").child("endTime");
    DatabaseReference switch5Start = schedule.child("switch1").child("startTime");
    DatabaseReference switch5End = schedule.child("switch1").child("endTime");

    DatabaseReference appliances = House1.child("appliances");
    DatabaseReference switch1 = appliances.child("switch1");
    DatabaseReference switch2 = appliances.child("switch2");
    DatabaseReference switch3 = appliances.child("switch3");
    DatabaseReference switch4 = appliances.child("switch4");
    DatabaseReference switch5 = appliances.child("switch5");

    int mm,yy,dd,hh,mm1,ss;
    static int position1=0;
    EditText e1 ;
    EditText e2 ;
    Button b1;
    Spinner s;
    String s1,s2;
    String a[] = {"device1","device2","device3","device4","device5"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scheduler);
        e1 = (EditText)findViewById(R.id.editText2);
        e2 = (EditText)findViewById(R.id.editText3);

        s = (Spinner) findViewById(R.id.spinner);
        b1 = (Button)findViewById(R.id.button1);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                s1 = e1.getText().toString()+":00";
                s2 = e2.getText().toString()+":00";
                if(position1 == 0){

                  switch1Start.setValue(s1);
                    switch1End.setValue(s2);
                }
                else if(position1 == 1){
                    switch2Start.setValue(s1);
                    switch2End.setValue(s2);
                }
            }
        });

        s.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                position1 = position;
                Log.v("adapter",String.valueOf(position));

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        Adapter adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, a);
        s.setAdapter((SpinnerAdapter) adapter);

    }
}
