package com.yarart.samsung_project.classes;

public class Order {
    public String order_status;
    public int order_number;
    public Basket paidBasket;
    // public Buyer buyer_information;
    // public int buyer_wallet_id = Wallet.id;

    public Order(String ord_stat, int ord_numb, Basket paidBasket) {
        this.order_status = ord_stat;
        this.order_number = ord_numb;
        this.paidBasket = paidBasket;
    }

    public boolean change_order_status(int a){
        if (a == 0) return false;
        if (a == 1) return true;
        return false;
    }
}
