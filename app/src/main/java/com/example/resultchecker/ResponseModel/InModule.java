package com.example.resultchecker.ResponseModel;

public class InModule {
    String individual_id, module_code, student_id, institute_id, module_name;

    public InModule(String individual_id, String module_code, String student_id, String institute_id, String module_name) {
        this.individual_id = individual_id;
        this.module_code = module_code;
        this.student_id = student_id;
        this.institute_id = institute_id;
        this.module_name = module_name;
    }

    public String getIndividual_id() {
        return individual_id;
    }

    public void setIndividual_id(String individual_id) {
        this.individual_id = individual_id;
    }

    public String getModule_code() {
        return module_code;
    }

    public void setModule_code(String module_code) {
        this.module_code = module_code;
    }

    public String getStudent_id() {
        return student_id;
    }

    public void setStudent_id(String student_id) {
        this.student_id = student_id;
    }

    public String getInstitute_id() {
        return institute_id;
    }

    public void setInstitute_id(String institute_id) {
        this.institute_id = institute_id;
    }

    public String getModule_name() {
        return module_name;
    }

    public void setModule_name(String module_name) {
        this.module_name = module_name;
    }
}
