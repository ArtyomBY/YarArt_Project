package com.yarart.samsung_project.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.yarart.samsung_project.MainActivity;
import com.yarart.samsung_project.MainActivity2_Admin;
import com.yarart.samsung_project.R;
import com.yarart.samsung_project.classes.Product;

public class AdminProductFragment extends Fragment {

    TextView tvNameDish;
    ImageView productImage;
    TextView tvProductPrice;
    TextView tvProductDescription;
    Button buttonEditTheProduct, buttonDeleteFromCatalog ;
    MainActivity2_Admin mainActivity2_admin;
    Product product;

    public AdminProductFragment(Product product) {
        this.product = product;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_admin_product, container, false);

        tvNameDish = v.findViewById(R.id.tvNameDish);
        mainActivity2_admin = (MainActivity2_Admin) requireActivity();
        productImage = v.findViewById(R.id.productImage);
        tvProductPrice = v.findViewById(R.id.productPrice);
        tvProductDescription = v.findViewById(R.id.productDescription);
        buttonEditTheProduct = v.findViewById(R.id.buttonEditTheProduct);
        buttonDeleteFromCatalog = v.findViewById(R.id.buttonDeleteFromCatalog);
        tvNameDish.setText(product.getDish());
        String productPrice = Integer.toString(product.getPrice());
        tvProductPrice.setText(productPrice);
        tvProductDescription.setText(product.getDescription());
        productImage.setImageResource(product.getDishResource());
        buttonDeleteFromCatalog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteFromCatalog(view, product);
            }
        });
        buttonEditTheProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editTheProduct(view, product);
            }
        });

        return v;
    }

    public void deleteFromCatalog(View view, Product product) {
        MainActivity.products.remove(MainActivity.products.indexOf(product));
        mainActivity2_admin.replaceFragment(new AdminCatalogFragment());
    }
    public void editTheProduct(View view, Product product) {
        mainActivity2_admin.replaceFragment(new EditProductFragment(product));
    }

}