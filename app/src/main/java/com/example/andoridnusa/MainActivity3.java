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
            String email = emailUser.getText() != null ? emailUser.getText().toString().trim() : "";
            String password = passwordUser.getText() != null ? passwordUser.getText().toString().trim() : "";

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(MainActivity3.this, "Email dan password tidak boleh kosong!", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(MainActivity3.this, "Format email tidak valid!", Toast.LENGTH_SHORT).show();
                return;
            }

            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Login berhasil", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(MainActivity3.this, "Login gagal, periksa email dan password!", Toast.LENGTH_SHORT).show();
                        }
                    });

            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Login berhasil", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                            finish();
                        } else {
                            String errorMessage = task.getException() != null ? task.getException().getMessage() : "Unknown error";
                            Toast.makeText(MainActivity3.this, "Login gagal: " + errorMessage, Toast.LENGTH_LONG).show();
                        }
                    });

        });
    }
}
