package com.yarart.samsung_project.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yarart.samsung_project.R;


public class ProfileFragment extends Fragment {


    ImageView profileImage;
    TextView profile_name, profile_type, profile_class, profile_school, profile_region;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_profile, container, false);

        profileImage = v.findViewById(R.id.profileImage);
        profile_name = v.findViewById(R.id.profile_name);
        profile_type = v.findViewById(R.id.profile_type);
        profile_class = v.findViewById(R.id.profile_class);
        profile_school = v.findViewById(R.id.profile_school);
        profile_region = v.findViewById(R.id.profile_region);

        profileImage.setImageResource(R.drawable.typical_user);
        return v;
    }
}