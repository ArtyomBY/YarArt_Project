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

    TextView tvNameDish;
    ImageView productImage;
    TextView tvProductPrice;
    TextView tvProductDescription;
    Button buttonPutInTheBasket, buttonDeleteFromBasket ;
    MainActivity mainActivity;
    Product product;

    public ProductFragment(Product product) {
        this.product = product;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_product, container, false);

        tvNameDish = v.findViewById(R.id.tvNameDish);
        mainActivity = (MainActivity) requireActivity();
        productImage = v.findViewById(R.id.productImage);
        tvProductPrice = v.findViewById(R.id.productPrice);
        tvProductDescription = v.findViewById(R.id.productDescription);
        buttonDeleteFromBasket = v.findViewById(R.id.buttonDeleteFromBasket);
        if (BasketFragment.basketList.size()==0) {buttonDeleteFromBasket.setVisibility(View.GONE);}
        buttonDeleteFromBasket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                delete_from_basket(view, product);
            }
        });


        tvNameDish.setText(product.getDish());
        String productPrice = Integer.toString(product.getPrice());
        tvProductPrice.setText(productPrice);
        tvProductDescription.setText(product.getDescription());
        productImage.setImageResource(product.getDishResource());

        v.findViewById(R.id.buttonPutInTheBasket).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                put_in_basket(view);
            }
        });
        return v;
    }

    public void put_in_basket(View view) {
        MainActivity.total_price += product.getPrice();
        BasketFragment.basketList.add(product);
        mainActivity.replaceFragment(new BasketFragment());
        mainActivity.bottomNavigationView.getMenu().findItem(R.id.basket_menu).setChecked(true);
        Toast.makeText(getContext(), "Товар добавлен в корзину!", Toast.LENGTH_SHORT).show();
    }

    public void delete_from_basket(View view, Product product) {

            if (BasketFragment.basketList.get(MainActivity.position).getDish().equals(product.getDish())) {

                MainActivity.total_price -= product.getPrice();
                BasketFragment.basketList.remove(BasketFragment.basketList.remove(BasketFragment.basketList.get(MainActivity.position)));
                mainActivity.replaceFragment(new BasketFragment());
            }
            else Toast.makeText(getContext(), "Данного продукта нет в корзине", Toast.LENGTH_SHORT).show();
    }

}