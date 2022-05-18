package com.yarart.samsung_project.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.yarart.samsung_project.R;
import com.yarart.samsung_project.classes.Product;

public class BasketFragment extends Fragment {

    ListView lv_Basket;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_basket, container, false);
//        TextView tv = (TextView) v.findViewById(R.id.textView2);
//        EditText et = (EditText) v.findViewById(R.id.editTextTextPassword2);
//        tv.setText("Хуй знает что это");
//        et.setHint("Введите пароль");

        lv_Basket = v.findViewById(R.id.basketList);

        // Inflate the layout for this fragment
        return v;
    }


    public void pay_for_the_shopping_cart(View view) {

    }


    private class BasketAdapter extends ArrayAdapter {

        private LayoutInflater inflater;
        private int layout;
        private Product[] basketList;

        public BasketAdapter(Context context, int resource, Product[] basketList) {
            super(context, resource,  basketList);
            this.layout = resource;
            this.basketList = basketList;
            this.inflater = LayoutInflater.from(context);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = inflater.inflate(this.layout, parent, false);

            ImageView dishImage = view.findViewById(R.id.imageView);
            TextView dishName = view.findViewById(R.id.textView);
            TextView dishPrice = view.findViewById(R.id.textView2);

            Product product = basketList[position];

            dishImage.setImageResource(product.getDishResource());
            dishName.setText(product.getDish());
            dishPrice.setText(product.getPrice());

            return view;
        }
    }
}