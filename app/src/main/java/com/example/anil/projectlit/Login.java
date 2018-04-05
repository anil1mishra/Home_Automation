package com.example.anil.projectlit;

import android.app.ActivityManager;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import static android.app.PendingIntent.getActivity;



import android.app.Dialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static android.app.PendingIntent.getActivity;

public class Login extends AppCompatActivity implements Runnable{

    Intent mServiceIntent;
    private SensorService mSensorService;

    Context ctx;

    Intent i;
    public Context getCtx() {
        return ctx;
    }

    private FirebaseAuth mauth;
    private  FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        i = new Intent(this,UnitHis.class);
        startActivity(i);
        ctx = this;
        Log.v("Before","Before");
        new Thread((Runnable) getCtx(),"run").start();Log.v("after","after");

        setContentView(R.layout.activity_login);

        mauth = FirebaseAuth.getInstance();
        final  EditText e = (EditText)findViewById(R.id.et1);
        final EditText e1 = (EditText)findViewById(R.id.et2);

        Button b = (Button)findViewById(R.id.b1);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final  String s= e.getText().toString(); final String s1 = e1.getText().toString();
                if (TextUtils.isEmpty(s) || TextUtils.isEmpty(s1)){
                    Toast.makeText(Login.this,"fields are empty",Toast.LENGTH_SHORT).show();
                }
                else {
                    mauth.signInWithEmailAndPassword(s, s1).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                Toast.makeText(Login.this, "sign in pblm", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }



        });
        mAuthListener =new FirebaseAuth.AuthStateListener(){

            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() != null){
                    Intent i1 = new Intent(getApplicationContext(),User_Navigate.class);
                    startActivity(i1);
                }
            }
        };
    }

    protected void onStart(){
        super.onStart();
        mauth.addAuthStateListener(mAuthListener);
    //    Log.v("aaaaa", String.valueOf(isMyServiceRunning(mSensorService.getClass())));
    }


    @Override
    protected void onDestroy() {
        //stopService(mServiceIntent);
        Log.i("MAINACT", "onDestroy!");
        super.onDestroy();

    }
    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                Log.i ("isMyServiceRunning?", true+"");
                return true;
            }
        }
        Log.i ("isMyServiceRunning?", false+"");
        return false;
    }
    @Override
    public void run() {

        Log.v("run","run");
        mSensorService = new SensorService(getCtx());
        mServiceIntent = new Intent(getCtx(), mSensorService.getClass());
        if (!isMyServiceRunning(mSensorService.getClass())) {
            Log.v("inside","if");
            startService(mServiceIntent);
        }
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }
}