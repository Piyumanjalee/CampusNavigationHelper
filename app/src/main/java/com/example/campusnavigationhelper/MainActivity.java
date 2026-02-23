package com.example.campusexample;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {


    Button btnMap, btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        btnMap = findViewById(R.id.btnMap);
        btnLogout = findViewById(R.id.btnLogout);


        btnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to CampusInfoActivity
                Intent intent = new Intent(MainActivity.this, com.example.campusexample.CampusInfoActivity.class);
                startActivity(intent);
            }
        });


        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Return to Login Screen
                Toast.makeText(MainActivity.this, "Logged Out", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish(); // Close dashboard to prevent going back
            }
        });
    }
}