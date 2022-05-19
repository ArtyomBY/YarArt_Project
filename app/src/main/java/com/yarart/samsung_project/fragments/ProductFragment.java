package com.yarart.samsung_project.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.yarart.samsung_project.MainActivity;
import com.yarart.samsung_project.R;
import com.yarart.samsung_project.classes.Product;

public class ProductFragment extends Fragment {

    static TextView tvNameDish;
    static ImageView productImage;
    static TextView tvProductPrice;
    static TextView tvProductDescription;
    Button btn_payment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_product, container, false);

        tvNameDish = v.findViewById(R.id.tvNameDish);
        productImage = v.findViewById(R.id.productImage);
        tvProductDescription = v.findViewById(R.id.productDescription);
        btn_payment = v.findViewById(R.id.btn_payment);
        btn_payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                put_in_basket(view);
            }
        });
        // Inflate the layout for this fragment
        return v;
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

    public static void getInfoAboutProduct(Product product) {
        tvNameDish.setText(product.getDish());
        tvProductPrice.setText(product.getPrice());
        tvProductDescription.setText(product.getDescription());
        productImage.setImageResource(product.getDishResource());
    }
}