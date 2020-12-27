package com.example.resultchecker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Sign extends AppCompatActivity {

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

                if(fullname.equals("") || email.equals("") || username.equals("") || password.equals("") || password2.equals("")){
                    Toast.makeText(getApplicationContext(), "Requires all fields", Toast.LENGTH_SHORT).show();
                }
                else if(password!=password2){
                    Toast.makeText(getApplicationContext(), "Password does not match", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getApplicationContext(), "Welcome", Toast.LENGTH_SHORT).show();
                }
            }

        });
    }
}