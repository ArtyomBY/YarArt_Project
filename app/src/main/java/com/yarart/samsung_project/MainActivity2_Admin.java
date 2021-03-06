package com.yarart.samsung_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.yarart.samsung_project.classes.Basket;
import com.yarart.samsung_project.classes.Order;
import com.yarart.samsung_project.classes.Product;
import com.yarart.samsung_project.classes.UserProfile;
import com.yarart.samsung_project.fragments.AdminCatalogFragment;
import com.yarart.samsung_project.fragments.OrderListFragment;
import com.yarart.samsung_project.fragments.ProfileFragment;
import com.yarart.samsung_project.fragments.RefillFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity2_Admin extends AppCompatActivity {

    public BottomNavigationView bottomNavigationView;
    public static boolean isActivityCreated = false;
    public static ArrayList<Order> orders = new ArrayList<>();

    public DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity2_admin);
        isActivityCreated = true;
        init();


    }


    public void init() {

        mDatabase = FirebaseDatabase.getInstance().getReference("Orders");
        mDatabase.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    DataSnapshot document = task.getResult();
                    if (document.exists()) {
                        HashMap<String, Order> orders_list_fb = document.getValue(new GenericTypeIndicator<HashMap<String, Order>>() {});
                        for (HashMap.Entry<String, Order> pair: orders_list_fb.entrySet())
                        {
                            System.out.println(pair.getKey() + " " + pair.getValue());
                            orders.add(pair.getValue());
                        }
                        Log.d("GET_ORDERS", "onComplete: ??????????????");
                    } else {
                        Log.d("GET_ORDERS", "onComplete: ????????????????????");
                    }
                } else {
                    Log.d("GET_ORDERS", "get failed with ", task.getException());
                }
            }
        });

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                orders.clear();
                HashMap<String, Order> orders_list_fb = snapshot.getValue(new GenericTypeIndicator<HashMap<String, Order>>() {});
                for (HashMap.Entry<String, Order> pair: orders_list_fb.entrySet())
                {
                    System.out.println(pair.getKey() + " " + pair.getValue());
                    orders.add(pair.getValue());
                    Map<String, Object> updateMap = new HashMap<>();
                    updateMap.put("/" + pair.getKey(), pair.getValue());
                    mDatabase.updateChildren(updateMap);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        if (MainActivity.products.size()==0) {
            MainActivity.products.add(new Product("??????????????", 10, "?????????????? ?? ??????????", true, R.drawable.ustrica));
            MainActivity.products.add(new Product("??????????", 35, "???????????? ??????????????????", true, R.drawable.pizza));
            MainActivity.products.add(new Product("?????????? ?? ??????????????????", 16, "?????????????? ?? ??????????????????", true, R.drawable.kartoshka));
        }
        if (MainActivity.orders.size()==0) {
            MainActivity.orders.add(new Order("???? ??????????", "TEST0", new Basket(MainActivity.products, 61, true)));
            MainActivity.orders.add(new Order("???? ??????????", "TEST1", new Basket(MainActivity.products, 54, true)));
            MainActivity.orders.add(new Order("???? ??????????", "TEST2", new Basket(MainActivity.products, 9999, true)));
        }

        Bundle args = getIntent().getExtras();
        UserProfile user = (UserProfile) args.get("User");

        bottomNavigationView = findViewById(R.id.bottomNavigationViewAdmin);
        bottomNavigationView.setSelectedItemId(R.id.refill_menu);
        replaceFragment(new RefillFragment());

        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){

                case R.id.refill_menu:
                    replaceFragment(new RefillFragment());
                    bottomNavigationView.getMenu().findItem(R.id.refill_menu).setChecked(true);
                    break;

                case R.id.order_list_menu:
                    replaceFragment(new OrderListFragment());
                    bottomNavigationView.getMenu().findItem(R.id.order_list_menu).setChecked(true);
                    break;

                case R.id.profile_menu:
                    replaceFragment(new ProfileFragment(user)); //??????
                    bottomNavigationView.getMenu().findItem(R.id.profile_menu).setChecked(true);
                    break;

                case R.id.catalog_menu:
                    replaceFragment(new AdminCatalogFragment()); //??????
                    bottomNavigationView.getMenu().findItem(R.id.catalog_menu).setChecked(true);
                    break;

            }


            return true;
        });
    }



    public void replaceFragment(Fragment fragment){

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_FrameLayout_Admin, fragment);
        fragmentTransaction.commit();

    }
}