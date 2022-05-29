package com.yarart.samsung_project;

import static com.yarart.samsung_project.MainActivity.orders;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.yarart.samsung_project.classes.Basket;
import com.yarart.samsung_project.classes.Buyer;
import com.yarart.samsung_project.classes.Order;
import com.yarart.samsung_project.classes.Product;
import com.yarart.samsung_project.classes.UserProfile;
import com.yarart.samsung_project.fragments.AdminCatalogFragment;
import com.yarart.samsung_project.fragments.BasketFragment;
import com.yarart.samsung_project.fragments.CatalogFragment;
import com.yarart.samsung_project.fragments.OrderListFragment;
import com.yarart.samsung_project.fragments.ProfileFragment;
import com.yarart.samsung_project.fragments.RefillFragment;

import java.util.HashMap;
import java.util.Map;

public class MainActivity2_Admin extends AppCompatActivity {

    public BottomNavigationView bottomNavigationView;
    public static boolean isActivityCreated = false;

    public DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity2_admin);
        isActivityCreated = true;
        init();



    }


    public void init() {
        //Buyer buyer = new Buyer("Быков Артем Ильич", 10, "МБОУ сош №35", "г. Иваново, Ивановская обл.");
        if (MainActivity.products.size()==0) {
            MainActivity.products.add(new Product("Устрица", 10, "Пирожок с маком", true, R.drawable.ustrica));
            MainActivity.products.add(new Product("Питца", 35, "Шедевр кулинарии", true, R.drawable.pizza));
            MainActivity.products.add(new Product("Пирог с картошкой", 16, "Пирожок с картошкой", true, R.drawable.kartoshka));
        }
        if (orders.size()==0) {
            orders.add(new Order("Не выдан", "TEST0", new Basket(MainActivity.products, 61, true)));
            orders.add(new Order("Не выдан", "TEST1", new Basket(MainActivity.products, 54, true)));
            orders.add(new Order("Не выдан", "TEST2", new Basket(MainActivity.products, 9999, true)));
        }

        Bundle args = getIntent().getExtras();
        UserProfile user = (UserProfile) args.get("User");

        mDatabase = FirebaseDatabase.getInstance().getReference("Users");

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
                    replaceFragment(new OrderListFragment(orders));
                    bottomNavigationView.getMenu().findItem(R.id.order_list_menu).setChecked(true);
                    break;

                case R.id.profile_menu:
                    replaceFragment(new ProfileFragment(user)); //тут
                    bottomNavigationView.getMenu().findItem(R.id.profile_menu).setChecked(true);
                    break;

                case R.id.catalog_menu:
                    replaceFragment(new AdminCatalogFragment()); //тут
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