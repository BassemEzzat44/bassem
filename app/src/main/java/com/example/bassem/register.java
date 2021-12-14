package com.example.bassem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class register extends AppCompatActivity {
    public EditText registerName, registerEmail, registerPhone, registerPassword;
    public Button sing_up;
    public ProgressBar registerProgressBar;
    public FirebaseAuth firebaseAuth;
    public FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        registerProgressBar = findViewById(R.id.registerProgressBar);
        registerName = findViewById(R.id.registerName);
        registerEmail = findViewById(R.id.registerEmail);
        registerPhone = findViewById(R.id.registerPhone);
        registerPassword = findViewById(R.id.registerPassword);
        firebaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        sing_up = findViewById(R.id.registerSingUp);
        sing_up.setOnClickListener(v -> validationData());
    }

    private void validationData() {
        String name = registerName.getText().toString().trim();
        String Email = registerEmail.getText().toString().trim();
        String phone = registerPhone.getText().toString().trim();
        String passsword = registerPassword.getText().toString().trim();
        if (name.isEmpty()) {
            Toast.makeText(register.this, R.string.error_name_register, Toast.LENGTH_LONG).show();
            registerName.requestFocus();
            return;
        }

        if (Email.isEmpty()) {
            Toast.makeText(register.this, "Please add ur email", Toast.LENGTH_LONG).show();
            registerEmail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(Email).matches()) {
            Toast.makeText(register.this, "Please add valid email", Toast.LENGTH_LONG).show();
            registerEmail.requestFocus();
            return;
        }

        if (phone.isEmpty()) {
            Toast.makeText(register.this, "Please add ur phone", Toast.LENGTH_LONG).show();
            registerPhone.requestFocus();
            return;
        }

        if (phone.length() < 11) {
            Toast.makeText(register.this, "phone should be 11 char", Toast.LENGTH_LONG).show();
            registerPhone.requestFocus();
            return;
        }

        if (passsword.isEmpty()) {
            Toast.makeText(register.this, "Please add ur password", Toast.LENGTH_LONG).show();
            registerPassword.requestFocus();
            return;
        }
        if (passsword.length() < 6) {
            Toast.makeText(register.this, "password should be 11 char", Toast.LENGTH_LONG).show();
            registerPassword.requestFocus();
            return;
        }

        Bassem(Email, passsword, name, phone);

    }

    private void Bassem(String email, String pass, String name, String phone) {


        registerProgressBar.setVisibility(View.VISIBLE);

        firebaseAuth.createUserWithEmailAndPassword(email, pass)
                .addOnCompleteListener(task -> {

                    if (task.isSuccessful()) {
                        registerProgressBar.setVisibility(View.GONE);
                        //send data
                        sendData(email, pass, name, phone);
                    } else {
                        // handle error
                        registerProgressBar.setVisibility(View.VISIBLE);
                        Toast.makeText(register.this, "Error " + task.getException().getMessage(), Toast.LENGTH_LONG).show();

                    }

                });

    }

    private void sendData(String Email, String passsword, String name, String phone) {
        long tsLong = System.currentTimeMillis() / 1000;
        String timestamp = Long.toString(tsLong);
        HashMap<String, Object> map = new HashMap<>();
        map.put("email", Email);
        map.put("password", passsword);
        map.put("Name", name);
        map.put("phone", phone);
        firestore.collection("users").document().set(map)
                .addOnSuccessListener(unused -> {
                    Toast.makeText(register.this,"done",Toast.LENGTH_SHORT);
                    registerProgressBar.setVisibility(View.GONE);
                    getSharedPreferences("usershared",MODE_PRIVATE)
                            .edit()
                            .putString("username",name)
                            .putString("phone",phone)
                            .putString("email",Email)
                            .putBoolean("islogin",true)
                            .apply();
                    startActivity(new Intent(register.this,login.class));
                    finish();
                })

                .addOnFailureListener(e -> {
                    registerProgressBar.setVisibility(View.GONE);
            Toast.makeText(register.this,"error"+e.getMessage(),Toast.LENGTH_SHORT);
                });

    }





    }




