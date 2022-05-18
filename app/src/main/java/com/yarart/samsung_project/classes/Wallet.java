package com.yarart.samsung_project.classes;

public class Wallet {

    private static int inc = 0;
    private int id;
    private double balance;


    public Wallet(){
        this.id = inc++;
        balance = 0;
    }

    public int getId() {
        return id;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance += balance;
    }
}