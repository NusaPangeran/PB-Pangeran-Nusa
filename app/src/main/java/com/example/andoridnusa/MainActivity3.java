package com.example.andoridnusa;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity3 extends AppCompatActivity {

    TextInputEditText emailUser, passwordUser;
    CheckBox checkBoxes;
    Button btLogin;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        emailUser = findViewById(R.id.email);
        passwordUser = findViewById(R.id.password);
        checkBoxes = findViewById(R.id.checkboxes);
        btLogin = findViewById(R.id.btnLogin);

        btLogin.setOnClickListener(view -> {
            String email = emailUser.getText().toString().trim();
            String password = passwordUser.getText().toString().trim();

            if (email.isEmpty()) {
                emailUser.setError("Email tidak boleh kosong");
                emailUser.requestFocus();
                return;
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                emailUser.setError("Format email tidak valid");
                emailUser.requestFocus();
                return;
            }

            if (password.isEmpty()) {
                passwordUser.setError("Password tidak boleh kosong");
                passwordUser.requestFocus();
                return;
            }

            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(MainActivity3.this, "Login berhasil", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(MainActivity3.this, HomeActivity.class));
                            finish();
                        } else {
                            Toast.makeText(MainActivity3.this, "Login gagal, periksa email dan password!", Toast.LENGTH_SHORT).show();
                        }
                    });
        });
    }
}
