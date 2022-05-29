package com.yarart.samsung_project.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.yarart.samsung_project.MainActivity;
import com.yarart.samsung_project.MainActivity2_Admin;
import com.yarart.samsung_project.R;
import com.yarart.samsung_project.classes.Product;

import java.util.ArrayList;


public class AdminCatalogFragment extends Fragment {

    ListView lv_catalog;
    Button deleteFromBasket, addProductToBasketButton;
    MainActivity2_Admin  mainActivity2_admin;

    public AdminCatalogFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_admin_catalog, container, false);

        mainActivity2_admin = (MainActivity2_Admin) requireActivity();
        lv_catalog = v.findViewById(R.id.product_list);
        deleteFromBasket = v.findViewById(R.id.buttonDeleteFromBasket);
        addProductToBasketButton = v.findViewById(R.id.addProductButton);
        Product[] productArray = MainActivity.products.toArray(new Product[MainActivity.products.size()]);
        ProductCatalogAdapter adapter = new ProductCatalogAdapter(v.getContext(), MainActivity.products.toArray(productArray));
        lv_catalog.setAdapter(adapter);
        addProductToBasketButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivity2_admin.replaceFragment(new AddProductFragment());
            }
        });

        lv_catalog.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                look_at_product(view, MainActivity.products.get(adapterView.getPositionForView(view)));
            }
        });

        return v;
    }



    public void look_at_product(View view, Product product) {
        TextView tv = view.findViewById(R.id.textView);
        String str = tv.getText().toString();
        MainActivity2_Admin mainActivity2_admin = (MainActivity2_Admin) requireActivity();
        mainActivity2_admin.replaceFragment(new AdminProductFragment(product));
    }






    private class ProductCatalogAdapter extends ArrayAdapter<Product> {

        public ProductCatalogAdapter(Context context, Product[] productList) {
            super(context, R.layout.layout_item_catalog, productList);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            final Product product = getItem(position);

            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.layout_item_catalog, null);
            }

            ((TextView) convertView.findViewById(R.id.textView)).setText(product.getDish());
            ((TextView) convertView.findViewById(R.id.textView2)).setText(Integer.toString(product.getPrice()));
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