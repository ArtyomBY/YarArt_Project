package com.yarart.samsung_project.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.yarart.samsung_project.LoginActivity;
import com.yarart.samsung_project.MainActivity;
import com.yarart.samsung_project.MainActivity2_Admin;
import com.yarart.samsung_project.R;
import com.yarart.samsung_project.classes.UserProfile;

import java.util.HashMap;
import java.util.Map;

public class RefillFragment extends Fragment {

    EditText etBuyerId, etSumOfCashToRefill;
    Button buttonRefill;
    String buyerId;
    int sumOfCashToRefill;

    DatabaseReference mDatabase;
    UserProfile cUser;

    public RefillFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_refill, container, false);


        etBuyerId = v.findViewById(R.id.etBuyerId);

        mDatabase = FirebaseDatabase.getInstance().getReference("Users");

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

        mDatabase.child(buyerId).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    DataSnapshot document = task.getResult();
                    if (document.exists()) {
                        cUser = document.getValue(UserProfile.class);
                        if (cUser != null) {
                            cUser.setWallet(sumOfCashToRefill);
                            Map<String, Object> updateMap = new HashMap<>();
                            updateMap.put("/" + buyerId, cUser);
                            mDatabase.updateChildren(updateMap);
                            Toast.makeText(getContext(), "Операция прошла успешно", Toast.LENGTH_SHORT).show();
                        } else
                            Toast.makeText(getContext(), "Пользователя не существует", Toast.LENGTH_SHORT).show();
                        Log.d("GET_USER", "DocumentSnapshot data: TODO find user in HashMap");
                    } else {
                        Log.d("GET_USER", "No such document");
                        Toast.makeText(getContext(), "Пользователя не существует", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.d("GET_USER", "get failed with ", task.getException());
                }
            }
        });
    }
}