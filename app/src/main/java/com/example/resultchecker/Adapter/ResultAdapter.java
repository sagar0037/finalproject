package com.example.resultchecker.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.resultchecker.Activities.ResultCheckActivity;
import com.example.resultchecker.Constants;
import com.example.resultchecker.R;
import com.example.resultchecker.ResponseModel.Result;
import com.example.resultchecker.SharedPrefManager;

import java.util.List;

public class ResultAdapter extends RecyclerView.Adapter<ResultAdapter.ResultAdapterVH> {
    List<Result> resultList;
    Context context;

    public ResultAdapter(List<Result> resultList, Context context) {
        this.resultList = resultList;
        this.context = context;
    }

    @NonNull
    @Override
    public ResultAdapterVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_question, parent, false);
        return new ResultAdapterVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ResultAdapterVH holder, int position) {
        String questionText = resultList.get(position).getQuestion_text();
        String questionId = resultList.get(position).getQuestion_id();
        String answerText = resultList.get(position).getAnswer_text();
        String resultText = resultList.get(position).getResult_text();

        holder.question.setText(questionText);
        holder.questionLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Answers", Toast.LENGTH_LONG).show();
                SharedPrefManager.setStringPreference(context, Constants.QUESTION_ID, questionId);
                SharedPrefManager.setStringPreference(context, Constants.QUESTION, questionText);
                SharedPrefManager.setStringPreference(context, Constants.ANSWER, answerText);
                SharedPrefManager.setStringPreference(context, Constants.RESULT, resultText);
                Intent intent = new Intent(context, ResultCheckActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return resultList.size();
    }

    public class ResultAdapterVH extends RecyclerView.ViewHolder {
        TextView question;
        RelativeLayout questionLayout;
        public ResultAdapterVH(@NonNull View itemView) {
            super(itemView);
            question = itemView.findViewById(R.id.txtQuestion);
            questionLayout = itemView.findViewById(R.id.questionLayout);
        }
    }
}
