package com.yarart.samsung_project.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.yarart.samsung_project.R;

public class RefillFragment extends Fragment {

    EditText etBuyerId, etSumOfCashToRefill;
    Button buttonRefill;
    String buyerId;
    int sumOfCashToRefill;

    public RefillFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_refill, container, false);

        etBuyerId = v.findViewById(R.id.etBuyerId);
        etSumOfCashToRefill = v.findViewById(R.id.etSumOfCashToRefill);
        buttonRefill = v.findViewById(R.id.buttonRefill);
        buttonRefill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                refillTheWalletOfBuyer(view);
            }
        });

        return v;
    }

    public void refillTheWalletOfBuyer(View view) {
        buyerId = etBuyerId.getText().toString();
        sumOfCashToRefill = Integer.parseInt(etSumOfCashToRefill.getText().toString());
    }
}