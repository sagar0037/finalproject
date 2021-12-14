package com.example.resultchecker.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.resultchecker.Constants;
import com.example.resultchecker.R;
import com.example.resultchecker.SharedPrefManager;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Thread thread = new Thread(){
            public void run(){
                try{
                    sleep(3000);
                }catch (Exception ex){
                    ex.printStackTrace();
                }
                finally {
                    String staff = SharedPrefManager.getStringPreference(getApplicationContext(), Constants.STAFF_ID);
                    String student = SharedPrefManager.getStringPreference(getApplicationContext(), Constants.STUDENT_ID);
                    if(staff!=null){
                        Intent intent = new Intent(SplashActivity.this, StaffHomeActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }
                    else if(student!=null){
                        Intent intent = new Intent(SplashActivity.this, StudentHomeActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }
                    else{
                        Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }
                }
            }
        };
        thread.start();
    }
}