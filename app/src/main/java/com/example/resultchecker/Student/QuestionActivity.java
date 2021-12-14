package com.example.resultchecker.Student;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.resultchecker.Constants;
import com.example.resultchecker.R;
import com.example.resultchecker.ResponseModel.JsonResponse;
import com.example.resultchecker.RetrofitClient;
import com.example.resultchecker.SharedPrefManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuestionActivity extends AppCompatActivity {
    TextView title, question;
    EditText answer;
    ImageView backImg;
    Button btnSendAnswer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        question = findViewById(R.id.txtQuestion);
        answer = findViewById(R.id.txtAnswer);
        backImg = findViewById(R.id.leftIcon);
        title = findViewById(R.id.barTitle);
        btnSendAnswer = findViewById(R.id.btnSendAns);

        String questionText = SharedPrefManager.getStringPreference(this, Constants.QUESTION);
        String questionId = SharedPrefManager.getStringPreference(this, Constants.QUESTION_ID);
        question.setText(questionText);
        String titleText = "Give Answer";
        title.setText(titleText);

        backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearData();
                finish();
            }
        });

        btnSendAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String answerText = answer.getText().toString();
                if(answerText.isEmpty()){
                    answer.requestFocus();
                    answer.setError("Please enter the question");
                }
                else if(answerText.length()<10){
                    answer.requestFocus();
                    answer.setError("Result must not be less than 10 letters");
                }
                else{
                    Call<JsonResponse> call = RetrofitClient
                            .getApi()
                            .addAnswer(answerText, questionId);
                    call.enqueue(new Callback<JsonResponse>() {
                        @Override
                        public void onResponse(Call<JsonResponse> call, Response<JsonResponse> response) {
                            JsonResponse jsonResponse = response.body();
                            if(response.isSuccessful()){
                                if(jsonResponse.getError().equals("000")){
                                    clearData();
                                    finish();
                                }
                                else {
                                    Toast.makeText(getApplicationContext(), jsonResponse.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                            else{
                                Toast.makeText(getApplicationContext(), "Response is not success", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<JsonResponse> call, Throwable t) {
                            Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        clearData();
    }

    private void clearData(){
        SharedPrefManager.setStringPreference(this, Constants.QUESTION, null);
        SharedPrefManager.setStringPreference(this, Constants.QUESTION_ID, null);
    }
}