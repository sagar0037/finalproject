package com.example.resultchecker.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.resultchecker.Adapter.AnswerAdapter;
import com.example.resultchecker.Constants;
import com.example.resultchecker.R;
import com.example.resultchecker.ResponseModel.Answer;
import com.example.resultchecker.ResponseModel.AnswerResponse;
import com.example.resultchecker.RetrofitClient;
import com.example.resultchecker.SharedPrefManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AnsweredQuestionActivity extends AppCompatActivity {
    TextView title;
    ImageView backImg;
    RecyclerView recyclerView;
    List<Answer> answerList;
    AnswerAdapter answerAdapter;
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answered_question);

        backImg = findViewById(R.id.leftIcon);
        title = findViewById(R.id.barTitle);
        String txtTitle = "Answered Questions";
        title.setText(txtTitle);
        String individualId = SharedPrefManager.getStringPreference(this, Constants.INDIVIDUAL);

        backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        loadQuestions(individualId);

        swipeRefreshLayout = findViewById(R.id.swipeQuestions);
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
        Call<AnswerResponse> call = RetrofitClient
                .getApi()
                .fetchAnswer(individualId);

        call.enqueue(new Callback<AnswerResponse>() {
            @Override
            public void onResponse(Call<AnswerResponse> call, Response<AnswerResponse> response) {
                AnswerResponse answerResponse = response.body();
                if(response.isSuccessful()){
                    if(answerResponse.getError().equals("000")){
                        answerList = response.body().getAnswers();
                        answerAdapter = new AnswerAdapter(answerList, getApplicationContext());
                        recyclerView = findViewById(R.id.questionView);
                        recyclerView.setHasFixedSize(true);
                        recyclerView.setAdapter(answerAdapter);
                        recyclerView.setLayoutManager(new LinearLayoutManager(AnsweredQuestionActivity.this));
                    }
                    else {
                        Toast.makeText(getApplicationContext(), answerResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(getApplicationContext(), "Response is not successful", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<AnswerResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}