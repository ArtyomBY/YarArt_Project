package com.yarart.samsung_project.fragments;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yarart.samsung_project.LoginActivity;
import com.yarart.samsung_project.MainActivity;
import com.yarart.samsung_project.R;
import com.yarart.samsung_project.classes.UserProfile;

import java.io.IOException;


public class ProfileFragment extends Fragment {

    static final int GALLERY_REQUEST = 1;

    View v;
    ImageView profileImage;
    TextView profile_id, profile_name, profile_type, profile_class, profile_school, profile_region;
    UserProfile userProfile;



//    public ProfileFragment(UserProfile userProfile) {
//        this.userProfile = userProfile;
//    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_profile, container, false);

        profileImage = v.findViewById(R.id.profileImage);
        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, GALLERY_REQUEST);
            }
        });

        profile_id = v.findViewById(R.id.tv_user_id);
//        profile_id.setText(userProfile.getId());

        profile_name = v.findViewById(R.id.profile_name);
//        profile_name.setText(userProfile.getFirstName() + " " + userProfile.getSecondName());

        profile_type = v.findViewById(R.id.profile_type);
//        profile_type.setText(userProfile.getUser_status());

        profile_class = v.findViewById(R.id.profile_class);
        profile_school = v.findViewById(R.id.profile_school);
        profile_region = v.findViewById(R.id.profile_region);

        profileImage.setImageResource(R.drawable.typical_user);
        return v;
    }

    public void onClickExitFromAccount(View view) {
        Intent i = new Intent(getContext(), LoginActivity.class);
        startActivity(i);
    }
}