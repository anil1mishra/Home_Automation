package com.example.anil.projectlit;

/**
 * Created by Anil on 03-10-2017.
 */
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Tab1 extends Fragment {
    DatabaseReference mrootref = FirebaseDatabase.getInstance().getReference();
    DatabaseReference House1 = mrootref.child("House1");
    DatabaseReference appliances = House1.child("appliances");
    DatabaseReference switch1 = appliances.child("switch1");
    DatabaseReference switch2 = appliances.child("switch2");
    Switch s1;
    Switch s2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab1, container, false);
        s1 = (Switch)rootView.findViewById(R.id.switch11);
        s2 = (Switch)rootView.findViewById(R.id.switch12);
        return rootView;

    }
    @Override
    public void onStart() {
        super.onStart();
        switch1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String sr = dataSnapshot.getValue().toString();
                if(sr.equals("on"))
                    s1.setChecked(true);
                else
                    s1.setChecked(false);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("tab1","tab1");
            }
        });
        switch2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String sr = dataSnapshot.getValue().toString();
                if(sr.equals("on"))
                    s2.setChecked(true);
                else
                    s2.setChecked(false);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("tab1","tab1");
            }
        });

        s1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    switch1.setValue("on");
                else
                    switch1.setValue("off");
            }
        });
        s2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    switch2.setValue("on");
                else
                    switch2.setValue("off");
            }
        });
    }
}