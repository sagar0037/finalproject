package com.example.resultchecker.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.resultchecker.Constants;
import com.example.resultchecker.R;
import com.example.resultchecker.SharedPrefManager;

import static com.example.resultchecker.SharedPrefManager.getStringPreference;

public class ResultCheckActivity extends AppCompatActivity {
    TextView title, question, answer, result;
    ImageView backImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_result);

        backImg = findViewById(R.id.leftIcon);
        title = findViewById(R.id.barTitle);
        String txtTitle = "Check Result";
        title.setText(txtTitle);

        question = findViewById(R.id.txtQuestion);
        answer = findViewById(R.id.txtAnswer);
        result = findViewById(R.id.txtResult);
        String txtQuestion = getStringPreference(this, Constants.QUESTION);
        String txtAnswer = getStringPreference(this, Constants.ANSWER);
        String txtResult = getStringPreference(this, Constants.RESULT);

        question.setText(txtQuestion);
        answer.setText(txtAnswer);
        result.setText(txtResult);


        backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPrefManager.setStringPreference(ResultCheckActivity.this, Constants.QUESTION_ID, null);
                SharedPrefManager.setStringPreference(ResultCheckActivity.this, Constants.QUESTION, null);
                SharedPrefManager.setStringPreference(ResultCheckActivity.this, Constants.ANSWER, null);
                SharedPrefManager.setStringPreference(ResultCheckActivity.this, Constants.RESULT, null);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        SharedPrefManager.setStringPreference(this, Constants.QUESTION_ID, null);
        SharedPrefManager.setStringPreference(this, Constants.QUESTION, null);
        SharedPrefManager.setStringPreference(this, Constants.ANSWER, null);
        SharedPrefManager.setStringPreference(this, Constants.RESULT, null);
    }
}