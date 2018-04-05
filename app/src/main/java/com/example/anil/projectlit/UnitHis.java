package com.example.anil.projectlit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Time;

public class UnitHis extends AppCompatActivity {

    DatabaseReference mrootref = FirebaseDatabase.getInstance().getReference();
    DatabaseReference House1 = mrootref.child("House1");
    DatabaseReference unit = House1.child("unit");
    DatabaseReference device1 = unit.child("device1");
    DatabaseReference device2 = unit.child("device2");
    Spinner s;
    int position1;
    String storedevice1,storedevice2;
    TextView t1;
    String a[] = {"Device 1","Device 2","Device 3","Device 4","Device 5"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unit_his);
        setListener();
        t1 = (TextView)findViewById(R.id.tv1);
        s = (Spinner) findViewById(R.id.spinner);
        s.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                position1 = position;
                if(position1 == 0){
                    t1.setText(storedevice1);
                }
                else if(position1 == 1){
                    t1.setText(storedevice2);
                }
                Log.v("adapter",String.valueOf(position));

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        Adapter adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, a);
        s.setAdapter((SpinnerAdapter) adapter);
    }

    private void setListener() {
        device1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {Log.v("ok","done");
                storedevice1=dataSnapshot.getValue().toString();
                Log.v("ok",storedevice1.toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        device2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                storedevice2=dataSnapshot.getValue().toString();
                Log.v("ok",storedevice2);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


}
