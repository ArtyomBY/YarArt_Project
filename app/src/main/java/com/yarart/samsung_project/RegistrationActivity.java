package com.yarart.samsung_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseError;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.yarart.samsung_project.classes.UserProfile;

import java.util.ArrayList;
import java.util.HashMap;

public class RegistrationActivity extends AppCompatActivity {

    EditText editTextEmail, editTextFirstName, editTextSecondName, editTextPassword, getEditTextPassword2;
    CheckBox checkBoxAdmin, checkBoxBuyer;
    private FirebaseAuth myAuth;
    private DatabaseReference myDatabase;
    private String PROFILE_TYPE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_user);

        init();

    }

    private void init() {
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextFirstName = findViewById(R.id.editTextTextFirstName);
        editTextSecondName = findViewById(R.id.editTextTextPersonSecondName);
        editTextPassword = findViewById(R.id.editTextTextPassword);
        getEditTextPassword2 = findViewById(R.id.editTextTextPassword2);
        checkBoxAdmin = findViewById(R.id.checkBoxAdmin);
        checkBoxBuyer = findViewById(R.id.checkBoxBuyer);

        myAuth = FirebaseAuth.getInstance();
        myDatabase = FirebaseDatabase.getInstance().getReference("Users");


    }

    public void onClickUserRegistration(View view) {

        if (editTextEmail.getText().toString().isEmpty() || editTextFirstName.getText().toString().isEmpty() || editTextSecondName.getText().toString().isEmpty() || editTextPassword.getText().toString().isEmpty() || getEditTextPassword2.getText().toString().isEmpty()
                || (checkBoxBuyer.isChecked() == false && checkBoxAdmin.isChecked() == false) || (checkBoxBuyer.isChecked() == true && checkBoxAdmin.isChecked() == true)){
            Toast.makeText(this, "???? ??????-???? ????????????????????", Toast.LENGTH_SHORT).show();
        } else {

            if (checkBoxBuyer.isChecked()){
                PROFILE_TYPE = "Buyer";
            } else if (checkBoxAdmin.isChecked()) {
                PROFILE_TYPE = "Admin";
            } else {
                Toast.makeText(this, "ErrorCheck", Toast.LENGTH_SHORT).show();
            }

            myAuth.createUserWithEmailAndPassword(editTextEmail.getText().toString(), editTextPassword.getText().toString())
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                String uid = myAuth.getCurrentUser().getUid();

                                String id = uid;
                                String firstName = editTextFirstName.getText().toString();
                                String secondName = editTextSecondName.getText().toString();
                                String email = editTextEmail.getText().toString();
                                UserProfile newUser = new UserProfile(id, firstName, secondName, email, PROFILE_TYPE);

                                myDatabase.child(uid).setValue(newUser);


                                Toast.makeText(RegistrationActivity.this, "???? ?????????????? ?????????????? ??????????????", Toast.LENGTH_SHORT).show();

                                Intent i = new Intent(RegistrationActivity.this, LoginActivity.class);
                                startActivity(i);

                            } else
                                Toast.makeText(RegistrationActivity.this, "?????????????? ???? ?????????????????? ????????????", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }
}