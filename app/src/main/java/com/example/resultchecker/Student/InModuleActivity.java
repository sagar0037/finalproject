package com.example.resultchecker.Student;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.resultchecker.Activities.AnsweredQuestionActivity;
import com.example.resultchecker.Activities.ResultActivity;
import com.example.resultchecker.Adapter.QuestionAdapter;
import com.example.resultchecker.Constants;
import com.example.resultchecker.R;
import com.example.resultchecker.ResponseModel.Question;
import com.example.resultchecker.ResponseModel.QuestionResponse;
import com.example.resultchecker.RetrofitClient;
import com.example.resultchecker.SharedPrefManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InModuleActivity extends AppCompatActivity {
    TextView title;
    ImageView backImg;
    List<Question> questionList;
    QuestionAdapter questionAdapter;
    RecyclerView recyclerView;
    Button btnAnswer, btnResult;
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_module);

        String moduleName = SharedPrefManager.getStringPreference(getApplicationContext(), Constants.REF_MODULE);
        String individualId = SharedPrefManager.getStringPreference(getApplicationContext(), Constants.INDIVIDUAL);
        btnAnswer = findViewById(R.id.btnViewAnswers);
        btnResult = findViewById(R.id.btnViewResult);
        backImg = findViewById(R.id.leftIcon);
        title = findViewById(R.id.barTitle);
        title.setText(moduleName);

        loadQuestions(individualId);

        backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPrefManager.setStringPreference(getApplicationContext(), Constants.MODULE, null);
                SharedPrefManager.setStringPreference(getApplicationContext(), Constants.INDIVIDUAL, null);
                SharedPrefManager.setStringPreference(getApplicationContext(), Constants.REF_MODULE, null);
                finish();
            }
        });

        btnAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InModuleActivity.this, AnsweredQuestionActivity.class);
                startActivity(intent);
            }
        });

        btnResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InModuleActivity.this, ResultActivity.class);
                startActivity(intent);
            }
        });

        swipeRefreshLayout = findViewById(R.id.swipeModules);
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadQuestions(individualId);
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    void loadQuestions(String individualId){
        Call<QuestionResponse> call = RetrofitClient
                .getApi()
                .fetchQuestion(individualId);

        call.enqueue(new Callback<QuestionResponse>() {
            @Override
            public void onResponse(Call<QuestionResponse> call, Response<QuestionResponse> response) {
                QuestionResponse questionResponse = response.body();
                if(response.isSuccessful()){
                    if(questionResponse.getError().equals("000")){
                        questionList = response.body().getQuestions();
                        questionAdapter = new QuestionAdapter(questionList, getApplicationContext());
                        recyclerView = findViewById(R.id.questionView);
                        recyclerView.setHasFixedSize(true);
                        recyclerView.setAdapter(questionAdapter);
                        recyclerView.setLayoutManager(new LinearLayoutManager(InModuleActivity.this));
                    }
                    else {
                        Toast.makeText(getApplicationContext(), questionResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(getApplicationContext(), "Response is not successful", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<QuestionResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        SharedPrefManager.setStringPreference(getApplicationContext(), Constants.MODULE, null);
        SharedPrefManager.setStringPreference(getApplicationContext(), Constants.INDIVIDUAL, null);
        SharedPrefManager.setStringPreference(getApplicationContext(), Constants.REF_MODULE, null);
    }
}