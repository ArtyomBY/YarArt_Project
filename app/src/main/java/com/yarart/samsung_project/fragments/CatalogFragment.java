package com.yarart.samsung_project.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.yarart.samsung_project.MainActivity;
import com.yarart.samsung_project.R;
import com.yarart.samsung_project.classes.Product;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class CatalogFragment extends Fragment {

    ListView lv_catalog;
    Button goToBasketButton;
    BottomNavigationView bottomNavigationView;

    public CatalogFragment() {
        // Required empty public constructor
    }

    View v;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_catalog, container, false);

        lv_catalog = v.findViewById(R.id.product_list);
        ProductCatalogAdapter adapter = new ProductCatalogAdapter(v.getContext(), makeProduct());
        lv_catalog.setAdapter(adapter);

        return v;
    }

    public Product[] makeProduct() {
        Product[] arr = new Product[3];

        String[] dishes = {"Устрица", "Питца", "Пирог с картошкой"};
        int[] prices = {10, 35, 16};

        for (int i = 0; i < arr.length; i++) {
            Product product = new Product();
            product.setDish(dishes[i]);
            product.setPrice(prices[i]);
            arr[i] = product;
        }

//        Catalog catalog = new Catalog(arr);
//        return (List<Product>) catalog;
        return arr;
    }

    public void put_in_basket(View view) {
        //Basket basket = new Basket()
        MainActivity mainActivity = (MainActivity) requireActivity();
        mainActivity.replaceFragment(new BasketFragment());
//        Intent i = new Intent(view.getContext(), BasketActivity.class);
//        i.putExtra("dishPrice", a);
//        startActivity(i);
        mainActivity.bottomNavigationView.getMenu().findItem(R.id.basket_menu).setChecked(true);
        Toast.makeText(getContext(), "Товар добавлен в корзину!", Toast.LENGTH_SHORT).show();
    }

    public void look_at_product(View view) {
        TextView tv = v.findViewById(R.id.textView);
        String str = tv.getText().toString();
        MainActivity mainActivity = (MainActivity) requireActivity();
        mainActivity.replaceFragment(new ProductFragment());
//        Intent i = new Intent(MainActivity_ProductCatalog.this, ProductActivity.class);
//        i.putExtra("nd", str);
//        startActivity(i);
    }

    public void goToBasket(View view) {
        //new Basket()
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
            if (product.getDish() == "Устрица")
                ((ImageView) convertView.findViewById(R.id.imageView)).setImageResource(R.drawable.ustrica);
            if (product.getDish() == "Питца")
                ((ImageView) convertView.findViewById(R.id.imageView)).setImageResource(R.drawable.pizza);
            if (product.getDish() == "Пирог с картошкой")
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