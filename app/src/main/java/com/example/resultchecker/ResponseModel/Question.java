package com.example.resultchecker.ResponseModel;

public class Question {
    private String question_id, question_text, individual_id;

    public Question(String question_id, String question_text, String individual_id) {
        this.question_id = question_id;
        this.question_text = question_text;
        this.individual_id = individual_id;
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
}
