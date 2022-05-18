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
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {

    EditText editTextEmail, editTextPassword;
    private FirebaseAuth myAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextTextPassword);
        myAuth = FirebaseAuth.getInstance();

    }


    public void onClickRegistration(View view) {

        Intent i = new Intent(this, RegistrationActivity.class);
        startActivity(i);

    }

    public void onClickLogin(View view) {
        if (editTextEmail.getText().toString().isEmpty() || editTextPassword.getText().toString().isEmpty()) {
            Toast.makeText(this, "Вы ввели не все данные", Toast.LENGTH_SHORT).show();
        } else {
            myAuth.signInWithEmailAndPassword(editTextEmail.getText().toString(), editTextPassword.getText().toString())
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Intent i = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(i);
                            } else{
                                Toast.makeText(LoginActivity.this, "Вы допустили ошибки при вводе данных", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }
}