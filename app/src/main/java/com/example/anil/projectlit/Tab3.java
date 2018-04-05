package com.example.anil.projectlit;

/**
 * Created by Anil on 03-10-2017.
 */
import android.os.Build;
import android.support.annotation.RequiresApi;
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

public class Tab3 extends Fragment{

    DatabaseReference mrootref = FirebaseDatabase.getInstance().getReference();
    DatabaseReference House1 = mrootref.child("House1");
    DatabaseReference appliances = House1.child("appliances");
    DatabaseReference switch5 = appliances.child("switch5");
    Switch s1;
    //Switch s2;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab3, container, false);
        s1 = (Switch)rootView.findViewById(R.id.switch31);
        //s2 = (Switch)rootView.findViewById(R.id.switch32);
        return rootView;
    }
    @Override
    public void onStart() {
        super.onStart();
        switch5.addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.ICE_CREAM_SANDWICH)
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
                Log.d("tab3","tab3");
            }
        });
//        light2.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                String sr = dataSnapshot.getValue().toString();
//                if(sr.equals("on"))
//                    s2.setChecked(true);
//                else
//                    s2.setChecked(false);
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                Log.d("tab3","tab3");
//            }
//        });
        s1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    switch5.setValue("on");
                else
                    switch5.setValue("off");
            }
        });
//        s2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if (isChecked)
//                    light2.setValue("on");
//                else
//                    light2.setValue("off");
//            }
//        });
    }
}
