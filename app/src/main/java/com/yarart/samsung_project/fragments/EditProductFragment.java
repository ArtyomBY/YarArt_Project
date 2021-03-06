package com.yarart.samsung_project.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.yarart.samsung_project.MainActivity;
import com.yarart.samsung_project.MainActivity2_Admin;
import com.yarart.samsung_project.R;
import com.yarart.samsung_project.classes.Product;

public class EditProductFragment extends Fragment {

    Product product;
    ImageView newProductImageView;
    EditText etNewProductDish, etNewProductDescription, etNewProductPrice;
    Button saveProductChangesButton;
    MainActivity2_Admin mainActivity2_admin;

    public EditProductFragment(Product product) {
        this.product = product;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_edit_product, container, false);
        mainActivity2_admin = (MainActivity2_Admin)requireActivity();
        newProductImageView = v.findViewById(R.id.newProductImageView);
        etNewProductDish = v.findViewById(R.id.etNewProductDish);
        etNewProductDescription = v.findViewById(R.id.etNewProductDescription);
        etNewProductPrice = v.findViewById(R.id.etNewProductPrice);
        saveProductChangesButton = v.findViewById(R.id.saveProductChangesButton);
        saveProductChangesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeProduct(view, product);
            }
        });
        return v;
    }

    public void changeProduct(View view, Product product) {
        if ((etNewProductDish.getText().toString().equals("")==false)&&(etNewProductDescription.getText().toString().equals("")==false)
                &&(etNewProductPrice.getText().toString().equals("")==false))
        {
            MainActivity.products.get(MainActivity.products.indexOf(product)).setDish(etNewProductDish.getText().toString());
            MainActivity.products.get(MainActivity.products.indexOf(product)).setDescription(etNewProductDescription.getText().toString());
            MainActivity.products.get(MainActivity.products.indexOf(product)).setPrice(Integer.parseInt(etNewProductPrice.getText().toString()));
            MainActivity.products.get(MainActivity.products.indexOf(product)).setDishResource(product.getDishResource());
            mainActivity2_admin.replaceFragment(new AdminProductFragment(product));
        }
        else Toast.makeText(getContext(), "???? ?????????? ???? ?????? ????????????",  Toast.LENGTH_SHORT).show();
    }

}