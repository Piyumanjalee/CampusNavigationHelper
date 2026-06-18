package com.example.campusexample;

import android.database.Cursor;
import android.os.Bundle;
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
    ArrayList<Note> notesList;
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
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadNotes();
    }

    private void loadNotes() {
        notesList.clear();
        Cursor cursor = dbHelper.getAllNotes();

        if (cursor.getCount() == 0) {
            Toast.makeText(this, "No notes found!", Toast.LENGTH_SHORT).show();
            NotesAdapter adapter = new NotesAdapter(this, notesList);
            listViewNotes.setAdapter(adapter);
        } else {
            while (cursor.moveToNext()) {
                int id = cursor.getInt(0); // note_id column
                String title = cursor.getString(1); // Title column
                String content = cursor.getString(2); // Content column
                notesList.add(new Note(id, title, content));
            }
            // ListView adapter
            NotesAdapter adapter = new NotesAdapter(this, notesList);
            listViewNotes.setAdapter(adapter);
        }
        cursor.close();
    }
}