package com.yarart.samsung_project.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yarart.samsung_project.R;

public class ProductFragment extends Fragment {

    TextView tvNameDish;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_product, container, false);

        tvNameDish = v.findViewById(R.id.tvNameDish);
        // Inflate the layout for this fragment
        return v;
    }
}