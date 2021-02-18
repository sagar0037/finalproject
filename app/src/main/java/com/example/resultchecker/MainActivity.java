package com.example.resultchecker;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    EditText ed1, ed2;
    TextView signUp;
    Button login;
    //CheckBox logged;
    //SharedPreferences prefer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ed1 = (EditText)findViewById(R.id.email);
        ed2 = (EditText)findViewById(R.id.password);
        signUp = (TextView)findViewById(R.id.sign);
        login = (Button)findViewById(R.id.login);
        //prefer = getSharedPreferences("User Information", Context.MODE_PRIVATE);

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SignActivity.class);
                startActivity(intent);
                finish();
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = ed1.getText().toString();
                String password = ed2.getText().toString();

                if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password)){
                    Toast.makeText(getApplicationContext(), "Requires all fields", Toast.LENGTH_SHORT).show();
                }
                else{
                    login(email, password);
                }
            }
        });

        /*String loginStatus = prefer.getString(getResources().getString(R.string.prefLogged), "");
        if(loginStatus.equals("Logged In")){
            startActivity(new Intent(MainActivity.this, HomePage.class));
        }*/
    }

    public void login(final String email, final String password){
        final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setCancelable(false);
        progressDialog.setIndeterminate(false);
        progressDialog.setTitle("Login to an account");
        progressDialog.show();
        String addUrl = "http://10.0.2.2/register/login.php";

        StringRequest request = new StringRequest(Request.Method.POST, addUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.equals("Login Successful")){
                    progressDialog.dismiss();
                    Toast.makeText(MainActivity.this, response, Toast.LENGTH_SHORT).show();
                    /*SharedPreferences.Editor editor = prefer.edit();

                    if(logged.isChecked()){
                        editor.putString(getResources().getString(R.string.prefLogged), "Logged In");
                    }
                    else {
                        editor.putString(getResources().getString(R.string.prefLogged), "Logged Out");
                    }

                    editor.apply();*/
                    Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                    startActivity(intent);
                }else {
                    progressDialog.dismiss();
                    Toast.makeText(MainActivity.this, response, Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> params = new HashMap<>();
                params.put("email", email);
                params.put("password", password);
                return params;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(30000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        BackgroundTask.getBgIns(MainActivity.this).addToRequestQueue(request);
    }

}