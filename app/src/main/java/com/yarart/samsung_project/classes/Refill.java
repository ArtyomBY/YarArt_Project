package com.yarart.samsung_project.classes;

public class Refill {
    public static void refill(Wallet[] arr, String id, double price){
        for (Wallet i: arr){
            if (i.getId().equals(id)){
                i.setBalance(price);
                System.out.println("Баланс кошелька: " + i.getBalance() + " руб.");
            };
        }
    }
}