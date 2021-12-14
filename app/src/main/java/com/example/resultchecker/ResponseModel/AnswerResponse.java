package com.example.resultchecker.ResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AnswerResponse {
    @SerializedName("answers")
    @Expose
    private List<Answer> answers = null;
    String error, message;

    public AnswerResponse(List<Answer> answers, String error, String message) {
        this.answers = answers;
        this.error = error;
        this.message = message;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
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
