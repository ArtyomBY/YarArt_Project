package com.yarart.samsung_project.classes;

public class Admin extends UserProfile{

    public Admin(String id, String firstName, String secondName, String email, String user_status) {
        super(id, firstName, secondName, email, user_status);
        user_status = "Администратор";
    }
}
