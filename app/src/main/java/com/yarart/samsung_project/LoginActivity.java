package com.yarart.samsung_project;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.yarart.samsung_project.classes.UserProfile;

import java.util.EventListener;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    EditText editTextEmail, editTextPassword;
    private FirebaseAuth myAuth;
    private DatabaseReference myDatabase;
    UserProfile user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();

    }

    private void init() {
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextTextPassword);

        myAuth = FirebaseAuth.getInstance();
        myDatabase = FirebaseDatabase.getInstance().getReference("Users");



    }


    public void onClickRegistrationForm(View view) {

        Intent i = new Intent(this, RegistrationActivity.class);
        startActivity(i);

    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser cUser = myAuth.getCurrentUser();
        String uid = myAuth.getUid();
        if (cUser != null) {
            myDatabase.child(uid).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    if (task.isSuccessful()) {
                        DataSnapshot document = task.getResult();
                        if (document.exists()) {
                            user = document.getValue(UserProfile.class);
                            if (user.getUser_status().equals("Buyer")){
                                Intent i = new Intent(LoginActivity.this, MainActivity.class);
                                i.putExtra("User", user);
                                Toast.makeText(LoginActivity.this, "Вы вошли под: " + cUser.getEmail(), Toast.LENGTH_SHORT).show();
                                startActivity(i);
                            } else {
                                Intent i = new Intent(LoginActivity.this, MainActivity2_Admin.class);
                                i.putExtra("User", user);
                                Toast.makeText(LoginActivity.this, "Вы вошли под: " + cUser.getEmail(), Toast.LENGTH_SHORT).show();
                                startActivity(i);
                            }
                            Log.d("GET_USER", "DocumentSnapshot data: TODO find user in HashMap");
                        } else {
                            Log.d("GET_USER", "No such document");
                        }
                    } else {
                        Log.d("GET_USER", "get failed with ", task.getException());
                    }
                }
            });
            Toast.makeText(LoginActivity.this, "Вы вошли под: " + cUser.getEmail(), Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(this, "Войдите в аккаунт", Toast.LENGTH_SHORT).show();
        }

}

    public void onClickLogin(View view) {
        if (editTextEmail.getText().toString().isEmpty() || editTextPassword.getText().toString().isEmpty()) { // || (checkBoxBuyer.isChecked() == false && checkBoxAdmin.isChecked() == false) || (checkBoxBuyer.isChecked() == true && checkBoxAdmin.isChecked() == true)
            Toast.makeText(this, "Вы ввели не все данные", Toast.LENGTH_SHORT).show();
        } else {
            String email = editTextEmail.getText().toString();
            myAuth.signInWithEmailAndPassword(email, editTextPassword.getText().toString())
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {

                                FirebaseUser cUser = myAuth.getCurrentUser();
                                String uid = cUser.getUid();
                                myDatabase.child(uid).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                                        if (task.isSuccessful()) {
                                            DataSnapshot document = task.getResult();



                                            if (document.exists()) {
                                            user = document.getValue(UserProfile.class);

                                                if (user.getUser_status().equals("Buyer")){
                                                    Intent i = new Intent(LoginActivity.this, MainActivity.class);
                                                    i.putExtra("User", user);
                                                    Toast.makeText(LoginActivity.this, "Вы вошли под: " + cUser.getEmail(), Toast.LENGTH_SHORT).show();
                                                    startActivity(i);
                                                } else {
                                                    Intent i = new Intent(LoginActivity.this, MainActivity2_Admin.class);
                                                    i.putExtra("User", user);
                                                    Toast.makeText(LoginActivity.this, "Вы вошли под: " + cUser.getEmail(), Toast.LENGTH_SHORT).show();
                                                    startActivity(i);
                                                }

                                                Log.d("GET_USER", "DocumentSnapshot data: TODO find user in HashMap");
                                            } else {
                                                Log.d("GET_USER", "No such document");
                                            }
                                        } else {
                                            Log.d("GET_USER", "get failed with ", task.getException());
                                        }
                                    }
                                });


                            } else{
                                Toast.makeText(LoginActivity.this, "Вы допустили ошибки при вводе данных", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

        }
    }
}