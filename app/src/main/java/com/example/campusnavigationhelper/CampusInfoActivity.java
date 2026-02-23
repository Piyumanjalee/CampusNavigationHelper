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


        setContentView(R.layout.activity_campus_info);


        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Campus Locations");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        Spinner spinnerFrom = findViewById(R.id.spinnerFrom);
        Spinner spinnerTo = findViewById(R.id.spinnerTo);
        Button btnNavigate = findViewById(R.id.btnNavigate);
        TextView txtRoute = findViewById(R.id.txtRoute);



        };
    }

    @Override
    public boolean onSupportNavigateUp() {
        // Handle the back button click to return to the previous activity
        onBackPressed();
        return true;
    }
}