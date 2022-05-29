package com.yarart.samsung_project.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.yarart.samsung_project.MainActivity2_Admin;
import com.yarart.samsung_project.R;
import com.yarart.samsung_project.classes.Order;

import java.util.ArrayList;

public class OrderListFragmentTwo extends Fragment {

    ArrayList<Order> orders;
    ListView lvOrderList;

    MainActivity2_Admin mainActivity2_admin;

    public OrderListFragmentTwo(ArrayList<Order> orders) {
        // Required empty public constructor
        this.orders = orders;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_order_list_two, container, false);

        mainActivity2_admin = (MainActivity2_Admin) requireActivity();

        lvOrderList = v.findViewById(R.id.lvOrderListTwo);

        Order[] orderArray = orders.toArray(new Order[orders.size()]);
        OrderListFragmentTwo.OrderArrayAdapter adapter = new OrderListFragmentTwo.OrderArrayAdapter(v.getContext(), orders.toArray(orderArray));
        lvOrderList.setAdapter(adapter);

        lvOrderList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                mainActivity2_admin.replaceFragment(new OrderFragment(orders.get(adapterView.getPositionForView(view))));
            }
        });

        return v;
    }

    private class OrderArrayAdapter extends ArrayAdapter<Order> {

        public OrderArrayAdapter(Context context, Order[] orderList) {
            super(context, R.layout.layout_orderlist_item, orderList);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            final Order order = getItem(position);

            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.layout_orderlist_item, null);
            }

            TextView numberOfOrder = convertView.findViewById(R.id.etNumberOfOrder);
            TextView priceOfOrder = convertView.findViewById(R.id.etPriceOfOrder);
            TextView positionOfOrderInList = convertView.findViewById(R.id.etPositionOfOrderInList);
            TextView statusOfOrder = convertView.findViewById(R.id.tvStatusOfOrder);
            //
            numberOfOrder.setText(order.getOrder_number());
            priceOfOrder.setText(Double.toString(order.paidBasket.getTotal_price_basket()));
            positionOfOrderInList.setText(Integer.toString(position));
            statusOfOrder.setText(order.getOrder_status());

            return convertView;
        }
    }

}