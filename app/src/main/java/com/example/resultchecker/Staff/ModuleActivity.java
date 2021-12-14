package com.example.resultchecker.Staff;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.resultchecker.Adapter.StudentAdapter;
import com.example.resultchecker.Constants;
import com.example.resultchecker.R;
import com.example.resultchecker.ResponseModel.StudentResponse;
import com.example.resultchecker.ResponseModel.Student;
import com.example.resultchecker.RetrofitClient;
import com.example.resultchecker.SharedPrefManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ModuleActivity extends AppCompatActivity {
    TextView title;
    ImageView backImg;
    RecyclerView recyclerView;
    List<Student> studentList;
    StudentAdapter studentAdapter;
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_module);

        recyclerView = findViewById(R.id.studentView);
        backImg = findViewById(R.id.leftIcon);
        title = findViewById(R.id.barTitle);
        String moduleName = SharedPrefManager.getStringPreference(this, Constants.REF_MODULE);
        title.setText(moduleName);
        backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPrefManager.setStringPreference(getApplicationContext(), Constants.MODULE, null);
                SharedPrefManager.setStringPreference(getApplicationContext(), Constants.REF_MODULE, null);
                finish();
            }
        });

        loadStudents();

        swipeRefreshLayout = findViewById(R.id.swipeModules);
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadStudents();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

    }

    void loadStudents(){
        String moduleCode = SharedPrefManager.getStringPreference(getApplicationContext(), Constants.MODULE);
        Call<StudentResponse> call = RetrofitClient
                .getApi()
                .fetchStudent(moduleCode);

        call.enqueue(new Callback<StudentResponse>() {
            @Override
            public void onResponse(Call<StudentResponse> call, Response<StudentResponse> response) {
                StudentResponse studentResponse = response.body();
                if(response.isSuccessful()){
                    studentList = studentResponse.getStudents();
                    studentAdapter = new StudentAdapter(studentList, getApplicationContext());
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setAdapter(studentAdapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(ModuleActivity.this));
                }
                else{
                    Toast.makeText(getApplicationContext(), "Response is not successful", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<StudentResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        SharedPrefManager.setStringPreference(getApplicationContext(), Constants.MODULE, null);
        SharedPrefManager.setStringPreference(getApplicationContext(), Constants.REF_MODULE, null);
    }
}