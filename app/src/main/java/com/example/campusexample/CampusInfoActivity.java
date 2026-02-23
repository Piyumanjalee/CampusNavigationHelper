package com.example.campusexample;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;


public class CampusInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Connect the Java class with the corresponding XML layout file
        setContentView(R.layout.activity_campus_info);

        // If you want to change the title of the Action Bar for this screen
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Campus Locations");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true); // Enable back button
        }

        // Initialize Navigation UI
        Spinner spinnerFrom = findViewById(R.id.spinnerFrom);
        Spinner spinnerTo = findViewById(R.id.spinnerTo);
        Button btnNavigate = findViewById(R.id.btnNavigate);
        TextView txtRoute = findViewById(R.id.txtRoute);

        // Populate Spinners with Campus Locations
        String[] locations = {
                "Main Library",
                "IT Faculty Building",
                "Student Canteen",
                "Faculty of Applied Sciences"
        };
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, locations);
        spinnerFrom.setAdapter(adapter);
        spinnerTo.setAdapter(adapter);

        // Handle Navigate Button Click
        btnNavigate.setOnClickListener(v -> {
            String from = spinnerFrom.getSelectedItem().toString();
            String to = spinnerTo.getSelectedItem().toString();

            if (from.equals(to)) {
                txtRoute.setText("You are already at the " + to + "!");
                txtRoute.setVisibility(View.VISIBLE);
                return;
            }

            // Simple mock routes for beginner level blueprint navigation
            String routeText = "Route from " + from + " to " + to + ":\n\n";

            if (from.equals("Main Library") && to.equals("Student Canteen")) {
                routeText += "1. Exit the Main Library and walk straight for 20m.\n2. Turn right and walk past the Administration block (52m).\n3. The Student Canteen will be on your left.";
            } else if (from.equals("Student Canteen") && to.equals("IT Faculty Building")) {
                routeText += "1. Head north from the Canteen for 15m.\n2. Cross the main courtyard (40m).\n3. Turn slightly left and walk 10m to reach the IT Faculty Building.";
            } else if (from.equals("IT Faculty Building") && to.equals("Faculty of Applied Sciences")) {
                routeText += "1. Exit the IT Faculty and walk straight 30m.\n2. Turn right onto the covered walkway and continue for 85m.\n3. You will arrive at the Faculty of Applied Sciences.";
            } else {
                routeText += "1. Head towards the central campus square (walk 45m).\n2. Look for the signs pointing towards " + to + ".\n3. Follow the main path for approximately 60m to your destination.";
            }

            txtRoute.setText(routeText);
            txtRoute.setVisibility(View.VISIBLE);
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        // Handle the back button click to return to the previous activity
        onBackPressed();
        return true;
    }
}