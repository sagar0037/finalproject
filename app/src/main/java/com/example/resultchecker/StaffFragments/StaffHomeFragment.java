package com.example.resultchecker.StaffFragments;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.resultchecker.Constants;
import com.example.resultchecker.Adapter.ModuleAdapter;
import com.example.resultchecker.R;
import com.example.resultchecker.ResponseModel.JsonResponse;
import com.example.resultchecker.ResponseModel.Module;
import com.example.resultchecker.ResponseModel.ModuleResponse;
import com.example.resultchecker.RetrofitClient;
import com.example.resultchecker.SharedPrefManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StaffHomeFragment extends Fragment {
    RecyclerView recyclerView;
    EditText code, name;
    List<Module> moduleList;
    ModuleAdapter moduleAdapter;
    Dialog dialog;
    SwipeRefreshLayout swipeRefreshLayout;

    private CarouselView carouselView;

    private int[] sImage = new int[]{
            R.drawable.image1, R.drawable.image2, R.drawable.image3, R.drawable.image4
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_staff_home, container, false);

        FloatingActionButton fModule = view.findViewById(R.id.fab_module);

        carouselView = view.findViewById(R.id.sildingImage);
        carouselView.setPageCount(sImage.length);
        recyclerView = view.findViewById(R.id.moduleView);

        carouselView.setImageListener(new ImageListener() {
            @Override
            public void setImageForPosition(int position, ImageView imageView) {
                imageView.setImageResource(sImage[position]);
            }
        });

        dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.module_staff);

        dialog.getWindow().setBackgroundDrawableResource(R.drawable.background);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(false);
        dialog.getWindow().getAttributes().windowAnimations = R.style.animation;

        Button cancelBtn = dialog.findViewById(R.id.btnCancel);
        Button addBtn = dialog.findViewById(R.id.btnAdd);
        ImageView cancelImg = dialog.findViewById(R.id.imgCancel);
        code = dialog.findViewById(R.id.textModuleCode);
        name = dialog.findViewById(R.id.textModuleName);
        ;
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
                String moduleCode = code.getText().toString();
                String moduleName = name.getText().toString();
                String staffId = SharedPrefManager.getStringPreference(getContext(), Constants.STAFF_ID);

                Call<JsonResponse> call = RetrofitClient
                        .getApi()
                        .addModule(moduleCode, moduleName, staffId);
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


        fModule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
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
        code.setText(null);
        name.setText(null);
        dialog.dismiss();
    }

    void loadModule(){
        String staffId = SharedPrefManager.getStringPreference(getContext(), Constants.STAFF_ID);
        Call<ModuleResponse> call = RetrofitClient
                .getApi()
                .fetchModule(staffId);

        call.enqueue(new Callback<ModuleResponse>() {
            @Override
            public void onResponse(Call<ModuleResponse> call, Response<ModuleResponse> response) {
                ModuleResponse moduleResponse = response.body();
                if(response.isSuccessful()){
                    moduleList = response.body().getModule();
                    moduleAdapter = new ModuleAdapter(moduleList, getContext());
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                    recyclerView.setAdapter(moduleAdapter);
                }
                else{
                    Toast.makeText(getContext(), "Response is not successful", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ModuleResponse> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
