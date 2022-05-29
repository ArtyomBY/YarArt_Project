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


    ArrayList<Order> orders;
    ListView lvOrderList;

    MainActivity2_Admin mainActivity2_admin;

    public OrderListFragment(ArrayList<Order> orders) {
        // Required empty public constructor
        this.orders = orders;
        mDatabase = FirebaseDatabase.getInstance().getReference("Orders");
//        mDatabase.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DataSnapshot> task) {
//                if (task.isSuccessful()) {
//                    DataSnapshot document = task.getResult();
//                    if (document.exists()) {
//                        orders = document.getValue(ArrayList.class);
//                        Toast.makeText(mainActivity2_admin, "Список заказов обновлен", Toast.LENGTH_SHORT).show();
//                        Log.d("GET_ORDERS", "onComplete: успешно");
//                    } else {
//                        Log.d("GET_ORDERS", "onComplete: успешно");
//                    }
//                } else {
//                    Log.d("GET_ORDERS", "get failed with ", task.getException());
//                }
//
//            }
//        });
        mDatabase.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    DataSnapshot document = task.getResult();
                    if (document.exists()) {
                        // и затем вот здесь распаковать данные
                         HashMap<String, Order> orders_list_fb = document.getValue(new GenericTypeIndicator<HashMap<String, Order>>() {
                         });

                        // Но поскольку ID юзера не совпадает, приходится вытаскивать целый список данных в виде хэшмапа
                        // у getValue сложный параметр, он используется для конвертации к Generic-типу (это который в <> указан)
                        // Везде, где нужно вытащить целый список данных, можно так писать.
//                                                HashMap<String, UserProfile> users = document.getValue(new GenericTypeIndicator<HashMap<String, UserProfile>>() {
//                                                });

                        for (HashMap.Entry<String, Order> pair: orders_list_fb.entrySet())
                        {
                            System.out.println(pair.getKey() + " " + pair.getValue());
                            orders.add(pair.getValue()); //nenenenen
                        }

                        // TODO
                        // Здесь дальше уже нужно перебрать через цикл HashMap и найти пользователя с нужным email-ом
                        // (по сути из-за поломки айдишников это единственный вариант на данный момент)
                        // и выбрать юзера с нужным email-ом и уже с ним какие-то действия делать, какие вы хотели
                        Log.d("GET_ORDERS", "onComplete: успешно");
                    } else {
                        Log.d("GET_ORDERS", "onComplete: безуспешно");
                    }
                } else {
                    Log.d("GET_ORDERS", "get failed with ", task.getException());
                }
            }
        });
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

        Order[] orderArray = orders.toArray(new Order[orders.size()]);


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



        OrderListFragment.OrderArrayAdapter adapter = new OrderListFragment.OrderArrayAdapter(v.getContext(), orders.toArray(orderArray));
        lvOrderList.setAdapter(adapter);

        lvOrderList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                mainActivity2_admin.replaceFragment(new OrderFragment(orders.get(adapterView.getPositionForView(view))));
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