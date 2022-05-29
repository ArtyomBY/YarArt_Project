package com.yarart.samsung_project.classes;

import java.util.ArrayList;
import java.util.List;

public class Basket {
    ArrayList<Product> productList_forPayment;
    private double total_price_basket;
    private boolean basket_status;

    public Basket(ArrayList<Product> basketList, double total_price_basket, boolean basket_status){
        this.productList_forPayment = basketList;
        this.total_price_basket = total_price_basket;
        this.basket_status = basket_status;
    }
    public Basket(){

    }

    public void addToProductList_forPayment(Product product) {
        productList_forPayment.add(product);
    };


    public void deleteProduct_fromBasket(Product product){ // int id

       productList_forPayment.remove(productList_forPayment.indexOf(product));

    }

    public double getTotal_price_basket() {
        return total_price_basket;
    }

    public void setTotal_price_basket(double total_price_basket) {
        this.total_price_basket = total_price_basket;
    }

    public boolean getBasketStatus() {
        return basket_status;
    }

    public void setBasket_status(boolean basket_status) {
        this.basket_status = basket_status;
    }
    public ArrayList<Product> getProductsFromBasket() {
        return productList_forPayment;
    }
}
