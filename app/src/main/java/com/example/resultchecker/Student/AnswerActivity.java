package com.example.resultchecker.Student;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.resultchecker.Constants;
import com.example.resultchecker.R;
import com.example.resultchecker.SharedPrefManager;

import static com.example.resultchecker.SharedPrefManager.getStringPreference;

public class AnswerActivity extends AppCompatActivity {
    TextView title, question, answer;
    ImageView backImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer);

        backImg = findViewById(R.id.leftIcon);
        title = findViewById(R.id.barTitle);
        String txtTitle = "View Answer";
        title.setText(txtTitle);

        question = findViewById(R.id.txtQuestion);
        answer = findViewById(R.id.txtAnswer);
        String txtQuestion = getStringPreference(this, Constants.QUESTION);
        String txtAnswer = getStringPreference(this, Constants.ANSWER);

        question.setText(txtQuestion);
        answer.setText(txtAnswer);

        backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearData();
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        clearData();
    }

    private void clearData(){
        SharedPrefManager.setStringPreference(this, Constants.QUESTION_ID, null);
        SharedPrefManager.setStringPreference(this, Constants.QUESTION, null);
        SharedPrefManager.setStringPreference(this, Constants.ANSWER, null);
    }
}