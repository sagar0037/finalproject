package com.example.resultchecker.StudentFragments;

import android.app.Dialog;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.resultchecker.Constants;
import com.example.resultchecker.Adapter.InModuleAdapter;
import com.example.resultchecker.R;
import com.example.resultchecker.ResponseModel.InModule;
import com.example.resultchecker.ResponseModel.InModuleResponse;
import com.example.resultchecker.ResponseModel.JsonResponse;
import com.example.resultchecker.RetrofitClient;
import com.example.resultchecker.SharedPrefManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StudentHomeFragment extends Fragment {
    RecyclerView recyclerView;
    List<InModule> inModuleList;
    InModuleAdapter inModuleAdapter;
    Dialog dialog;
    CarouselView carouselView;
    Button cancelBtn, addBtn;
    EditText moduleCode;
    SwipeRefreshLayout swipeRefreshLayout;

    private int[] sImage = new int[]{
            R.drawable.image1, R.drawable.image2, R.drawable.image3, R.drawable.image4
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_student_home, container, false);

        recyclerView = view.findViewById(R.id.inModuleView);

        carouselView = view.findViewById(R.id.sildingImage);
        carouselView.setPageCount(sImage.length);

        carouselView.setImageListener(new ImageListener() {
            @Override
            public void setImageForPosition(int position, ImageView imageView) {
                imageView.setImageResource(sImage[position]);
            }
        });


        FloatingActionButton fModule = view.findViewById(R.id.fab_module);

        fModule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
            }
        });

        dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.module_student);

        dialog.getWindow().setBackgroundDrawableResource(R.drawable.background);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(false);
        dialog.getWindow().getAttributes().windowAnimations = R.style.animation;

        moduleCode = dialog.findViewById(R.id.txtModuleCode);
        cancelBtn = dialog.findViewById(R.id.btnCancel);
        addBtn = dialog.findViewById(R.id.btnAdd);
        ImageView cancelImg = dialog.findViewById(R.id.imgCancel);

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismissDialog();
            }
        });

        cancelImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismissDialog();
            }
        });

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String module = moduleCode.getText().toString();
                String student = SharedPrefManager.getStringPreference(getContext(), Constants.STUDENT_ID);
                String institute = SharedPrefManager.getStringPreference(getContext(), Constants.INSTITUTE_ID);
                Call<JsonResponse> call = RetrofitClient
                        .getApi()
                        .individualModule(module, student, institute);

                call.enqueue(new Callback<JsonResponse>() {
                    @Override
                    public void onResponse(Call<JsonResponse> call, Response<JsonResponse> response) {
                        JsonResponse jsonResponse = response.body();
                        if (response.isSuccessful()) {
                            if (jsonResponse.getError().equals("000")) {
                                dismissDialog();
                            }
                        }
                        Toast.makeText(getContext(), jsonResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<JsonResponse> call, Throwable t) {
                        Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        loadModule();

        swipeRefreshLayout = view.findViewById(R.id.swipeModules);
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadModule();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        return view;
    }

    void dismissDialog(){
        moduleCode.setText(null);
        dialog.dismiss();
    }

    void loadModule(){
        String studentId = SharedPrefManager.getStringPreference(getContext(), Constants.STUDENT_ID);

        Call<InModuleResponse> call = RetrofitClient
                .getApi()
                .fetchInModule(studentId);

        call.enqueue(new Callback<InModuleResponse>() {
            @Override
            public void onResponse(Call<InModuleResponse> call, Response<InModuleResponse> response) {
                if(response.isSuccessful()){
                    inModuleList = response.body().getInModule();
                    inModuleAdapter = new InModuleAdapter(inModuleList, getContext());
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                    recyclerView.setAdapter(inModuleAdapter);
                }
                else{
                    Toast.makeText(getContext(), "Response is not successful", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<InModuleResponse> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}