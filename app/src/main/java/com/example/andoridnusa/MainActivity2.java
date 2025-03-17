package com.example.andoridnusa;

import static android.content.ContentValues.TAG;
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
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference usersRef = database.getReference("users");

        iniButton.setOnClickListener(view -> {
            String username, password, NIM, email;

            username = iniPennguna.getText() != null ? iniPennguna.getText().toString() : "";
            password = iniSandi.getText() != null ? iniSandi.getText().toString() : "";
            NIM = iniNIM.getText() != null ? iniNIM.getText().toString() : "";
            email = iniEmail.getText() != null ? iniEmail.getText().toString() : "";

            if (TextUtils.isEmpty(username)) {
                Toast.makeText(MainActivity2.this, "Enter Username", Toast.LENGTH_LONG).show();
                iniPennguna.requestFocus();
                return;
            } else if (TextUtils.isEmpty(password)) {
                Toast.makeText(MainActivity2.this, "Enter Password", Toast.LENGTH_LONG).show();
                iniSandi.requestFocus();
                return;
            } else if (TextUtils.isEmpty(NIM)) {
                Toast.makeText(MainActivity2.this, "Please Insert your NIM", Toast.LENGTH_LONG).show();
                iniNIM.requestFocus();
                return;
            } else if (TextUtils.isEmpty(email)) {
                Toast.makeText(MainActivity2.this, "Enter Email", Toast.LENGTH_LONG).show();
                iniEmail.requestFocus();
                return;
            } else {
                registerUser(username, password, NIM, email);
            }
        });
    }

    private void registerUser(String username, String password, String NIM, String email) {
        FirebaseAuth auth = FirebaseAuth.getInstance();

        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(MainActivity2.this, task -> {
            if (task.isSuccessful()) {
                FirebaseUser fUser = auth.getCurrentUser();
                if (fUser == null) return;

                String uid = fUser.getUid();
                UserDetails userDetails = new UserDetails(uid, username, password, NIM, email);

                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
                reference.child(uid).setValue(userDetails).addOnCompleteListener(task1 -> {
                    if (task1.isSuccessful()) {
                        fUser.sendEmailVerification();
                        Toast.makeText(MainActivity2.this, "Account Created", Toast.LENGTH_LONG).show();

                        Intent intent = new Intent(MainActivity2.this, HomeActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(MainActivity2.this, "Account registration failed", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "Register Error");
                    }
                });
            } else {
                try {
                    throw task.getException();
                } catch (FirebaseAuthUserCollisionException e) {
                    iniEmail.setError("Email is already registered");
                } catch (Exception e) {
                    Log.e(TAG, e.getMessage());
                    Toast.makeText(MainActivity2.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
