package com.yarart.samsung_project.classes;

public class Order {
    private String order_status;
    private String order_number;
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


    public String getOrder_number() {
        return order_number;
    }

    public String getOrder_status() {
        return order_status;
    }

    public void setOrder_status(String order_status) {
        this.order_status = order_status;
    }
}
