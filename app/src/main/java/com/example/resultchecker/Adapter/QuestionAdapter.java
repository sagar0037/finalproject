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
import com.example.resultchecker.ResponseModel.Question;
import com.example.resultchecker.SharedPrefManager;
import com.example.resultchecker.Student.QuestionActivity;

import java.util.List;

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.QuestionAdapterVH>{
    List<Question> questionList;
    Context context;

    public QuestionAdapter(List<Question> questionList, Context context) {
        this.questionList = questionList;
        this.context = context;
    }

    @NonNull
    @Override
    public QuestionAdapter.QuestionAdapterVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_question, parent, false);
        return new QuestionAdapterVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionAdapter.QuestionAdapterVH holder, int position) {
        String questionText = questionList.get(position).getQuestion_text();
        String questionId = questionList.get(position).getQuestion_id();

        holder.question.setText(questionText);
        holder.questionLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String student = SharedPrefManager.getStringPreference(context, Constants.STUDENT_ID);
                if(student!=null){
                    SharedPrefManager.setStringPreference(context, Constants.QUESTION_ID, questionId);
                    SharedPrefManager.setStringPreference(context, Constants.QUESTION, questionText);
                    Intent intent = new Intent(context, QuestionActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
                else{
                    Toast.makeText(context, "Only student is allowed to give answers", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return questionList.size();
    }

    public class QuestionAdapterVH extends RecyclerView.ViewHolder {
        TextView question;
        RelativeLayout questionLayout;
        public QuestionAdapterVH(@NonNull View itemView) {
            super(itemView);
            question = itemView.findViewById(R.id.txtQuestion);
            questionLayout = itemView.findViewById(R.id.questionLayout);
        }
    }
}
