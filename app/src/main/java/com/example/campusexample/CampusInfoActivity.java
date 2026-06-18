package com.example.campusexample;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast; // අලුතින් එකතු කරන ලදි
import androidx.appcompat.app.AppCompatActivity;


public class CampusInfoActivity extends AppCompatActivity {

    // Introduce DatabaseHelper
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Connect the Java class with the corresponding XML layout file
        setContentView(R.layout.activity_campus_info);

        // Initialize the DatabaseHelper
        dbHelper = new DatabaseHelper(this);


        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Campus Locations");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true); // Enable back button
        }

        // Initialize Navigation UI
        Spinner spinnerFrom = findViewById(R.id.spinnerFrom);
        Spinner spinnerTo = findViewById(R.id.spinnerTo);
        Button btnNavigate = findViewById(R.id.btnNavigate);
        Button btnViewFloorPlan = findViewById(R.id.btnViewFloorPlan);
        TextView txtRoute = findViewById(R.id.txtRoute);

        // Initialize Favourite Buttons
        Button btnFavLibrary = findViewById(R.id.btnFavLibrary);
        Button btnFavIT = findViewById(R.id.btnFavIT);
        Button btnFavCanteen = findViewById(R.id.btnFavCanteen);
        Button btnFavApplied = findViewById(R.id.btnFavApplied);
        Button btnFavMainLib = findViewById(R.id.btnFavMainLib);

        // Populate Spinners with Campus Locations
        String[] locations = {
                "Main Library",
                "Main Building",
                "IT Faculty Building",
                "Student Canteen",
                "Faculty of Applied Sciences"
        };
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, locations);
        spinnerFrom.setAdapter(adapter);
        spinnerTo.setAdapter(adapter);

        // Listener to show/hide "View Floor Plan" button
        spinnerTo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String toLocation = locations[position];
                if (toLocation.equals("Main Library") || toLocation.equals("IT Faculty Building") ||
                        toLocation.equals("Faculty of Applied Sciences") || toLocation.equals("Main Building")) {
                    btnViewFloorPlan.setVisibility(View.VISIBLE);
                } else {
                    btnViewFloorPlan.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                btnViewFloorPlan.setVisibility(View.GONE);
            }
        });

        // Handle View Floor Plan Click
        btnViewFloorPlan.setOnClickListener(v -> {
            String toLocation = spinnerTo.getSelectedItem().toString();
            Intent intent = new Intent(CampusInfoActivity.this, IndoorMapActivity.class);
            intent.putExtra("LOCATION_NAME", toLocation);
            startActivity(intent);
        });

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

        // ==========================================
        // FAVOURITE BUTTONS CLICK LISTENERS
        // ==========================================

        // 1. 1st Main Library Button Click
        btnFavLibrary.setOnClickListener(v -> {
            saveToFavourites("1. Main Library", "Location: Next to the Faculty of Science.\nOperating Hours: 8:00 AM - 7:00 PM.");
        });

        // 2. IT Faculty Building Button Click
        btnFavIT.setOnClickListener(v -> {
            saveToFavourites("2. IT Faculty Building", "Location: Behind the main auditorium. Follow the second left path from the gate.");
        });

        // 3. Student Canteen Button Click
        btnFavCanteen.setOnClickListener(v -> {
            saveToFavourites("3. Student Canteen", "Location: Near the playground. Open for Breakfast and Lunch.");
        });

        // 4. Faculty of Applied Sciences Button Click
        btnFavApplied.setOnClickListener(v -> {
            saveToFavourites("Faculty of Applied Sciences", "Location: Near the Main Entrance, Mihintale.\nDepartments: Biological & Physical Sciences.");
        });

        // 5. 2nd Main Library Button Click
        btnFavMainLib.setOnClickListener(v -> {
            saveToFavourites("Main Library", "Location: Centrally located near Humanities Faculty.\nOpen: Weekdays 8 AM - 6 PM.");
        });
    }


    private void saveToFavourites(String name, String description) {
        boolean isInserted = dbHelper.addFavourite(name, description);
        if (isInserted) {
            Toast.makeText(CampusInfoActivity.this, name + " Added to Favourites!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(CampusInfoActivity.this, "Failed to add to Favourites", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        // Handle the back button click to return to the previous activity (OnBackPressedDispatcher)
        onBackPressed();
        return true;
    }
}