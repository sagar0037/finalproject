package com.example.resultchecker.StaffFragments;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.resultchecker.Activities.PaymentActivity;
import com.example.resultchecker.Constants;
import com.example.resultchecker.R;
import com.example.resultchecker.ResponseModel.JsonResponse;
import com.example.resultchecker.RetrofitClient;
import com.example.resultchecker.SharedPrefManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StaffProfileFragment extends Fragment {
    Dialog dialog;
    TextView txtName, txtUser, txtMail, txtPhone, txtInst, chnPassword, donation;
    EditText current, new1, new2;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_staff_profile,container,false);

        txtName = view.findViewById(R.id.txtFullName);
        txtUser = view.findViewById(R.id.txtUsername);
        txtMail = view.findViewById(R.id.txtEmail);
        txtPhone = view.findViewById(R.id.txtPhoneNo);
        txtInst = view.findViewById(R.id.txtInstitute);
        chnPassword = view.findViewById(R.id.changePassword);
        donation = view.findViewById(R.id.donateUs);

        String staffId = SharedPrefManager.getStringPreference(getContext(), Constants.STAFF_ID);
        String staffName = SharedPrefManager.getStringPreference(getContext(), Constants.NAME);
        String staffUser = SharedPrefManager.getStringPreference(getContext(), Constants.USER);
        String staffMail = SharedPrefManager.getStringPreference(getContext(), Constants.EMAIL);
        String staffPhone = SharedPrefManager.getStringPreference(getContext(), Constants.PHONE);
        String staffInst = SharedPrefManager.getStringPreference(getContext(), Constants.INSTITUTE);

        txtName.setText(staffName);
        txtUser.setText(staffUser);
        txtMail.setText(staffMail);
        txtPhone.setText(staffPhone);
        txtInst.setText(staffInst);

        dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.change_password);

        dialog.getWindow().setBackgroundDrawableResource(R.drawable.background);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(false);
        dialog.getWindow().getAttributes().windowAnimations = R.style.animation;
        current = dialog.findViewById(R.id.currentPassword);
        new1 = dialog.findViewById(R.id.newPassword);
        new2 = dialog.findViewById(R.id.confirmPassword);
        ImageView cancelImg = dialog.findViewById(R.id.imgCancel);
        Button updatePass = dialog.findViewById(R.id.btnUpdatePassword);


        cancelImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearData();
                dialog.dismiss();
            }
        });

        updatePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String oldPass = current.getText().toString();
                String newPass = new1.getText().toString();
                String confPass = new2.getText().toString();
                if(oldPass.isEmpty()){
                    current.requestFocus();
                    current.setError("Please enter your current password");
                }
                else if(newPass.isEmpty()){
                    new1.requestFocus();
                    new1.setError("Please enter your new password");
                }
                else if(newPass.length()<6){
                    new1.requestFocus();
                    new1.setError("Password must not be less than 6");
                }
                else if(confPass.isEmpty()){
                    new2.requestFocus();
                    new2.setError("Please confirmPass your password");
                }
                else if(!newPass.equals(confPass)){
                    new2.requestFocus();
                    new2.setError("Password do not match to each other");
                }
                else{
                    Call<JsonResponse> call = RetrofitClient
                            .getApi()
                            .staffPass(oldPass, newPass, staffId);
                    call.enqueue(new Callback<JsonResponse>() {
                        @Override
                        public void onResponse(Call<JsonResponse> call, Response<JsonResponse> response) {
                            JsonResponse jsonResponse = response.body();
                            if(response.isSuccessful()){
                                if(jsonResponse.getError().equals("000")){
                                    clearData();
                                    dialog.dismiss();
                                }
                                Toast.makeText(getContext(), jsonResponse.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                            else{
                                Toast.makeText(getContext(), "Response is not success", Toast.LENGTH_SHORT).show();
                            }

                        }

                        @Override
                        public void onFailure(Call<JsonResponse> call, Throwable t) {
                            Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            }
        });


        chnPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
            }
        });

        donation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), PaymentActivity.class));
            }
        });

        return view;
    }

    void clearData(){
        current.setText(null);
        new1.setText(null);
        new2.setText(null);
    }

}
