package com.yarart.samsung_project.classes;

public class Order {
    public String order_status;
    public String order_number;
    public Basket paidBasket;
    // public Buyer buyer_information;
    // public int buyer_wallet_id = Wallet.id;

    public Order(String ord_stat, String ord_numb, Basket paidBasket) {
        this.order_status = ord_stat;
        this.order_number = ord_numb;
        this.paidBasket = paidBasket;
    }

    public Order() {

    }

    public String change_order_status(boolean a){
        if (a) return "Не выдан";
        else return "Выдан";
    }
}
