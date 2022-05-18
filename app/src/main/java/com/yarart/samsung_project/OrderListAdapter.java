package com.yarart.samsung_project;

import android.content.Context;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;

import com.yarart.samsung_project.classes.Order;

import java.util.ArrayList;
import java.util.List;

public class OrderListAdapter extends ArrayAdapter<Order> {

    public OrderListAdapter(@NonNull Context context, int resource,  @NonNull ArrayList<Order> objects) {
        super(context, resource, objects);
    }

}
