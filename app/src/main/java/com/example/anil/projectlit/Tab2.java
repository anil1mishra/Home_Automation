package com.example.anil.projectlit;

/**
 * Created by Anil on 03-10-2017.
 */
import android.support.v4.app.Fragment;
import android.os.Bundle;
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

public class Tab2 extends Fragment {
    DatabaseReference mrootref = FirebaseDatabase.getInstance().getReference();
    DatabaseReference House1 = mrootref.child("House1");
    DatabaseReference appliances = House1.child("appliances");
    DatabaseReference switch3 = appliances.child("switch3");
    DatabaseReference switch4 = appliances.child("switch4");
    DatabaseReference switch5 = appliances.child("switch5");
    Switch s1;
    Switch s2;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab2, container, false);
        s1 = (Switch)rootView.findViewById(R.id.switch21);
        s2 = (Switch)rootView.findViewById(R.id.switch22);
        return rootView;
    }
    @Override
    public void onStart() {
        super.onStart();
        switch3.addValueEventListener(new ValueEventListener() {
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
                Log.d("tab2","tab2");
            }
        });
        switch4.addValueEventListener(new ValueEventListener() {
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
                Log.d("tab2","tab2");
            }
        });

        s1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    switch3.setValue("on");
                else
                    switch3.setValue("off");
            }
        });
        s2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    switch4.setValue("on");
                else
                    switch4.setValue("off");
            }
        });
    }
}
