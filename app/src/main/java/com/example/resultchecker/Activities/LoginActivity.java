package com.example.resultchecker.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import android.util.Patterns;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.resultchecker.Constants;
import com.example.resultchecker.R;
import com.example.resultchecker.ResponseModel.StaffLoginResponse;
import com.example.resultchecker.ResponseModel.StudentLoginResponse;
import com.example.resultchecker.RetrofitClient;
import com.example.resultchecker.SharedPrefManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    EditText email, password;
    RadioGroup user_group;
    private long backTime;
    Dialog failureDialog, successDialog;
    TextView failure, success, failureOk, successOk;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.textEmail);
        password = findViewById(R.id.textPassword);
        user_group = findViewById(R.id.radioUserGroup);
        TextView register = findViewById(R.id.txtReg);
        final Button login = findViewById(R.id.btnLogin);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Logging...");

        failureDialog = new Dialog(this);
        failureDialog.setContentView(R.layout.custom_failure);
        failureDialog.getWindow().setBackgroundDrawableResource(R.drawable.background);
        failureDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        failureDialog.setCancelable(false);
        failureDialog.getWindow().getAttributes().windowAnimations = R.style.animation;
        failure = failureDialog.findViewById(R.id.txtMessage);
        failureOk = failureDialog.findViewById(R.id.txtOK);


        successDialog = new Dialog(this);
        successDialog.setContentView(R.layout.custom_success);
        successDialog.getWindow().setBackgroundDrawableResource(R.drawable.background);
        successDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        successDialog.setCancelable(false);
        successDialog.getWindow().getAttributes().windowAnimations = R.style.animation;
        success = successDialog.findViewById(R.id.txtMessage);
        successOk = successDialog.findViewById(R.id.txtOK);

        failureOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                failureDialog.dismiss();
            }
        });

        successOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String staff = SharedPrefManager.getStringPreference(getApplicationContext(), Constants.STAFF_ID);
                String student = SharedPrefManager.getStringPreference(getApplicationContext(), Constants.STUDENT_ID);
                if(staff!=null){
                    Intent intent = new Intent(LoginActivity.this, StaffHomeActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                }
                else if(student!=null){
                    Intent intent = new Intent(LoginActivity.this, StudentHomeActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                }
                successDialog.dismiss();
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userEmail = email.getText().toString();
                String userPassword = password.getText().toString();
                if(userEmail.isEmpty()){
                    email.requestFocus();
                    email.setError("Please enter your email");
                }
                else if(!Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()){
                    email.requestFocus();
                    email.setError("Email is not valid");
                }
                else if(userPassword.isEmpty()){
                    password.requestFocus();
                    password.setError("Please enter your password");
                }
                else{
                    int checkUser = user_group.getCheckedRadioButtonId();
                    if(checkUser==-1){
                        String msg = "Please choose an user";
                        failure.setText(msg);
                        failureDialog.show();
                    }
                    else{
                        switch (checkUser){
                            case R.id.radioStaff:
                                loginStaff(userEmail, userPassword);
                                break;
                            case R.id.radioStudent:
                                loginStudent(userEmail, userPassword);
                                break;
                        }
                    }
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (backTime+2000>System.currentTimeMillis()){
            super.onBackPressed();
            return;
        }else{
            Toast.makeText(getBaseContext(), "Press back again to exit", Toast.LENGTH_SHORT).show();
        }
        backTime = System.currentTimeMillis();
    }


    public void loginStaff(String userEmail, String userPassword){
        progressDialog.show();
        Call<StaffLoginResponse> call = RetrofitClient
                .getApi()
                .logStaff(userEmail, userPassword);

        call.enqueue(new Callback<StaffLoginResponse>() {
            @Override
            public void onResponse(Call<StaffLoginResponse> call, Response<StaffLoginResponse> response) {
                StaffLoginResponse loginResponse = response.body();
                if(response.isSuccessful()){
                    String msg = loginResponse.getMessage();
                    if(loginResponse.getError().equals("000")){
                        success.setText(msg);
                        String staff = String.valueOf(loginResponse.getStaff_id());
                        String institute = String.valueOf(loginResponse.getInstitute_id());
                        SharedPrefManager.setStringPreference(getApplicationContext(), Constants.STAFF_ID, staff);
                        SharedPrefManager.setStringPreference(getApplicationContext(), Constants.NAME, loginResponse.getFull_name());
                        SharedPrefManager.setStringPreference(getApplicationContext(), Constants.USER, loginResponse.getUsername());
                        SharedPrefManager.setStringPreference(getApplicationContext(), Constants.EMAIL, loginResponse.getEmail());
                        SharedPrefManager.setStringPreference(getApplicationContext(), Constants.PHONE, loginResponse.getPhone());
                        SharedPrefManager.setStringPreference(getApplicationContext(), Constants.INSTITUTE_ID, institute);
                        SharedPrefManager.setStringPreference(getApplicationContext(), Constants.INSTITUTE, loginResponse.getInstitute_name());
                        progressDialog.dismiss();
                        successDialog.show();
                    }
                    else {
                        failure.setText(msg);
                        progressDialog.dismiss();
                        failureDialog.show();
                    }
                }
                else{
                    String msg = "Response is not a success";
                    failure.setText(msg);
                    progressDialog.dismiss();
                    failureDialog.show();
                }
            }

            @Override
            public void onFailure(Call<StaffLoginResponse> call, Throwable t) {
                String msg = t.getMessage();
                failure.setText(msg);
                progressDialog.dismiss();
                failureDialog.show();
            }
        });
    }

    public void loginStudent(String userEmail, String userPassword){
        progressDialog.show();
        Call<StudentLoginResponse> call = RetrofitClient
                .getApi()
                .logStudent(userEmail, userPassword);

        call.enqueue(new Callback<StudentLoginResponse>() {
            @Override
            public void onResponse(Call<StudentLoginResponse> call, Response<StudentLoginResponse> response) {
                StudentLoginResponse loginResponse = response.body();
                if(response.isSuccessful()){
                    String msg = loginResponse.getMessage();
                    if(loginResponse.getError().equals("000")){
                        success.setText(msg);
                        String student = String.valueOf(loginResponse.getStudent_id());
                        String institute = String.valueOf(loginResponse.getInstitute_id());
                        SharedPrefManager.setStringPreference(getApplicationContext(), Constants.STUDENT_ID, student);
                        SharedPrefManager.setStringPreference(getApplicationContext(), Constants.NAME, loginResponse.getFull_name());
                        SharedPrefManager.setStringPreference(getApplicationContext(), Constants.USER, loginResponse.getUsername());
                        SharedPrefManager.setStringPreference(getApplicationContext(), Constants.EMAIL, loginResponse.getEmail());
                        SharedPrefManager.setStringPreference(getApplicationContext(), Constants.PHONE, loginResponse.getPhone());
                        SharedPrefManager.setStringPreference(getApplicationContext(), Constants.INSTITUTE_ID, institute);
                        SharedPrefManager.setStringPreference(getApplicationContext(), Constants.INSTITUTE, loginResponse.getInstitute_name());
                        progressDialog.dismiss();
                        successDialog.show();
                    }
                    else{
                        failure.setText(msg);
                        progressDialog.dismiss();
                        failureDialog.show();
                    }
                }
                else{
                    String msg = "Response is not a success";
                    failure.setText(msg);
                    progressDialog.dismiss();
                    failureDialog.show();
                }
            }

            @Override
            public void onFailure(Call<StudentLoginResponse> call, Throwable t) {
                String msg = t.getMessage();
                failure.setText(msg);
                progressDialog.dismiss();
                failureDialog.show();
            }
        });
    }

}