package com.yarart.samsung_project.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.yarart.samsung_project.R;


public class EditProfileFragment extends Fragment {

    EditText etProfileFirstName, etProfileSecondName, etProfileClass, etProfileSchool, etProfileRegion;
    Button saveProfileChangesButton;

    public EditProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_edit_profile, container, false);
        etProfileFirstName = v.findViewById(R.id.etProfileFirstName);
        etProfileSecondName = v.findViewById(R.id.etProfileSecondName);
        etProfileClass = v.findViewById(R.id.etProfileClass);
        etProfileSchool = v.findViewById(R.id.etProfileSchool);
        etProfileRegion = v.findViewById(R.id.etProfileRegion);
        saveProfileChangesButton = v.findViewById(R.id.profileChangesButtonSave);
        saveProfileChangesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveProfileChanges();
            }
        });

        return v;
    }

    String[] saveProfileChanges() {
        String firstName = etProfileFirstName.getText().toString();
        String secondName = etProfileSecondName.getText().toString();
        String profileClass = etProfileClass.getText().toString();
        String profileSchool = etProfileSchool.getText().toString();
        String profileRegion = etProfileRegion.getText().toString();
        String[] profileChanges = {firstName, secondName, profileClass, profileSchool, profileRegion};
        return profileChanges;
    }
}