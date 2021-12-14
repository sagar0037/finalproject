package com.example.resultchecker.ResponseModel;

public class StudentLoginResponse {
    private int student_id;
    private String full_name, username, email, phone;
    private int institute_id;
    private String institute_name;
    String error, message;

    public StudentLoginResponse(int student_id, String full_name, String username, String email, String phone, int institute_id, String institute_name, String error, String message) {
        this.student_id = student_id;
        this.full_name = full_name;
        this.username = username;
        this.email = email;
        this.phone = phone;
        this.institute_id = institute_id;
        this.institute_name = institute_name;
        this.error = error;
        this.message = message;
    }

    public int getStudent_id() {
        return student_id;
    }

    public void setStudent_id(int student_id) {
        this.student_id = student_id;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getInstitute_id() {
        return institute_id;
    }

    public void setInstitute_id(int institute_id) {
        this.institute_id = institute_id;
    }

    public String getInstitute_name() {
        return institute_name;
    }

    public void setInstitute_name(String institute_name) {
        this.institute_name = institute_name;
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

