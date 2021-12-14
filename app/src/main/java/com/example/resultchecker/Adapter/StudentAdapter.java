package com.example.resultchecker.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.resultchecker.Staff.IndividualActivity;
import com.example.resultchecker.Constants;
import com.example.resultchecker.R;
import com.example.resultchecker.ResponseModel.Student;
import com.example.resultchecker.SharedPrefManager;

import java.util.List;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.StudentAdapterVH> {
    List<Student> studentList;
    Context context;

    public StudentAdapter(List<Student> studentList, Context context) {
        this.studentList = studentList;
        this.context = context;
    }

    @NonNull
    @Override
    public StudentAdapter.StudentAdapterVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_student, parent,false);
        return new StudentAdapterVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentAdapter.StudentAdapterVH holder, int position) {
        String username = studentList.get(position).getUsername();
        String individual = studentList.get(position).getIndividual_id();
        holder.txtUser.setText(username);
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPrefManager.setStringPreference(context, Constants.INDIVIDUAL, individual);
                SharedPrefManager.setStringPreference(context, Constants.REF_USER, username);
                Intent intent = new Intent(context, IndividualActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return studentList.size();
    }

    public class StudentAdapterVH extends RecyclerView.ViewHolder {
        RelativeLayout relativeLayout;
        TextView txtUser;
        public StudentAdapterVH(@NonNull View itemView) {
            super(itemView);
            txtUser= itemView.findViewById(R.id.txtUsername);
            relativeLayout = itemView.findViewById(R.id.studentLayout);
        }
    }
}


