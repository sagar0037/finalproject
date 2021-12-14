package com.example.resultchecker.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.resultchecker.Constants;
import com.example.resultchecker.R;
import com.example.resultchecker.SharedPrefManager;
import com.example.resultchecker.StaffFragments.StaffHomeFragment;
import com.example.resultchecker.StaffFragments.StaffProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class StaffHomeActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private BottomNavigationView btnNavView;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_home);

        btnNavView = findViewById(R.id.bottomNav);
        btnNavView.setOnNavigationItemSelectedListener(this);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Logging Out");

        Toolbar toolbar = findViewById(R.id.bar);
        setSupportActionBar(toolbar);
        loadFragment(new StaffHomeFragment());
    }

    void loadFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.relativeLayout, fragment).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_nav, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.action_logout){
                logoutUser();
        }
        return super.onOptionsItemSelected(item);
    }

    void logoutUser(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Do you really want to logout?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        progressDialog.show();
                        SharedPrefManager.setStringPreference(getApplicationContext(), Constants.STAFF_ID, null);
                        SharedPrefManager.setStringPreference(getApplicationContext(), Constants.NAME, null);
                        SharedPrefManager.setStringPreference(getApplicationContext(), Constants.USER, null);
                        SharedPrefManager.setStringPreference(getApplicationContext(), Constants.EMAIL, null);
                        SharedPrefManager.setStringPreference(getApplicationContext(), Constants.PHONE, null);
                        SharedPrefManager.setStringPreference(getApplicationContext(), Constants.INSTITUTE_ID, null);
                        SharedPrefManager.setStringPreference(getApplicationContext(), Constants.INSTITUTE, null);
                        Intent intent = new Intent(StaffHomeActivity.this, SplashActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                        progressDialog.dismiss();
                        Toast.makeText(StaffHomeActivity.this, "You have been logged out", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.setTitle("Logout");
        alertDialog.show();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;
        switch (item.getItemId()) {
            case R.id.home_nav:
                fragment = new StaffHomeFragment();
                break;
            case R.id.profile_nav:
                fragment = new StaffProfileFragment();
                break;
        }
        if (fragment != null) {
            loadFragment(fragment);
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        if(btnNavView.getSelectedItemId()==R.id.home_nav){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Do you really want to exit?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finishAffinity();
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alertDialog = builder.create();
            alertDialog.setTitle("Exit");
            alertDialog.show();
        }
        else{
            btnNavView.setSelectedItemId(R.id.home_nav);
        }


    }
}