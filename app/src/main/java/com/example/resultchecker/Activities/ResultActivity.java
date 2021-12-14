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
import com.example.resultchecker.Adapter.ResultAdapter;
import com.example.resultchecker.Constants;
import com.example.resultchecker.R;
import com.example.resultchecker.ResponseModel.AnswerResponse;
import com.example.resultchecker.ResponseModel.Result;
import com.example.resultchecker.ResponseModel.ResultResponse;
import com.example.resultchecker.RetrofitClient;
import com.example.resultchecker.SharedPrefManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResultActivity extends AppCompatActivity {
    TextView title;
    ImageView backImg;
    RecyclerView recyclerView;
    List<Result> resultList;
    ResultAdapter resultAdapter;
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        backImg = findViewById(R.id.leftIcon);
        title = findViewById(R.id.barTitle);
        String txtTitle = "Checked Questions";
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
        Call<ResultResponse> call = RetrofitClient
                .getApi()
                .fetchResult(individualId);

        call.enqueue(new Callback<ResultResponse>() {
            @Override
            public void onResponse(Call<ResultResponse> call, Response<ResultResponse> response) {
                ResultResponse resultResponse = response.body();
                if(response.isSuccessful()){
                    if(resultResponse.getError().equals("000")){
                        resultList = response.body().getResult();
                        resultAdapter = new ResultAdapter(resultList, getApplicationContext());
                        recyclerView = findViewById(R.id.questionView);
                        recyclerView.setHasFixedSize(true);
                        recyclerView.setAdapter(resultAdapter);
                        recyclerView.setLayoutManager(new LinearLayoutManager(ResultActivity.this));
                    }
                    else {
                        Toast.makeText(getApplicationContext(), resultResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(getApplicationContext(), "Response is not successful", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResultResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}