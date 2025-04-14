package com.example.andoridnusa;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.color.utilities.Cam16;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HomeActivity extends AppCompatActivity {

    private TextView Card1, CardDeskripsi1, Card2, CardDeskripsi2, Card3, CardDeskripsi3, Card4, CardDeskripsi4, Card5, CardDeskripsi5, Card6, CardDeskripsi6;
    private Button btnLogout;
    private FirebaseAuth mAuth;
    private static final String TAG = "HomeActivity";

    CardView cardView1, cardView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);

        mAuth = FirebaseAuth.getInstance();

        FirebaseUser user = mAuth.getCurrentUser();

        Card1 = findViewById(R.id.Card1);
        CardDeskripsi1 = findViewById(R.id.CardDeskripsi1);
        Card2 = findViewById(R.id.Card2);
        CardDeskripsi2 = findViewById(R.id.CardDeskripsi2);
        Card3 = findViewById(R.id.Card3);
        CardDeskripsi3 = findViewById(R.id.CardDeskripsi3);
        Card4 = findViewById(R.id.Card4);
        CardDeskripsi4 = findViewById(R.id.CardDeskripsi4);
        Card5 = findViewById(R.id.Card5);
        CardDeskripsi5 = findViewById(R.id.CardDeskripsi5);
        Card6 = findViewById(R.id.Card6);
        CardDeskripsi6 = findViewById(R.id.CardDeskripsi6);

        if (user != null) {
            Card1.setText("Selamat Datang, " + user.getDisplayName());
            CardDeskripsi1.setText("Ini adalah halaman utama aplikasi");
        }else {
            Card1.setText("Halo");
            CardDeskripsi1.setText("Silahkan login untuk melihat lebih lanjut");

        }



        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    public void openProfile(android.view.View view) {
        Intent intent = new Intent(HomeActivity.this, ProfileActivity.class);
        startActivity(intent);
    }

}