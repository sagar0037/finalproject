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

import com.example.resultchecker.Staff.ModuleActivity;
import com.example.resultchecker.Constants;
import com.example.resultchecker.R;
import com.example.resultchecker.ResponseModel.Module;
import com.example.resultchecker.SharedPrefManager;

import java.util.List;

public class ModuleAdapter extends RecyclerView.Adapter<ModuleAdapter.ModuleAdapterVH> {

    private List<Module> moduleList;
    private Context context;

    public ModuleAdapter(List<Module> moduleList, Context context) {
        this.moduleList = moduleList;
        this.context = context;
    }


    @NonNull
    @Override
    public ModuleAdapter.ModuleAdapterVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_module, parent,false);
        return new ModuleAdapterVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ModuleAdapter.ModuleAdapterVH holder, int position) {
        String moduleName = moduleList.get(position).getModule_name();
        String moduleCode = moduleList.get(position).getModule_code();
        holder.moduleNameText.setText(moduleName);
        holder.moduleCodeText.setText(moduleCode);

        holder.moduleView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ModuleActivity.class);
                SharedPrefManager.setStringPreference(context, Constants.MODULE, moduleCode);
                SharedPrefManager.setStringPreference(context, Constants.REF_MODULE, moduleName);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return moduleList.size();
    }

    public class ModuleAdapterVH extends RecyclerView.ViewHolder{
        TextView moduleCodeText, moduleNameText;
        CardView moduleView;
        public ModuleAdapterVH(@NonNull View itemView) {
            super(itemView);
            moduleCodeText = itemView.findViewById(R.id.moduleCode);
            moduleNameText = itemView.findViewById(R.id.moduleName);
            moduleView = itemView.findViewById(R.id.cardView);
        }
    }


}