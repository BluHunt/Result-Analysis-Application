package com.example.ra.Utils;

public class stu {
    private String RollNo,Name,EnrollmentNo;

    public stu(String rollNo, String name, String enrollmentNo) {
        RollNo = rollNo;
        Name = name;
        EnrollmentNo = enrollmentNo;
    }

    public String getRollNo() {
        return RollNo;
    }

    public void setRollNo(String rollNo) {
        RollNo = rollNo;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getEnrollmentNo() {
        return EnrollmentNo;
    }

    public void setEnrollmentNo(String enrollmentNo) {
        EnrollmentNo = enrollmentNo;
    }

    public stu() {
    }
}
