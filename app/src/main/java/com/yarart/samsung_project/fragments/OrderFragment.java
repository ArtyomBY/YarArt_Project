package com.yarart.samsung_project.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.yarart.samsung_project.MainActivity;
import com.yarart.samsung_project.MainActivity2_Admin;
import com.yarart.samsung_project.R;
import com.yarart.samsung_project.classes.Order;
import com.yarart.samsung_project.classes.Product;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class OrderFragment extends Fragment {

    ArrayList<Product> productsFromOrder = new ArrayList<>();
    ListView lv_order;
    TextView tvOrderStatus, tvTotalPriceOrder, tvNumberOfOrder;
    Order order;
    Button sendNoticeToBuyerButton, deleteOrderButton;

    public OrderFragment(Order order) {
        // Required empty public constructor
        if (order.paidBasket.getBasketStatus()) {
            this.productsFromOrder = order.paidBasket.getProductsFromBasket();
            this.order = order;
            MainActivity.userOrder = order;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_order, container, false);
        sendNoticeToBuyerButton = v.findViewById(R.id.sendNoticeToBuyerButton);
        deleteOrderButton = v.findViewById(R.id.deleteOrderButton);
        if (!MainActivity2_Admin.isActivityCreated) {
            sendNoticeToBuyerButton.setVisibility(View.GONE);
            deleteOrderButton.setVisibility(View.GONE);
        }
        tvNumberOfOrder = v.findViewById(R.id.tvNumberOfOrder);
        tvOrderStatus = v.findViewById(R.id.tvOrderStatus);
        tvTotalPriceOrder = v.findViewById(R.id.tvTotalPriceOrder);
        lv_order = v.findViewById(R.id.productsFromOrderList);
        Product[] products = productsFromOrder.toArray(new Product[productsFromOrder.size()]);
        OrderFragment.ProductOrderAdapter adapter = new OrderFragment.ProductOrderAdapter(getContext(), productsFromOrder.toArray(products));
        lv_order.setAdapter(adapter);
        tvTotalPriceOrder.setText(Double.toString(order.paidBasket.getTotal_price_basket()));
        tvOrderStatus.setText(order.order_status);
        tvNumberOfOrder.setText(order.order_number);
        sendNoticeToBuyerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendNoticeToBuyer(view, order);
            }
        });
        deleteOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteOrderFromOrderList(view, order, MainActivity.orders);
            }
        });



        return v;
    }

    public void sendNoticeToBuyer(View view, Order order) {

    }

    public void deleteOrderFromOrderList(View view, Order order, ArrayList<Order> orders) {
        MainActivity.orders.remove(MainActivity.orders.indexOf(order));
        MainActivity2_Admin mainActivity2_admin = (MainActivity2_Admin) requireActivity();
        mainActivity2_admin.replaceFragment(new OrderListFragment(orders));
    }


    private class ProductOrderAdapter extends ArrayAdapter<Product> {

        public ProductOrderAdapter(Context context, Product[] productList) {
            super(context, R.layout.layout_order_item, productList);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            final Product product = getItem(position);

            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.layout_order_item, null);
            }

            ((TextView) convertView.findViewById(R.id.textView)).setText(product.getDish());
            ((TextView) convertView.findViewById(R.id.textView2)).setText(Integer.toString(product.getPrice()));
            TextView viewById = (TextView) convertView.findViewById(R.id.productNumberFromOrder);
            viewById.setText(Integer.toString(position));
            if (product.getDish().equals("Устрица"))
                ((ImageView) convertView.findViewById(R.id.imageView)).setImageResource(R.drawable.ustrica);
            if (product.getDish().equals("Питца"))
                ((ImageView) convertView.findViewById(R.id.imageView)).setImageResource(R.drawable.pizza);
            if (product.getDish().equals("Пирог с картошкой"))
                ((ImageView) convertView.findViewById(R.id.imageView)).setImageResource(R.drawable.kartoshka);

//        CheckBox ch = (CheckBox) convertView.findViewById(R.id.checkbox);
//        ch.setChecked(product.like);
//        ch.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                product.like = ((CheckBox) v).isChecked();
//            }
//        });
            return convertView;
        }
    }
}