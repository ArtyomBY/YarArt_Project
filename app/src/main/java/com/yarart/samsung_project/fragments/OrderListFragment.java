package com.yarart.samsung_project.fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.yarart.samsung_project.MainActivity2_Admin;
import com.yarart.samsung_project.R;
import com.yarart.samsung_project.classes.Order;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class OrderListFragment extends Fragment {

    DatabaseReference mDatabase;

    ListView lvOrderList;

    MainActivity2_Admin mainActivity2_admin;

    public OrderListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

//        mDatabase = FirebaseDatabase.getInstance().getReference("Orders");
//        mDatabase.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                MainActivity2_Admin.orders.clear();
//                HashMap<String, Order> orders_list_fb = snapshot.getValue(new GenericTypeIndicator<HashMap<String, Order>>() {});
//                for (HashMap.Entry<String, Order> pair: orders_list_fb.entrySet())
//                {
//                    System.out.println(pair.getKey() + " " + pair.getValue());
//                    MainActivity2_Admin.orders.add(pair.getValue());
//                    Map<String, Object> updateMap = new HashMap<>();
//                    updateMap.put("/" + pair.getKey(), pair.getValue());
//                    mDatabase.updateChildren(updateMap);
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });


        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_order_list, container, false);

        mainActivity2_admin = (MainActivity2_Admin) requireActivity();

        lvOrderList = v.findViewById(R.id.lvOrderList);

        Order[] orderArray = MainActivity2_Admin.orders.toArray(new Order[MainActivity2_Admin.orders.size()]);




        OrderListFragment.OrderArrayAdapter adapter = new OrderListFragment.OrderArrayAdapter(v.getContext(), MainActivity2_Admin.orders.toArray(orderArray));
        lvOrderList.setAdapter(adapter);

        lvOrderList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                mainActivity2_admin.replaceFragment(new OrderFragment(MainActivity2_Admin.orders.get(adapterView.getPositionForView(view))));
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