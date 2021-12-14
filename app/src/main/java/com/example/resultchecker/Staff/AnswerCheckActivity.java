package com.example.resultchecker.Staff;

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

import static com.example.resultchecker.SharedPrefManager.getStringPreference;

public class AnswerCheckActivity extends AppCompatActivity {
    Button sendAnswer;
    TextView title, question, answer;
    EditText result;
    ImageView backImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_answer);

        backImg = findViewById(R.id.leftIcon);
        title = findViewById(R.id.barTitle);
        sendAnswer = findViewById(R.id.btnSendAnswer);
        String txtTitle = "Check Answer";
        title.setText(txtTitle);

        question = findViewById(R.id.txtQuestion);
        answer = findViewById(R.id.txtAnswer);
        result = findViewById(R.id.etResult);
        String txtQuestion = getStringPreference(this, Constants.QUESTION);
        String txtAnswer = getStringPreference(this, Constants.ANSWER);
        String answerId = getStringPreference(this, Constants.ANSWER_ID);

        question.setText(txtQuestion);
        answer.setText(txtAnswer);

        backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearData();
                finish();
            }
        });

        sendAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String resultText = result.getText().toString();
                if(resultText.isEmpty()){
                    result.requestFocus();
                    result.setError("Please enter the question");
                }
                else if(resultText.length()<10){
                    result.requestFocus();
                    result.setError("Result must not be less than 10 letters");
                }
                else {
                    Call<JsonResponse> call = RetrofitClient
                            .getApi()
                            .addResult(resultText, answerId);
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
        SharedPrefManager.setStringPreference(this, Constants.QUESTION_ID, null);
        SharedPrefManager.setStringPreference(this, Constants.QUESTION, null);
        SharedPrefManager.setStringPreference(this, Constants.ANSWER, null);
        SharedPrefManager.setStringPreference(this, Constants.ANSWER_ID, null);
    }
}