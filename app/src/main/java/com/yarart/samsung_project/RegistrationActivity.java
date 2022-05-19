package com.yarart.samsung_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseError;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.yarart.samsung_project.classes.UserProfile;

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

    }

    public void onClickUserRegistration(View view) {

        if (editTextEmail.getText().toString().isEmpty() || editTextFirstName.getText().toString().isEmpty() || editTextSecondName.getText().toString().isEmpty() || editTextPassword.getText().toString().isEmpty() || getEditTextPassword2.getText().toString().isEmpty() || (checkBoxBuyer.isChecked() == false && checkBoxAdmin.isChecked() == false) || (checkBoxBuyer.isChecked() == true && checkBoxAdmin.isChecked() == true)){
            Toast.makeText(this, "Вы что-то пропустили", Toast.LENGTH_SHORT).show();
        } else {

            if (checkBoxBuyer.isChecked()){
                PROFILE_TYPE = "Buyer";
                myDatabase = FirebaseDatabase.getInstance().getReference(PROFILE_TYPE);
            } else if (checkBoxAdmin.isChecked()) {
                PROFILE_TYPE = "Admin";
                myDatabase = FirebaseDatabase.getInstance().getReference(PROFILE_TYPE);
            } else {
                Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
            }

            myAuth.createUserWithEmailAndPassword(editTextEmail.getText().toString(), editTextPassword.getText().toString())
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){

                                String id = myDatabase.getKey();
                                id = myDatabase.getKey();
                                String firstName = editTextFirstName.getText().toString();
                                String secondName = editTextSecondName.getText().toString();
                                String email = editTextEmail.getText().toString();
                                UserProfile newUser = new UserProfile(id, firstName, secondName, email, PROFILE_TYPE);
                                myDatabase.push().setValue(newUser);


                                Intent i = new Intent(RegistrationActivity.this, LoginActivity.class);
                                Toast.makeText(RegistrationActivity.this, "Вы успешно создали аккаунт", Toast.LENGTH_SHORT).show();
                                startActivity(i);
                            } else
                                Toast.makeText(RegistrationActivity.this, "Кажется вы допустили ошибку", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }
}