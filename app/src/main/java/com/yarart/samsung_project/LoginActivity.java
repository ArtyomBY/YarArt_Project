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
//    CheckBox checkBoxBuyer, checkBoxAdmin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();

    }

    private void init() {
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextTextPassword);
//        checkBoxAdmin = findViewById(R.id.checkBoxAdmin);
//        checkBoxBuyer = findViewById(R.id.checkBoxBuyer);

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

//                                ChildEventListener childEventListener = new ChildEventListener() {
//                                    @Override
//                                    public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//
//                                        user = snapshot.getValue(UserProfile.class);
//
//                                    }
//
//                                    @Override
//                                    public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//
//                                    }
//
//                                    @Override
//                                    public void onChildRemoved(@NonNull DataSnapshot snapshot) {
//
//                                    }
//
//                                    @Override
//                                    public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//
//                                    }
//
//                                    @Override
//                                    public void onCancelled(@NonNull DatabaseError error) {
//
//                                    }
//                                };

                                // вот здесь почему-то ID не совпадает с ID записанным в Firebase. Почему так? В идеале с этим можно разобраться, но сейчас не критично
                                // Ни тем, ни другим способом не совпадает
//                                String userId1 = myAuth.getCurrentUser().getUid();
//                                String userId = task.getResult().getUser().getUid();


                                // Если бы совпадало, то можно было бы вот так получить текущего пользователя:
                                // сначала взять ID пользователя, котрый залогинился
                                // и затем "провалиться" в пользователя с нужным ID и вытащить его через get
//                                myDatabase.child(userId).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {

                                // P.S. А вообще еще советую почитать про то, как работать со списками данных
                                FirebaseUser cUser = myAuth.getCurrentUser();
                                String uid = cUser.getUid();
                                myDatabase.child(uid).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                                        if (task.isSuccessful()) {
                                            DataSnapshot document = task.getResult();



                                            if (document.exists()) {
                                                // и затем вот здесь распаковать данные
                                            user = document.getValue(UserProfile.class);

                                                // Но поскольку ID юзера не совпадает, приходится вытаскивать целый список данных в виде хэшмапа
                                                // у getValue сложный параметр, он используется для конвертации к Generic-типу (это который в <> указан)
                                                // Везде, где нужно вытащить целый список данных, можно так писать.
//                                                HashMap<String, UserProfile> users = document.getValue(new GenericTypeIndicator<HashMap<String, UserProfile>>() {
//                                                });

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
                                                // TODO
                                                // Здесь дальше уже нужно перебрать через цикл HashMap и найти пользователя с нужным email-ом
                                                // (по сути из-за поломки айдишников это единственный вариант на данный момент)
                                                // и выбрать юзера с нужным email-ом и уже с ним какие-то действия делать, какие вы хотели
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