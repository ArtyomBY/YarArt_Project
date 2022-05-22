package com.yarart.samsung_project.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.yarart.samsung_project.MainActivity;
import com.yarart.samsung_project.R;
import com.yarart.samsung_project.classes.Basket;
import com.yarart.samsung_project.classes.Product;

import java.util.ArrayList;

public class BasketFragment extends Fragment {

    ListView lv_Basket;
    Product product;
    Button deleteFromBasket;
    static ArrayList<Product> basketList = new ArrayList<>();
    TextView tvTotalPrice;

    public BasketFragment(Product product) {
        this.product = product;
    }
    public BasketFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_basket, container, false);
//        TextView tv = (TextView) v.findViewById(R.id.textView2);
//        EditText et = (EditText) v.findViewById(R.id.editTextTextPassword2);
//        tv.setText("Хуй знает что это");
//        et.setHint("Введите пароль");
        lv_Basket = v.findViewById(R.id.basketList);
        tvTotalPrice = v.findViewById(R.id.tvTotalPrice);
        deleteFromBasket = v.findViewById(R.id.buttonDeleteFromBasket);
        Product[] products = basketList.toArray(new Product[basketList.size()]);
        ProductBasketAdapter adapter = new ProductBasketAdapter(getContext(), basketList.toArray(products));
        lv_Basket.setAdapter(adapter);
        lv_Basket.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                look_at_product(view, basketList.get(adapterView.getPositionForView(view)));
                adapter.notifyDataSetChanged();

                String total_price_str = Integer.toString(MainActivity.total_price);
                tvTotalPrice.setText(total_price_str);

            }
        });



        // Inflate the layout for this fragment
        return v;
    }
    public void look_at_product(View view, Product product) {
        TextView tv = view.findViewById(R.id.textView);
        String str = tv.getText().toString();
        MainActivity mainActivity = (MainActivity) requireActivity();
//        deleteFromBasket.setVisibility(View.VISIBLE);
        mainActivity.replaceFragment(new ProductFragment(product));
//        Intent i = new Intent(MainActivity_ProductCatalog.this, ProductActivity.class);
//        i.putExtra("nd", str);
//        startActivity(i);
    }


    public void pay_for_the_shopping_cart(View view) {

    }



    private class ProductBasketAdapter extends ArrayAdapter<Product> {

        public ProductBasketAdapter(Context context, Product[] productList) {
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