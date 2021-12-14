package com.example.resultchecker.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.resultchecker.Student.InModuleActivity;
import com.example.resultchecker.Constants;
import com.example.resultchecker.R;
import com.example.resultchecker.ResponseModel.InModule;
import com.example.resultchecker.SharedPrefManager;

import java.util.List;

public class InModuleAdapter extends RecyclerView.Adapter<InModuleAdapter.InModuleAdapterVH> {

    private List<InModule> inModuleList;
    private Context context;

    public InModuleAdapter(List<InModule> inModuleList, Context context) {
        this.inModuleList = inModuleList;
        this.context = context;
    }

    @NonNull
    @Override
    public InModuleAdapter.InModuleAdapterVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_module, parent, false);
        return new InModuleAdapterVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InModuleAdapter.InModuleAdapterVH holder, int position) {
        String moduleCode = inModuleList.get(position).getModule_code();
        String moduleName = inModuleList.get(position).getModule_name();
        String individualId = inModuleList.get(position).getIndividual_id();

        holder.moduleNameText.setText(moduleName);
        holder.moduleCodeText.setText(moduleCode);

        holder.moduleView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, InModuleActivity.class);
                SharedPrefManager.setStringPreference(context, Constants.REF_MODULE, moduleName);
                SharedPrefManager.setStringPreference(context, Constants.MODULE, moduleCode);
                SharedPrefManager.setStringPreference(context, Constants.INDIVIDUAL, individualId);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return inModuleList.size();
    }

    public class InModuleAdapterVH extends RecyclerView.ViewHolder {
        TextView moduleCodeText, moduleNameText;
        CardView moduleView;
        public InModuleAdapterVH(@NonNull View itemView) {
            super(itemView);
            moduleCodeText = itemView.findViewById(R.id.moduleCode);
            moduleNameText = itemView.findViewById(R.id.moduleName);
            moduleView = itemView.findViewById(R.id.cardView);
        }
    }
}
