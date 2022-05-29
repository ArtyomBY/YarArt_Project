package com.yarart.samsung_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.yarart.samsung_project.classes.Basket;
import com.yarart.samsung_project.classes.Buyer;
import com.yarart.samsung_project.classes.Order;
import com.yarart.samsung_project.classes.Product;
import com.yarart.samsung_project.classes.UserProfile;
import com.yarart.samsung_project.fragments.BasketFragment;
import com.yarart.samsung_project.fragments.CatalogFragment;
import com.yarart.samsung_project.fragments.ProfileFragment;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    static public int total_price = 0;
    static public int position = 0;
    static public Uri uriResource;
    public static ArrayList<Product> products = new ArrayList<>();
    public static ArrayList<Order> orders = new ArrayList<>();
    public static ArrayList<Product> productsFromBasket = new ArrayList<>();
    public static Order userOrder = new Order();
    public static int userImageBitmap = 0;
    public static boolean isActivityCreated = false;
    public static int numberOfOrder = 0;

    public static UserProfile user;

    public BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        isActivityCreated = true;
        if (MainActivity2_Admin.isActivityCreated) MainActivity2_Admin.isActivityCreated = false;
        init();
    }


    public void init() {
        //Buyer buyer = new Buyer("Быков Артем Ильич", 10, "МБОУ сош №35", "г. Иваново, Ивановская обл.");
        if (products.size()==0) {
            products.add(new Product("Устрица", 10, "Пирожок с маком", true, R.drawable.ustrica));
            products.add(new Product("Питца", 35, "Шедевр кулинарии", true, R.drawable.pizza));
            products.add(new Product("Пирог с картошкой", 16, "Пирожок с картошкой", true, R.drawable.kartoshka));
        }
        if (orders.size()==0) {
            orders.add(new Order("Не выдан", "TEST0", new Basket(products, 61, true)));
            orders.add(new Order("Не выдан", "TEST1", new Basket(products, 54, true)));
            orders.add(new Order("Не выдан", "TEST2", new Basket(products, 9999, true)));
        }

        Bundle args = getIntent().getExtras();
        user = (UserProfile) args.get("User");


        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.catatlog_menu);
        replaceFragment(new CatalogFragment());

        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.basket_menu:
                    replaceFragment(new BasketFragment());
                    bottomNavigationView.getMenu().findItem(R.id.basket_menu).setChecked(true);
//                    Intent intent = new Intent(this, BasketActivity.class);
//                    startActivity(intent);
                    break;
                case R.id.profil_menu:
                    replaceFragment(new ProfileFragment(user));
                    bottomNavigationView.getMenu().findItem(R.id.profil_menu).setChecked(true);
//                    Intent intent2 = new Intent(this, UserProfileActivity.class);
//                    startActivity(intent2);
                    break;
                case R.id.catatlog_menu:
                    replaceFragment(new CatalogFragment());
                    bottomNavigationView.getMenu().findItem(R.id.catatlog_menu).setChecked(true);
                    break;
            }


            return true;
        });
    }



    public void replaceFragment(Fragment fragment){

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_FrameLayout, fragment);
        fragmentTransaction.commit();

    }
}