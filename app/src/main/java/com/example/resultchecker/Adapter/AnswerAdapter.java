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

import com.example.resultchecker.Constants;
import com.example.resultchecker.R;
import com.example.resultchecker.ResponseModel.Answer;
import com.example.resultchecker.SharedPrefManager;
import com.example.resultchecker.Staff.AnswerCheckActivity;
import com.example.resultchecker.Student.AnswerActivity;

import java.util.List;

public class AnswerAdapter extends RecyclerView.Adapter<AnswerAdapter.AnswerAdapterVH> {
    List<Answer> answerList;
    Context context;

    public AnswerAdapter(List<Answer> answerList, Context context) {
        this.answerList = answerList;
        this.context = context;
    }

    @NonNull
    @Override
    public AnswerAdapterVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_question, parent, false);
        return new AnswerAdapterVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AnswerAdapterVH holder, int position) {
        String questionText = answerList.get(position).getQuestion_text();
        String questionId = answerList.get(position).getQuestion_id();
        String answerId = answerList.get(position).getAnswer_id();
        String answerText = answerList.get(position).getAnswer_text();

        holder.question.setText(questionText);
        holder.questionLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String staff = SharedPrefManager.getStringPreference(context, Constants.STAFF_ID);
                SharedPrefManager.setStringPreference(context, Constants.QUESTION_ID, questionId);
                SharedPrefManager.setStringPreference(context, Constants.QUESTION, questionText);
                SharedPrefManager.setStringPreference(context, Constants.ANSWER, answerText);
                if(staff!=null){
                    SharedPrefManager.setStringPreference(context, Constants.ANSWER_ID, answerId);
                    Toast.makeText(context, "Answers", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(context, AnswerCheckActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
                else{
                    Toast.makeText(context, "Only staff is allowed to check answers", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(context, AnswerActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return answerList.size();
    }

    public class AnswerAdapterVH extends RecyclerView.ViewHolder {
        TextView question;
        RelativeLayout questionLayout;
        public AnswerAdapterVH(@NonNull View itemView) {
            super(itemView);
            question = itemView.findViewById(R.id.txtQuestion);
            questionLayout = itemView.findViewById(R.id.questionLayout);
        }
    }
}
