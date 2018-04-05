package com.example.anil.projectlit;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Switch;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;



 public class SensorService extends Service{

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

    private Date date;
     private Timer timer;
     private TimerTask timerTask;

    private String compareStringOne = "9:45:00";
    private String compareStringTwo = "1:45:00";
     private Date dateCompare1One,dateCompare2One;
     private Date dateCompare1Two,dateCompare2Two;

    public static int counter=0;
    public SensorService(Context applicationContext) {
        super();
        Log.i("HERE", "here I am!");
    }

    public SensorService() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {Log.i("start", "here I am!");
        super.onStartCommand(intent, flags, startId);
        timer = new Timer();
        dateCompare1One=parseDate(compareStringOne);dateCompare1Two=parseDate(compareStringTwo);
        dateCompare2One=parseDate(compareStringTwo);dateCompare2Two=parseDate(compareStringTwo);
        startTimer();
        listener();
        //compareDates();
        return START_STICKY;
    }

    private void listener() {
        switch1Start.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String sr = dataSnapshot.getValue().toString();
                dateCompare1One = parseDate(sr);Log.v("listen",dateCompare1One.toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        switch1End.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String sr = dataSnapshot.getValue().toString();
                dateCompare1Two = parseDate(sr);Log.v("listen",dateCompare1Two.toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        switch2Start.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String sr = dataSnapshot.getValue().toString();
                dateCompare2One = parseDate(sr);Log.v("listen",dateCompare1Two.toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        switch2End.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String sr = dataSnapshot.getValue().toString();
                dateCompare2Two = parseDate(sr);Log.v("listen",dateCompare1Two.toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("EXIT", "ondestroy!");
        stoptimertask();
    }


    long oldTime=0;
    public void startTimer() {
        //set a new Timer


        //initialize the TimerTask's job
        Log.v("aaaa","hey");
        initializeTimerTask();

        //schedule the timer, to wake up every 1 second
        timer.schedule(timerTask, 1000, 1000); //
    }

    /**
     * it sets the timer to print the counter every x seconds
     */
    public void initializeTimerTask() {
        timerTask = new TimerTask() {
            public void run() {
                compareDates();

               // Log.v("chk","error");
            }
        };
    }

    /**
     * not needed
     */
    public void stoptimertask() {
        //stop the timer, if it's not already null
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    //comparing time
    public static final String inputFormat = "HH:mm:ss";



    SimpleDateFormat inputParser = new SimpleDateFormat(inputFormat);


    private void compareDates() {
        Calendar now = Calendar.getInstance();

        int hour = now.get(Calendar.HOUR);
        int minute = now.get(Calendar.MINUTE);
        int second = now.get(Calendar.SECOND);

        date = parseDate(hour + ":" + minute + ":" + second);

            Log.v("hello", dateCompare1One.toString() + " " + date.toString() + " " + dateCompare1Two.toString());
            Log.v("hello", dateCompare2One.toString() + " " + date.toString() + " " + dateCompare2Two.toString());

            if (dateCompare1One.equals(date)) {

                Log.v("chk","prev");
                switch1.setValue("on");
            }
            if (dateCompare1Two.equals(date)) {
                Log.v("chk","next");
                switch1.setValue("off");
            }

            if (dateCompare2One.equals(date)) {

                switch2.setValue("on");
            }
            if (dateCompare2Two.equals(date)) {
                switch2.setValue("off");
            }

    }

    private Date parseDate(String date) {

        try {
            return inputParser.parse(date);
        } catch (java.text.ParseException e) {
            return new Date(0);
        }
    }
}
