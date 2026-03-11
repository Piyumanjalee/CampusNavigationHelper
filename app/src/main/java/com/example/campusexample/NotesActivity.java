package com.example.campusexample;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class NotesActivity extends AppCompatActivity {

    EditText etNoteTitle, etNoteContent;
    Button btnSaveNote;
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        dbHelper = new DatabaseHelper(this);

        // UI elements සම්බන්ධ කිරීම
        etNoteTitle = findViewById(R.id.etNoteTitle);
        etNoteContent = findViewById(R.id.etNoteContent);
        btnSaveNote = findViewById(R.id.btnSaveNote);

        btnSaveNote.setOnClickListener(v -> {
            String title = etNoteTitle.getText().toString().trim();
            String content = etNoteContent.getText().toString().trim();

            if (title.isEmpty() || content.isEmpty()) {
                Toast.makeText(this, "Please write something before saving!", Toast.LENGTH_SHORT).show();
            } else {
                // Database එකට සේව් කිරීම
                boolean isSaved = dbHelper.addNote(title, content);

                if (isSaved) {
                    Toast.makeText(this, "Note saved successfully!", Toast.LENGTH_SHORT).show();
                    finish(); // සේව් වුණාට පස්සේ Dashboard එකට ආපහු යනවා
                } else {
                    Toast.makeText(this, "Failed to save note", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}