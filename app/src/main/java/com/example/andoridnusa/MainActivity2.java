package com.example.andoridnusa;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.andoridnusa.Models.UserDetails;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity2 extends AppCompatActivity {

    Button iniButton;
    TextInputEditText iniPennguna, iniSandi, iniNIM, iniEmail;
    FirebaseAuth mAuth;
    DatabaseReference usersRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        iniButton = findViewById(R.id.iniButton);
        iniPennguna = findViewById(R.id.iniPengguna);
        iniSandi = findViewById(R.id.iniSandi);
        iniNIM = findViewById(R.id.iniNIM);
        iniEmail = findViewById(R.id.iniEmail);

        mAuth = FirebaseAuth.getInstance();
        usersRef = FirebaseDatabase.getInstance().getReference("Users");

        iniButton.setOnClickListener(view -> {
            String username = iniPennguna.getText().toString().trim();
            String password = iniSandi.getText().toString().trim();
            String NIM = iniNIM.getText().toString().trim();
            String email = iniEmail.getText().toString().trim();

            if (TextUtils.isEmpty(username)) {
                iniPennguna.setError("Masukkan username");
                iniPennguna.requestFocus();
                return;
            }
            if (TextUtils.isEmpty(password)) {
                iniSandi.setError("Masukkan password");
                iniSandi.requestFocus();
                return;
            }
            if (TextUtils.isEmpty(NIM)) {
                iniNIM.setError("Masukkan NIM");
                iniNIM.requestFocus();
                return;
            }
            if (TextUtils.isEmpty(email)) {
                iniEmail.setError("Masukkan email");
                iniEmail.requestFocus();
                return;
            }

            registerUser(username, password, NIM, email);
        });
    }

    private void registerUser(String username, String password, String NIM, String email) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser firebaseUser = mAuth.getCurrentUser();
                        if (firebaseUser != null) {
                            String uid = firebaseUser.getUid();
                            UserDetails userDetails = new UserDetails(uid, username, password, NIM, email);

                            usersRef.child(uid).setValue(userDetails).addOnCompleteListener(task1 -> {
                                if (task.isSuccessful()) {
                                    Toast.makeText(MainActivity2.this, "Login berhasil", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(MainActivity2.this, MainActivity3.class));
                                    finish();

                                } else {
                                    Toast.makeText(MainActivity2.this, "Gagal menyimpan data pengguna!", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    } else {
                        try {
                            throw task.getException();
                        } catch (FirebaseAuthUserCollisionException e) {
                            iniEmail.setError("Email sudah terdaftar");
                            iniEmail.requestFocus();
                        } catch (Exception e) {
                            Log.e("Register", e.getMessage());
                            Toast.makeText(MainActivity2.this, "Registrasi gagal: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
