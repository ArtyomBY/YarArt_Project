package com.yarart.samsung_project.classes;

public class Wallet {

//    private static int inc = 0;
    private String id;
    private double balance;


    public Wallet(String id){
        this.id = id;
//        this.id = inc++;
        balance = 0;
    }

    public String getId() {
        return id;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance += balance;
    }
}