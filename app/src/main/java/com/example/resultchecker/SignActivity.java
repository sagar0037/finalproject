package com.example.resultchecker;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class SignActivity extends AppCompatActivity {

    EditText ed1, ed2, ed3, ed4, ed5;
    Button reg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);

        ed1 = (EditText)findViewById(R.id.name);
        ed2 = (EditText)findViewById(R.id.email);
        ed3 = (EditText)findViewById(R.id.user);
        ed4 = (EditText)findViewById(R.id.pass1);
        ed5 = (EditText)findViewById(R.id.pass2);
        reg = (Button)findViewById(R.id.signup);

        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fullname = ed1.getText().toString();
                String email = ed2.getText().toString();
                String username = ed3.getText().toString();
                String password = ed4.getText().toString();
                String password2 = ed5.getText().toString();

                if (TextUtils.isEmpty(fullname) || TextUtils.isEmpty(email) || TextUtils.isEmpty(username) || TextUtils.isEmpty(password) || TextUtils.isEmpty(password2)){
                    Toast.makeText(getApplicationContext(), "Requires all fields", Toast.LENGTH_SHORT).show();
                }

                else if(!password.equals(password2)){
                    Toast.makeText(getApplicationContext(), "Password does not match", Toast.LENGTH_SHORT).show();
                }else {
                    signNewAccount(fullname, email, username, password);
                    Toast.makeText(getApplicationContext(), "Welcome", Toast.LENGTH_LONG).show();
                }
            }

        });
    }

    public void signNewAccount(final String fullname, final String email, final String username, final String password){
        final ProgressDialog progressDialog = new ProgressDialog(SignActivity.this);
        progressDialog.setCancelable(false);
        progressDialog.setIndeterminate(false);
        progressDialog.setTitle("Sign Up New Account");
        progressDialog.show();
        String addUrl = "http://10.0.2.2/register/signup.php";

        StringRequest request = new StringRequest(Request.Method.POST, addUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.equals("Sign Up Successful")){
                    progressDialog.dismiss();
                    Toast.makeText(SignActivity.this, response, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SignActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }else {
                    progressDialog.dismiss();
                    Toast.makeText(SignActivity.this, response, Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(SignActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> params = new HashMap<>();
                params.put("fullname", fullname);
                params.put("email", email);
                params.put("username", username);
                params.put("password", password);
                return params;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(30000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        BackgroundTask.getBgIns(SignActivity.this).addToRequestQueue(request);
    }
}