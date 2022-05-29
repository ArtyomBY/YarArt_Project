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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.yarart.samsung_project.MainActivity2_Admin;
import com.yarart.samsung_project.R;
import com.yarart.samsung_project.classes.Order;

import java.util.ArrayList;
import java.util.HashMap;


public class OrderListFragment extends Fragment {

    DatabaseReference mDatabase;

    ListView lvOrderList;

    MainActivity2_Admin mainActivity2_admin;

    public OrderListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_order_list, container, false);


//        adapter = new FirebaseRecyclerAdapter<HashMap<String, Order>, TaskViewHolder>(HashMap.class, R.layout.layout_orderlist_item, TaskViewHolder.class, mDatabase) {
//
//            @NonNull
//            @Override
//            public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//
//                return null;
//            }
//
//            @Override
//            protected void onBindViewHolder(@NonNull TaskViewHolder holder, int position, @NonNull HashMap<String, Order> model) {
//                holder.orderNum.setText(model.get("RKZDH").getOrder_number());
//                holder.price.setText(Integer.toString((int) model.get("RKZDH").paidBasket.getTotal_price_basket()));
//                holder.position.setText("1");
//            }
//        };
//        recyclerView.setAdapter(adapter);

        mainActivity2_admin = (MainActivity2_Admin) requireActivity();

        lvOrderList = v.findViewById(R.id.productsFromOrderList);

        Order[] orderArray = MainActivity2_Admin.orders.toArray(new Order[MainActivity2_Admin.orders.size()]);


//        FirebaseListOptions<HashMap<String, Order>> options = new FirebaseListOptions.Builder<HashMap<String, Order>>()
//                .setQuery(mDatabase, HashMap.class)
//                .setLayout(R.layout.layout_orderlist_item)
//                .build();
//        mAdapter = new FirebaseListAdapter<HashMap<String, Order>>(options) {
//            @Override
//            protected void populateView(@NonNull View v, @NonNull HashMap<String, Order> model, int position) {
//                //                OrderArrayAdapter adapter = new OrderArrayAdapter(getContext(), );
//                tv = v.findViewById(R.id.etNumberOfOrder);
//                tv.setText(model.get("JQQCF").getOrder_number());
//            }
//        };



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




    private static class TaskViewHolder extends RecyclerView.ViewHolder{

        TextView orderNum;
        TextView price;
        TextView position;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            orderNum = (TextView) itemView.findViewById(R.id.etNumberOfOrder);
            price = (TextView) itemView.findViewById(R.id.etPriceOfOrder);
            position = (TextView) itemView.findViewById(R.id.etPositionOfOrderInList);
        }

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