package com.example.resultchecker.ResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResultResponse {
    @SerializedName("result")
    @Expose
    private List<Result> result = null;
    String error, message;

    public ResultResponse(List<Result> result, String error, String message) {
        this.result = result;
        this.error = error;
        this.message = message;
    }

    public List<Result> getResult() {
        return result;
    }

    public void setResult(List<Result> result) {
        this.result = result;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
