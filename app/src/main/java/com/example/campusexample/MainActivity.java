package com.example.campusexample;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    // Declare required buttons only
    Button btnMap, btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize buttons
        btnMap = findViewById(R.id.btnMap);
        btnLogout = findViewById(R.id.btnLogout);

        // Set click listener for Campus Map
        btnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to CampusInfoActivity
                Intent intent = new Intent(MainActivity.this, CampusInfoActivity.class);
                startActivity(intent);
            }
        });

        // Set click listener for Logout
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