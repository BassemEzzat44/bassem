package com.example.bassem;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class forgit extends AppCompatActivity {

public EditText registerEmail;
public Button forg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgit);
        registerEmail = findViewById(R.id.registerEmail);
        forg = findViewById(R.id.forg);
        forg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerforg();

            }
        });
    }

    public void registerforg() {
        String Emaill = registerEmail.getText().toString().trim();
        if (Emaill.isEmpty()) {
            Toast.makeText(forgit.this, "Please add ur email", Toast.LENGTH_LONG).show();
            registerEmail.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(Emaill).matches()) {
            Toast.makeText(forgit.this, "Please add valid email", Toast.LENGTH_LONG).show();
            registerEmail.requestFocus();
            return;
        }
    }
}