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

public class AddProductFragment extends Fragment {

    Product product;
    ImageView newProductImageView;
    EditText etNewProductDish, etNewProductDescription, etNewProductPrice;
    Button saveProductChangesButton;
    MainActivity2_Admin mainActivity2_admin;

    public AddProductFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        product = new Product();
        View v = inflater.inflate(R.layout.fragment_add_product, container, false);
        mainActivity2_admin = (MainActivity2_Admin)requireActivity();
        newProductImageView = v.findViewById(R.id.newProductImageViewAdd);
        etNewProductDish = v.findViewById(R.id.etNewProductDishAdd);
        etNewProductDescription = v.findViewById(R.id.etNewProductDescriptionAdd);
        etNewProductPrice = v.findViewById(R.id.etNewProductPriceAdd);
        saveProductChangesButton = v.findViewById(R.id.saveProductChangesButtonAdd);
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
            product.setDish(etNewProductDish.getText().toString());
            product.setDescription(etNewProductDescription.getText().toString());
            product.setPrice(Integer.parseInt(etNewProductPrice.getText().toString()));
            MainActivity.products.add(product);
            mainActivity2_admin.replaceFragment(new AdminProductFragment(product));
        }
        else Toast.makeText(getContext(), "Вы ввели не все данные",  Toast.LENGTH_SHORT).show();

        }

    }
