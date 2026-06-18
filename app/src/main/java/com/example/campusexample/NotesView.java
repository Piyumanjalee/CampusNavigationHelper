package com.example.campusexample;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class NotesView extends AppCompatActivity {

    // UI and Database components
    ListView listViewNotes;
    ArrayList<String> notesList;
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_notes_view);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize components
        dbHelper = new DatabaseHelper(this);
        listViewNotes = findViewById(R.id.listViewNotes);
        notesList = new ArrayList<>();

        // Load notes
        loadNotes();
    }

    private void loadNotes() {
        Cursor cursor = dbHelper.getAllNotes();

        if (cursor.getCount() == 0) {
            Toast.makeText(this, "No notes found!", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                String title = cursor.getString(1); // Title column
                String content = cursor.getString(2); // Content column
                notesList.add(title + ": \n" + content);
            }
            // ListView adapter
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.list_item_card, R.id.text1, notesList);
            listViewNotes.setAdapter(adapter);
        }
        cursor.close();
    }
}