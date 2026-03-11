package com.example.campusexample;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    // 1. Declare all buttons including btnNotes
    Button btnMap, btnNotes, btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 2. Initialize buttons from XML layout
        btnMap = findViewById(R.id.btnMap);
        btnNotes = findViewById(R.id.btnNotes); // Connected to 'My Notes' button
        btnLogout = findViewById(R.id.btnLogout);

        // 3. Set click listener for Campus Map
        btnMap.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, CampusInfoActivity.class);
            startActivity(intent);
        });

        // 4. Set click listener for My Notes (Navigation to NotesActivity)
        btnNotes.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, NotesActivity.class);
            startActivity(intent);
        });

        // 5. Set click listener for Logout
        btnLogout.setOnClickListener(v -> {
            Toast.makeText(MainActivity.this, "Logged Out Successfully", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish(); // Close dashboard to prevent going back
        });
    }
}