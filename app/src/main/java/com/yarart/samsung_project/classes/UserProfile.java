package com.yarart.samsung_project.classes;

import android.widget.ImageView;

import java.io.Serializable;

public class UserProfile implements Serializable {

    private String id;
    private String firstName;
    private String secondName;
    private String email;
    private String user_status;
    private double wallet;

    public UserProfile() {

    }

    public UserProfile(String id, String firstName, String secondName, String email, String user_status) {

        this.id = id;
        this.firstName = firstName;
        this.secondName = secondName;
        this.email = email;
        this.user_status = user_status;
        this.wallet = 0.0;
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

    public double getWallet() {
        return wallet;
    }

    public void setWallet(double price) {
        this.wallet += price;
    }

    @Override
    public String toString() {
        return "UserProfile{" +
                "id='" + id + '\'' +
                ", firstName='" + firstName + '\'' +
                ", secondName='" + secondName + '\'' +
                ", email='" + email + '\'' +
                ", user_status='" + user_status + '\'' +
                '}';
    }


}
