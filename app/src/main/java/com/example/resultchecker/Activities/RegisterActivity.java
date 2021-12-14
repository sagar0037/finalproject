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
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.resultchecker.R;
import com.example.resultchecker.ResponseModel.JsonResponse;
import com.example.resultchecker.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    EditText full_name, username, email, phone, school_id, password, cPassword;
    RadioGroup user_group;
    TextView title;
    ImageView backImg;
    Dialog failureDialog, successDialog;
    TextView failure, success, failureOk, successOk;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        full_name = findViewById(R.id.txtFname);
        username = findViewById(R.id.txtUsername);
        email = findViewById(R.id.txtEmail);
        phone = findViewById(R.id.txtPhone);
        school_id = findViewById(R.id.txtSchoolId);
        password = findViewById(R.id.txtPassword);
        cPassword = findViewById(R.id.txtConfPassword);
        user_group = findViewById(R.id.schoolGroup);
        TextView login = findViewById(R.id.txtLog);
        Button register = findViewById(R.id.btnRegister);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Registering...");

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
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
                successDialog.dismiss();
            }
        });

        backImg = findViewById(R.id.leftIcon);
        title = findViewById(R.id.barTitle);
        String reg = "Register Your Account";

        title.setText(reg);
        backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fullName = full_name.getText().toString();
                String userName = username.getText().toString();
                String userEmail = email.getText().toString();
                String userPhone = phone.getText().toString();
                String userPassword = password.getText().toString();
                String confPassword = cPassword.getText().toString();
                String userSchool = school_id.getText().toString();

                if(fullName.isEmpty()){
                    full_name.requestFocus();
                    full_name.setError("Please enter your full name");
                }
                else if(userName.isEmpty()){
                    username.requestFocus();
                    username.setError("Please enter your username");
                }
                else if(userEmail.isEmpty()){
                    email.requestFocus();
                    email.setError("Please enter your email");
                }
                else if(!Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()){
                    email.requestFocus();
                    email.setError("Email is not valid");
                }
                else if(userPhone.isEmpty()){
                    phone.requestFocus();
                    phone.setError("Please enter your phone number");
                }
                else if(userSchool.isEmpty()){
                    school_id.requestFocus();
                    school_id.setError("Please enter your school id");
                }
                else if(userPassword.isEmpty()){
                    password.requestFocus();
                    password.setError("Please enter your password");
                }
                else if(userPassword.length()<6){
                    password.requestFocus();
                    password.setError("Password must not be less than 6");
                }
                else if(confPassword.isEmpty()){
                    cPassword.requestFocus();
                    cPassword.setError("Please confirmPass your password");
                }
                else if(!userPassword.equals(confPassword)){
                    cPassword.requestFocus();
                    cPassword.setError("Password do not match to each other");
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
                            case R.id.staffRBtn:
                                registerStaff(fullName, userName, userEmail, userPassword, userPhone, userSchool);
                                break;
                            case R.id.studentRBtn:
                                registerStudent(fullName, userName, userEmail, userPassword, userPhone, userSchool);
                                break;
                        }
                    }
                }
            }
        });
    }


    public void registerStaff(String fullName, String userName, String userEmail, String userPassword, String userPhone, String userSchool) {
        progressDialog.show();
        Call<JsonResponse> call = RetrofitClient
                .getApi()
                .regStaff(fullName, userName, userEmail, userPassword, userPhone, userSchool);
        call.enqueue(new Callback<JsonResponse>() {
            @Override
            public void onResponse(Call<JsonResponse> call, Response<JsonResponse> response) {
                JsonResponse jsonResponse = response.body();
                if(response.isSuccessful()){
                    String msg = jsonResponse.getMessage();
                    if(jsonResponse.getError().equals("000")){
                        success.setText(msg);
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
            public void onFailure(Call<JsonResponse> call, Throwable t) {
                String msg = t.getMessage();
                failure.setText(msg);
                progressDialog.dismiss();
                failureDialog.show();
            }
        });
    }

    public void registerStudent(String fullName, String userName, String userEmail, String userPassword, String userPhone, String userSchool) {
        progressDialog.show();
        Call<JsonResponse> call = RetrofitClient
                .getApi()
                .regStudent(fullName, userName, userEmail, userPassword, userPhone, userSchool);
        call.enqueue(new Callback<JsonResponse>() {
            @Override
            public void onResponse(Call<JsonResponse> call, Response<JsonResponse> response) {
                JsonResponse jsonResponse = response.body();
                if(response.isSuccessful()){
                    String msg = jsonResponse.getMessage();
                    if(jsonResponse.getError().equals("000")){
                        success.setText(msg);
                        progressDialog.dismiss();
                        successDialog.show();
                    }
                    else{
                        failure.setText(msg);
                        failureDialog.show();
                    }
                }
                else{
                    String msg = "Response is not a success";
                    failure.setText(msg);
                    progressDialog.dismiss();
                    failureDialog.show();                }
            }

            @Override
            public void onFailure(Call<JsonResponse> call, Throwable t) {
                String msg = t.getMessage();
                failure.setText(msg);
                progressDialog.dismiss();
                failureDialog.show();
            }
        });
    }
}