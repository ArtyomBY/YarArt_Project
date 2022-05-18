package com.yarart.samsung_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.yarart.samsung_project.classes.Basket;
import com.yarart.samsung_project.classes.Product;

public class ProductActivity extends AppCompatActivity {

    TextView tvNameDish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        tvNameDish = findViewById(R.id.tvNameDish);
        String nameDish = getIntent().getStringExtra("nd");
        tvNameDish.setText(nameDish);

    }

}