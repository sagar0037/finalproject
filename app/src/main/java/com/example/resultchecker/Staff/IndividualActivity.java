package com.example.resultchecker.Staff;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.resultchecker.Activities.AnsweredQuestionActivity;
import com.example.resultchecker.Activities.ResultActivity;
import com.example.resultchecker.Adapter.QuestionAdapter;
import com.example.resultchecker.Constants;
import com.example.resultchecker.R;
import com.example.resultchecker.ResponseModel.JsonResponse;
import com.example.resultchecker.ResponseModel.Question;
import com.example.resultchecker.ResponseModel.QuestionResponse;
import com.example.resultchecker.RetrofitClient;
import com.example.resultchecker.SharedPrefManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class IndividualActivity extends AppCompatActivity {

    TextView title;
    ImageView backImg;
    FloatingActionButton fab;
    Dialog dialog;
    EditText question;
    RecyclerView recyclerView;
    List<Question> questionList;
    QuestionAdapter questionAdapter;
    Button btnAnswer, btnResult;
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_individual);

        btnAnswer = findViewById(R.id.btnViewAnswers);
        btnResult = findViewById(R.id.btnViewResult);
        backImg = findViewById(R.id.leftIcon);
        title = findViewById(R.id.barTitle);
        fab = findViewById(R.id.fab_question);
        String userName = SharedPrefManager.getStringPreference(this, Constants.REF_USER);;
        title.setText(userName);

        String individual = SharedPrefManager.getStringPreference(IndividualActivity.this, Constants.INDIVIDUAL);
        loadQuestions(individual);

        dialog = new Dialog(this);
        dialog.setContentView(R.layout.add_question);
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.background);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(false);
        dialog.getWindow().getAttributes().windowAnimations = R.style.animation;
        question = dialog.findViewById(R.id.txtQuestion);
        ImageView cancelImg = dialog.findViewById(R.id.imgCancel);
        Button addQuestion = dialog.findViewById(R.id.btnAddQuestion);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
            }
        });

        btnAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(IndividualActivity.this, AnsweredQuestionActivity.class);
                startActivity(intent);
            }
        });

        btnResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(IndividualActivity.this, ResultActivity.class);
                startActivity(intent);
            }
        });

        addQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String questionText = question.getText().toString();
                if(questionText.isEmpty()){
                    question.requestFocus();
                    question.setError("Please enter the question");
                }
                else if(questionText.length()<10){
                    question.requestFocus();
                    question.setError("Question must not be less than 10 letters");
                }
                else{
                    Call<JsonResponse> call = RetrofitClient
                            .getApi()
                            .addQuestion(questionText, individual);

                    call.enqueue(new Callback<JsonResponse>() {
                        @Override
                        public void onResponse(Call<JsonResponse> call, Response<JsonResponse> response) {
                            JsonResponse jsonResponse = response.body();
                            if(response.isSuccessful()){
                                if(jsonResponse.getError().equals("000")){
                                    clearData();
                                    dialog.dismiss();
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

                    Toast.makeText(IndividualActivity.this, "Question Added", Toast.LENGTH_LONG).show();
                    clearData();
                    dialog.dismiss();
                }

            }
        });

        cancelImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearData();
                dialog.dismiss();
            }
        });

        backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPrefManager.setStringPreference(getApplicationContext(), Constants.INDIVIDUAL, null);
                SharedPrefManager.setStringPreference(getApplicationContext(), Constants.REF_USER, null);
                finish();
            }
        });

        swipeRefreshLayout = findViewById(R.id.swipeQuestions);
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadQuestions(individual);
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    private void clearData() {
        question.setText(null);
    }

    void loadQuestions(String individual){

        Call<QuestionResponse> call = RetrofitClient
                .getApi()
                .fetchQuestion(individual);

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
                        recyclerView.setLayoutManager(new LinearLayoutManager(IndividualActivity.this));
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
        SharedPrefManager.setStringPreference(getApplicationContext(), Constants.INDIVIDUAL, null);
        SharedPrefManager.setStringPreference(getApplicationContext(), Constants.REF_USER, null);
    }
}