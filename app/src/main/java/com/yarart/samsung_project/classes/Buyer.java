package com.yarart.samsung_project.classes;

import android.widget.ImageView;

public class Buyer extends UserProfile{
    int grade;
    private String school,region;
    private static int inc = 0;

    public Buyer(String id, String firstName, String secondName, String email, String user_status, int grade, String school, String region) {
        super(id, firstName, secondName, email, user_status);
        this.grade = grade;
        this.school = school;
        this.region = region;
        user_status = "Покупатель";

//        this.id = inc++;
//
//        this.fullName = fullName;
//        this.grade = grade;
//        this.school = school;
//        this.region = region;
//        //this.profile_picture = imageView;
//        user_status = "Покупатель";

    }
}
