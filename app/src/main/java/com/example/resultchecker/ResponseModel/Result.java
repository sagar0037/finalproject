package com.example.resultchecker.ResponseModel;

public class Result {
    private String question_id, question_text, individual_id, answer_text, result_text;

    public Result(String question_id, String question_text, String individual_id, String answer_text, String result_text) {
        this.question_id = question_id;
        this.question_text = question_text;
        this.individual_id = individual_id;
        this.answer_text = answer_text;
        this.result_text = result_text;
    }

    public String getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(String question_id) {
        this.question_id = question_id;
    }

    public String getQuestion_text() {
        return question_text;
    }

    public void setQuestion_text(String question_text) {
        this.question_text = question_text;
    }

    public String getIndividual_id() {
        return individual_id;
    }

    public void setIndividual_id(String individual_id) {
        this.individual_id = individual_id;
    }

    public String getAnswer_text() {
        return answer_text;
    }

    public void setAnswer_text(String answer_text) {
        this.answer_text = answer_text;
    }

    public String getResult_text() {
        return result_text;
    }

    public void setResult_text(String result_text) {
        this.result_text = result_text;
    }
}
