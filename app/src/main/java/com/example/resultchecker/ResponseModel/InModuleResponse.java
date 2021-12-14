package com.example.resultchecker.ResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class InModuleResponse {
    @SerializedName("inmodule")
    @Expose
    private List<InModule> inModule = null;

    public InModuleResponse(List<InModule> inModule) {
        this.inModule = inModule;
    }

    public List<InModule> getInModule() {
        return inModule;
    }

    public void setInModule(List<InModule> inModule) {
        this.inModule = inModule;
    }
}
