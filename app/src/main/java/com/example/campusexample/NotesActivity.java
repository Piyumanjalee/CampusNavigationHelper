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


        etNoteTitle = findViewById(R.id.etNoteTitle);
        etNoteContent = findViewById(R.id.etNoteContent);
        btnSaveNote = findViewById(R.id.btnSaveNote);

        // Check if we are in Edit Mode
        boolean isEdit = getIntent().getBooleanExtra("IS_EDIT", false);
        int noteId = getIntent().getIntExtra("NOTE_ID", -1);

        if (isEdit) {
            android.widget.TextView tvNoteHeader = findViewById(R.id.tvNoteHeader);
            if (tvNoteHeader != null) {
                tvNoteHeader.setText("Edit Note");
            }
            etNoteTitle.setText(getIntent().getStringExtra("NOTE_TITLE"));
            etNoteContent.setText(getIntent().getStringExtra("NOTE_CONTENT"));
            btnSaveNote.setText("UPDATE NOTE");
        }

        btnSaveNote.setOnClickListener(v -> {
            String title = etNoteTitle.getText().toString().trim();
            String content = etNoteContent.getText().toString().trim();

            if (title.isEmpty() || content.isEmpty()) {
                Toast.makeText(this, "Please write something before saving!", Toast.LENGTH_SHORT).show();
            } else {
                boolean isSaved;
                if (isEdit) {
                    isSaved = dbHelper.updateNote(noteId, title, content);
                } else {
                    isSaved = dbHelper.addNote(title, content);
                }

                if (isSaved) {
                    Toast.makeText(this, isEdit ? "Note updated successfully!" : "Note saved successfully!", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(this, isEdit ? "Failed to update note" : "Failed to save note", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}