package com.example.bassem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class MainActivity extends AppCompatActivity {
    boolean isLogin;
    int dddddd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        isLogin = getSharedPreferences("usershared", MODE_PRIVATE)
                .getBoolean("islogin", false);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this, register.class);
                startActivity(intent);
                finish();
            }
        }, 3000);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (isLogin) {
            Intent intent = new Intent(MainActivity.this, login.class);
            startActivity(intent);
            finish();

        } else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(MainActivity.this, register.class);
                    startActivity(intent);
                    finish();
                }
            }, 3000);

        }
    }
}