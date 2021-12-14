package com.example.resultchecker;

import com.example.resultchecker.ResponseModel.AnswerResponse;
import com.example.resultchecker.ResponseModel.ResultResponse;
import com.example.resultchecker.ResponseModel.StudentResponse;
import com.example.resultchecker.ResponseModel.InModuleResponse;
import com.example.resultchecker.ResponseModel.JsonResponse;
import com.example.resultchecker.ResponseModel.ModuleResponse;
import com.example.resultchecker.ResponseModel.QuestionResponse;
import com.example.resultchecker.ResponseModel.StaffLoginResponse;
import com.example.resultchecker.ResponseModel.StudentLoginResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface Api {

    //REGISTER
    @FormUrlEncoded
    @POST("staffregister.php")
    Call<JsonResponse> regStaff(
            @Field("full_name") String full_name,
            @Field("username") String username,
            @Field("email") String email,
            @Field("password") String password,
            @Field("phone") String phone,
            @Field("institute_id") String institute_id
    );

    @FormUrlEncoded
    @POST("studentregister.php")
    Call<JsonResponse> regStudent(
            @Field("full_name") String full_name,
            @Field("username") String username,
            @Field("email") String email,
            @Field("password") String password,
            @Field("phone") String phone,
            @Field("institute_id") String institute_id
    );

    //LOGIN
    @FormUrlEncoded
    @POST("stafflogin.php")
    Call<StaffLoginResponse> logStaff(
            @Field("email") String email,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("studentlogin.php")
    Call<StudentLoginResponse> logStudent(
            @Field("email") String email,
            @Field("password") String password
    );

    //CHANGE PASSWORD
    @FormUrlEncoded
    @POST("staffpassword.php")
    Call<JsonResponse> staffPass(
            @Field("oldPass") String oldPass,
            @Field("newPass") String newPass,
            @Field("staff_id") String staff_id
    );

    @FormUrlEncoded
    @POST("studentpassword.php")
    Call<JsonResponse> studentPass(
            @Field("oldPass") String oldPass,
            @Field("newPass") String newPass,
            @Field("student_id") String student_id
    );

    //MODULE
    @FormUrlEncoded
    @POST("addmodule.php")
    Call<JsonResponse> addModule(
            @Field("module_code") String module_code,
            @Field("module_name") String module_name,
            @Field("staff_id") String staff_id
    );


    @FormUrlEncoded
    @POST("fetchmodule.php")
    Call<ModuleResponse> fetchModule(
            @Field("staff_id") String staff_id
    );

    //INDIVIDUAL MODULE
    @FormUrlEncoded
    @POST("individualmodule.php")
    Call<JsonResponse> individualModule(
            @Field("module_code") String module_code,
            @Field("student_id") String student_id,
            @Field("institute_id") String institute_id
    );

    @FormUrlEncoded
    @POST("fetchinmodule.php")
    Call<InModuleResponse> fetchInModule(
            @Field("student_id") String student_id
    );


    @FormUrlEncoded
    @POST("fetchstudent.php")
    Call<StudentResponse> fetchStudent(
            @Field("module_code") String module_code
    );

    //QUESTIONS
    @FormUrlEncoded
    @POST("addquestions.php")
    Call<JsonResponse> addQuestion(
            @Field("question_text") String question_text,
            @Field("individual_id") String individual_id
    );

    @FormUrlEncoded
    @POST("fetchquestions.php")
    Call<QuestionResponse> fetchQuestion(
            @Field("individual_id") String individual_id
    );

    //ANSWERS
    @FormUrlEncoded
    @POST("addanswers.php")
    Call<JsonResponse> addAnswer(
            @Field("answer_text") String answer_text,
            @Field("question_id") String question_id
    );

    @FormUrlEncoded
    @POST("fetchqna.php")
    Call<AnswerResponse> fetchAnswer(
            @Field("individual_id") String individual_id
    );


    //RESULT
    @FormUrlEncoded
    @POST("addresult.php")
    Call<JsonResponse> addResult(
            @Field("result_text") String result_text,
            @Field("answer_id") String answer_id
    );

    @FormUrlEncoded
    @POST("fetchresult.php")
    Call<ResultResponse> fetchResult(
            @Field("individual_id") String individual_id
    );
}
