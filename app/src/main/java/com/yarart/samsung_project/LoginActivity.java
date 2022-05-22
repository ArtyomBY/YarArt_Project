package com.yarart.samsung_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.yarart.samsung_project.classes.UserProfile;

import java.util.EventListener;
import java.util.HashMap;

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

//    @Override
//    protected void onStart() {
//        super.onStart();
//        FirebaseUser cUser = myAuth.getCurrentUser();
//        if (cUser != null) {
//            Toast.makeText(this, "Вы вошли под: " + cUser.getEmail(), Toast.LENGTH_SHORT).show();
//
//            Intent i = new Intent(LoginActivity.this, MainActivity.class);
//            i.putExtra("User", user);
//            startActivity(i);
//
//        } else{
////            Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
//        }
//    }

    public void onClickLogin(View view) {
        if (editTextEmail.getText().toString().isEmpty() || editTextPassword.getText().toString().isEmpty()) {
            Toast.makeText(this, "Вы ввели не все данные", Toast.LENGTH_SHORT).show();
        } else {
            myAuth.signInWithEmailAndPassword(editTextEmail.getText().toString(), editTextPassword.getText().toString())
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {


                                myDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        if(snapshot.exists()){
                                            HashMap<String, Object> userMap = (HashMap<String, Object>) snapshot.getValue();

                                            user = (UserProfile) userMap.get("1.programx.1@mail.ru");
//                                        user = (UserProfile) snapshot.getValue();
                                        } else
                                            Toast.makeText(LoginActivity.this, "", Toast.LENGTH_SHORT).show();
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });

//                                    Bundle args = getIntent().getExtras();
//                                    UserProfile newUser = (UserProfile) args.get("User");


                                Intent i = new Intent(LoginActivity.this, MainActivity.class);
                                i.putExtra("User", user);
                                startActivity(i);

                            } else{
                                Toast.makeText(LoginActivity.this, "Вы допустили ошибки при вводе данных", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

        }
    }
}