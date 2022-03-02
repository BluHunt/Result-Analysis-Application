package com.example.ra.Utils;

public class teacher_subject {
    String status,sub,code,name;

    public teacher_subject(String status, String sub, String code, String name) {
        this.status = status;
        this.sub = sub;
        this.code = code;
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSub() {
        return sub;
    }

    public void setSub(String sub) {
        this.sub = sub;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public teacher_subject() {
    }
}
