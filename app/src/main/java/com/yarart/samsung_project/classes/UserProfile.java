package com.yarart.samsung_project.classes;

import android.widget.ImageView;

public class UserProfile {

    //ImageView profile_picture;
    private String id;
//    private String user_id;
    private static long inc = 0;
    private String firstName;
    private String secondName;
    private String email;
    private String user_status;

    public UserProfile(String id, String firstName, String secondName, String email, String user_status) {

        this.id = id;
//        inc++;
//        this.id = Long.toString(inc);
        this.firstName = firstName;
        this.secondName = secondName;
        this.email = email;
        this.user_status = user_status;
    }

    public void change_information() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUser_status() {
        return user_status;
    }

    public void setUser_status(String user_status) {
        this.user_status = user_status;
    }



}
