package com.example.campusexample;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class FavouritesActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper;
    private ListView listViewFavourites;
    private ArrayList<String> favList;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites);

        // Action bar title and back button
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("My Favourites");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        dbHelper = new DatabaseHelper(this);
        listViewFavourites = findViewById(R.id.listViewFavourites);
        favList = new ArrayList<>();

        // Favourites load
        loadFavouritesData();
    }

    private void loadFavouritesData() {
        Cursor cursor = dbHelper.getAllFavourites();

        if (cursor.getCount() == 0) {
            Toast.makeText(this, "No Favourites added yet!", Toast.LENGTH_SHORT).show();
        } else {
            // Read every row in the database
            while (cursor.moveToNext()) {
                // Column index 1 = fav_name, Column index 2 = fav_description
                String name = cursor.getString(1);
                String description = cursor.getString(2);

                // Add to list
                favList.add(name + "\n" + description);
            }

            // ListView adapter
            adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, favList);
            listViewFavourites.setAdapter(adapter);
        }
        cursor.close();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}