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
import com.google.android.gms.maps.model.LatLng;


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
        Spinner spinnerCategory = findViewById(R.id.spinnerCategory);
        Spinner spinnerFrom = findViewById(R.id.spinnerFrom);
        Spinner spinnerTo = findViewById(R.id.spinnerTo);
        Button btnNavigate = findViewById(R.id.btnNavigate);
        Button btnViewFloorPlan = findViewById(R.id.btnViewFloorPlan);
        Button btnViewGoogleMap = findViewById(R.id.btnViewGoogleMap);
        TextView txtRoute = findViewById(R.id.txtRoute);

        // Initialize Favourite Buttons
        Button btnFavLibrary = findViewById(R.id.btnFavLibrary);
        Button btnFavIT = findViewById(R.id.btnFavIT);
        Button btnFavCanteen = findViewById(R.id.btnFavCanteen);
        Button btnFavApplied = findViewById(R.id.btnFavApplied);
        Button btnFavMainLib = findViewById(R.id.btnFavMainLib);

        // Defined Location Lists
        String[] categories = { "Campus Locations", "Mihintale Places (5km Radius)" };
        String[] campusLocations = {
                "Main Library",
                "Main Building",
                "IT Faculty Building",
                "Student Canteen",
                "Faculty of Applied Sciences"
        };
        String[] localPlaces = {
                "Mihintale Center (9G63+C8C)",
                "Mihintale Temple Complex",
                "Kaludiya Pokuna",
                "Ancient Hospital Ruins",
                "Kantaka Cetiya",
                "Aradhana Gala",
                "Mihintale Railway Station"
        };

        // Populate Category Spinner
        ArrayAdapter<String> catAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, categories);
        spinnerCategory.setAdapter(catAdapter);

        spinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    ArrayAdapter<String> fromAdapter = new ArrayAdapter<>(CampusInfoActivity.this, android.R.layout.simple_spinner_dropdown_item, campusLocations);
                    spinnerFrom.setAdapter(fromAdapter);
                    spinnerTo.setAdapter(fromAdapter);
                } else {
                    ArrayAdapter<String> fromAdapter = new ArrayAdapter<>(CampusInfoActivity.this, android.R.layout.simple_spinner_dropdown_item, localPlaces);
                    spinnerFrom.setAdapter(fromAdapter);
                    spinnerTo.setAdapter(fromAdapter);
                }
                btnViewFloorPlan.setVisibility(View.GONE);
                txtRoute.setVisibility(View.GONE);
                btnViewGoogleMap.setVisibility(View.GONE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        // Listener to show/hide "View Floor Plan" button
        spinnerTo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (spinnerTo.getSelectedItem() == null) return;
                String toLocation = spinnerTo.getSelectedItem().toString();
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

        // Handle View Google Map Click
        btnViewGoogleMap.setOnClickListener(v -> {
            String from = spinnerFrom.getSelectedItem().toString();
            String to = spinnerTo.getSelectedItem().toString();
            Intent intent = new Intent(CampusInfoActivity.this, GoogleMapActivity.class);
            intent.putExtra("FROM_LOCATION", from);
            intent.putExtra("TO_LOCATION", to);
            startActivity(intent);
        });

        // Handle Navigate Button Click
        btnNavigate.setOnClickListener(v -> {
            String from = spinnerFrom.getSelectedItem().toString();
            String to = spinnerTo.getSelectedItem().toString();

            if (from.equals(to)) {
                txtRoute.setText("You are already at the " + to + "!");
                txtRoute.setVisibility(View.VISIBLE);
                btnViewGoogleMap.setVisibility(View.GONE);
                return;
            }

            String routeText = calculateRealRoute(from, to);

            txtRoute.setText(routeText);
            txtRoute.setVisibility(View.VISIBLE);
            btnViewGoogleMap.setVisibility(View.VISIBLE);
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

    private String calculateRealRoute(String fromName, String toName) {
        LatLng start = getCoordinates(fromName);
        LatLng end = getCoordinates(toName);

        float[] results = new float[2];
        android.location.Location.distanceBetween(
                start.latitude, start.longitude,
                end.latitude, end.longitude,
                results
        );

        float distance = results[0]; // distance in meters
        float initialBearing = results[1]; // bearing in degrees

        // Get compass direction from bearing
        String direction = getCompassDirection(initialBearing);

        // Calculate estimated walking time (average walking speed is 1.4 m/s)
        int walkingTimeMinutes = Math.round((distance / 1.4f) / 60f);
        if (walkingTimeMinutes < 1) walkingTimeMinutes = 1;

        StringBuilder sb = new StringBuilder();
        sb.append("Real-World Route:\n");
        sb.append("• From: ").append(fromName).append("\n");
        sb.append("• To: ").append(toName).append("\n");
        sb.append("• Walking Distance: ").append(String.format("%.0f meters", distance)).append("\n");
        sb.append("• Direction: Head ").append(direction).append("\n");
        sb.append("• Est. Time: ~").append(walkingTimeMinutes).append(" min\n\n");
        sb.append("Real-world Directions:\n");
        sb.append("1. Orient your device towards ").append(direction).append(".\n");
        sb.append("2. Walk approx. ").append(String.format("%.0f meters", distance)).append(" to reach your destination.\n");
        sb.append("3. Tap 'VIEW GOOGLE MAP' below for dynamic turn-by-turn walking route guidance.");

        return sb.toString();
    }

    private String getCompassDirection(float bearing) {
        if (bearing < 0) {
            bearing += 360;
        }
        if (bearing >= 337.5 || bearing < 22.5) {
            return "North (N)";
        } else if (bearing >= 22.5 && bearing < 67.5) {
            return "North-East (NE)";
        } else if (bearing >= 67.5 && bearing < 112.5) {
            return "East (E)";
        } else if (bearing >= 112.5 && bearing < 157.5) {
            return "South-East (SE)";
        } else if (bearing >= 157.5 && bearing < 202.5) {
            return "South (S)";
        } else if (bearing >= 202.5 && bearing < 247.5) {
            return "South-West (SW)";
        } else if (bearing >= 247.5 && bearing < 292.5) {
            return "West (W)";
        } else {
            return "North-West (NW)";
        }
    }

    private LatLng getCoordinates(String name) {
        if (name == null) return new LatLng(8.3636, 80.5042);
        switch (name.trim()) {
            case "Mihintale Center (9G63+C8C)":
                return new LatLng(8.3585, 80.5020);
            case "Main Library":
            case "1. Main Library":
            case "5. Main Library":
                return new LatLng(8.3622, 80.5033);
            case "Main Building":
                return new LatLng(8.3625, 80.5022);
            case "IT Faculty Building":
            case "2. IT Faculty Building":
                return new LatLng(8.3644, 80.5028);
            case "Student Canteen":
            case "3. Student Canteen":
                return new LatLng(8.3628, 80.5048);
            case "Faculty of Applied Sciences":
            case "4. Faculty of Applied Sciences":
                return new LatLng(8.3608, 80.5042);
            case "Mihintale Temple Complex":
                return new LatLng(8.3512, 80.5167);
            case "Kaludiya Pokuna":
                return new LatLng(8.3422, 80.5133);
            case "Ancient Hospital Ruins":
                return new LatLng(8.3514, 80.5085);
            case "Kantaka Cetiya":
                return new LatLng(8.3508, 80.5140);
            case "Aradhana Gala":
                return new LatLng(8.3524, 80.5195);
            case "Mihintale Railway Station":
                return new LatLng(8.3562, 80.5046);
            default:
                return new LatLng(8.3636, 80.5042); // default campus center
        }
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