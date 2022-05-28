package com.yarart.samsung_project.fragments;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.yarart.samsung_project.LoginActivity;
import com.yarart.samsung_project.MainActivity;
import com.yarart.samsung_project.MainActivity2_Admin;
import com.yarart.samsung_project.R;
import com.yarart.samsung_project.classes.UserProfile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;


public class ProfileFragment extends Fragment {

    private static final int IMAGE_PICK_CODE = 1000;
    private static final int PERMISSION_CODE = 1001;
    private static final String APP_PREFERENCES = "";

    View v;
    DatabaseReference mDatabase;

    static int imageResource;
    ImageView profileImage;

    TextView profile_id, profile_name, profile_type, profile_class, profile_school, profile_region, wallet_balance;
    ImageButton btn_exit, btn_editProfile;
    Button goToUserOrderButton;
    UserProfile userProfile;


    public ProfileFragment(UserProfile userProfile) {
        this.userProfile = userProfile;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_profile, container, false);

        btn_editProfile = v.findViewById(R.id.editProfileButton);
        goToUserOrderButton = v.findViewById(R.id.goToOrderButton);

        profileImage = v.findViewById(R.id.profileImage);

//        InputStream inputStream = requireActivity().getApplicationContext().getAssets().open(){
//            Drawable d = Drawable.createFromStream(inputStream, null);
//            profileImage.setImageDrawable(d);
//        }

//        profileImage.setImageResource(d);
        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //check runtime permission
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    if (requireActivity().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                            == PackageManager.PERMISSION_DENIED){
                        //permission not granted, request it.
                        String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
                        //show popup for runtime permission
                        requestPermissions(permissions, PERMISSION_CODE);
                    }
                    else {
                        //permission already granted
                        pickImageFromGallery();
                    }
                }
                else {
                    //system os is less then marshmallow
                    pickImageFromGallery();
                }

            }
        });

        goToUserOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (MainActivity.userOrder.order_status!=null) {
                    MainActivity mainActivity = (MainActivity) requireActivity();
                    mainActivity.replaceFragment(new OrderFragment(MainActivity.userOrder));
                }
               else Toast.makeText(getContext(), "Вы еще не сделали заказ", Toast.LENGTH_SHORT).show();
            }
        });


        profile_id = v.findViewById(R.id.tv_user_id);
        profile_id.setText(userProfile.getId());

        profile_name = v.findViewById(R.id.profile_name);
        profile_name.setText(userProfile.getFirstName() + " " + userProfile.getSecondName());

        profile_type = v.findViewById(R.id.profile_type);
        profile_type.setText(userProfile.getUser_status());

        profile_class = v.findViewById(R.id.profile_class);
        profile_school = v.findViewById(R.id.profile_school);
        profile_region = v.findViewById(R.id.profile_region);

        wallet_balance = v.findViewById(R.id.tv_balance);
        wallet_balance.setText(Integer.toString((int) userProfile.getWallet()));
        mDatabase = FirebaseDatabase.getInstance().getReference("Users").child(userProfile.getId());
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userProfile = snapshot.getValue(UserProfile.class);
                wallet_balance.setText(Integer.toString((int) userProfile.getWallet()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        btn_exit = v.findViewById(R.id.btn_exitFromAccount);
        btn_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickExitFromAccount(view);
            }
        });
        btn_editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editProfile(view);
            }
        });
        return v;
    }





    //handle result of picked image
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == IMAGE_PICK_CODE){
            //set image to image view
            MainActivity.uriResource = data.getData();
            profileImage.setImageURI(data.getData());
        }
    }
    //handle result of runtime permission
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case PERMISSION_CODE:{
                if (grantResults.length >0 && grantResults[0] ==
                        PackageManager.PERMISSION_GRANTED){
                    //permission was granted
                    pickImageFromGallery();
                }
                else {
                    //permission was denied
                    Toast.makeText(getContext(), "Permission denied...!", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
    private void pickImageFromGallery() {
        //intent to pick image
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_PICK_CODE);
    }








    public void onClickExitFromAccount(View view) {
        FirebaseAuth.getInstance().signOut();

        Intent i = new Intent(getContext(), LoginActivity.class);
        startActivity(i);
    }

    void editProfile(View view) {
        if (userProfile.getUser_status().equals("Admin")){
            MainActivity2_Admin mainActivity2 = (MainActivity2_Admin) requireActivity();
            mainActivity2.replaceFragment(new EditProfileFragment());
        } else if (userProfile.getUser_status().equals("Buyer")){
            MainActivity mainActivity = (MainActivity) requireActivity();
            mainActivity.replaceFragment(new EditProfileFragment());
        } else {
            Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
        }

    }
}