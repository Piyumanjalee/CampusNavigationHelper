package com.example.campusexample;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class FavouritesActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper;
    private ListView listViewFavourites;
    private ArrayList<Favourite> favList;

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
        favList.clear();
        Cursor cursor = dbHelper.getAllFavourites();

        if (cursor.getCount() == 0) {
            Toast.makeText(this, "No Favourites added yet!", Toast.LENGTH_SHORT).show();
            FavouritesAdapter adapter = new FavouritesAdapter(this, favList);
            listViewFavourites.setAdapter(adapter);
        } else {
            // Read every row in the database
            while (cursor.moveToNext()) {
                // Column index 0 = fav_id, Column index 1 = fav_name, Column index 2 = fav_description
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                String description = cursor.getString(2);

                // Add to list
                favList.add(new Favourite(id, name, description));
            }

            // ListView adapter
            FavouritesAdapter adapter = new FavouritesAdapter(this, favList);
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