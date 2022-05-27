package com.yarart.samsung_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.yarart.samsung_project.classes.Buyer;
import com.yarart.samsung_project.classes.Product;
import com.yarart.samsung_project.classes.UserProfile;
import com.yarart.samsung_project.fragments.AdminCatalogFragment;
import com.yarart.samsung_project.fragments.BasketFragment;
import com.yarart.samsung_project.fragments.CatalogFragment;
import com.yarart.samsung_project.fragments.ProfileFragment;
import com.yarart.samsung_project.fragments.RefillFragment;

public class MainActivity2_Admin extends AppCompatActivity {

    public BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity2_admin);

        init();



    }


    public void init() {
        //Buyer buyer = new Buyer("Быков Артем Ильич", 10, "МБОУ сош №35", "г. Иваново, Ивановская обл.");
        if (MainActivity.products.size()==0) {
            MainActivity.products.add(new Product("Устрица", 10, "Пирожок с маком", true, R.drawable.ustrica));
            MainActivity.products.add(new Product("Питца", 35, "Шедевр кулинарии", true, R.drawable.pizza));
            MainActivity.products.add(new Product("Пирог с картошкой", 16, "Пирожок с картошкой", true, R.drawable.kartoshka));
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
//                    Intent intent = new Intent(this, BasketActivity.class);
//                    startActivity(intent);
                    break;

                case R.id.order_list_menu:

                case R.id.profile_menu:
                    replaceFragment(new ProfileFragment(user)); //тут
                    bottomNavigationView.getMenu().findItem(R.id.profile_menu).setChecked(true);
//                    Intent intent2 = new Intent(this, UserProfileActivity.class);
//                    startActivity(intent2);
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