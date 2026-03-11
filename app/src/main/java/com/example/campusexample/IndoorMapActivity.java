package com.example.campusexample;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class IndoorMapActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_indoor_map);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Indoor Floor Plan");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        TextView tvLocationName = findViewById(R.id.tvLocationName);
        TextView tvFloorDetails = findViewById(R.id.tvFloorDetails);
        ImageView ivFloorPlan = findViewById(R.id.ivFloorPlan);

        // Retrieve location name from Intent
        String locationName = getIntent().getStringExtra("LOCATION_NAME");

        if (locationName != null) {
            tvLocationName.setText(locationName);
            String floorDetails = "";

            // Display appropriate floor plan details based on location
            if (locationName.equals("Main Library")) {
                floorDetails = "• Ground Floor: Reading Area\n• 1st Floor: Study Area\n• 2nd Floor: Lab";
                
                int resId = getResources().getIdentifier("floor_plan_library", "drawable", getPackageName());
                if (resId != 0) {
                     ivFloorPlan.setImageResource(resId);
                } else {
                     ivFloorPlan.setImageResource(R.mipmap.ic_launcher); // Fallback image
                }

            } else if (locationName.equals("Main Building")) {
                floorDetails = "• Ground Floor: Chemistry and Bio Labs\n• 1st Floor: Lecture Halls\n• 2nd Floor: Auditorium";
                ivFloorPlan.setImageResource(R.mipmap.ic_launcher);

            } else if (locationName.equals("Faculty of Applied Sciences")) {
                floorDetails = "• Ground Floor: AR Office\n• 1st Floor: Lectures Office";
                ivFloorPlan.setImageResource(R.mipmap.ic_launcher);

            } else if (locationName.equals("IT Faculty Building")) {
                floorDetails = "• Ground Floor: Lecture Halls\n• 1st Floor: Labs";
                
                int resId = getResources().getIdentifier("floor_plan_it_faculty", "drawable", getPackageName());
                if (resId != 0) {
                     ivFloorPlan.setImageResource(resId);
                } else {
                     ivFloorPlan.setImageResource(R.mipmap.ic_launcher); // Fallback image
                }
            } else {
                 floorDetails = "Floor plan not available for " + locationName;
                 ivFloorPlan.setImageResource(R.mipmap.ic_launcher); // Fallback image
            }
            
            tvFloorDetails.setText(floorDetails);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
