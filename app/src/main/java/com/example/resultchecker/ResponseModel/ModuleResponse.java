package com.example.resultchecker.ResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class ModuleResponse implements Serializable {
    @SerializedName("module")
    @Expose
    private List<Module> module = null;


    public ModuleResponse(List<Module> module) {
        this.module = module;
    }

    public List<Module> getModule() {
        return module;
    }

    public void setModule(List<Module> module) {
        this.module = module;
    }
}
