package com.example.campusexample;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Database constants
    private static final String DATABASE_NAME = "CampusNav.db";
    // Incremented to 4 to add the new 'notes' table
    private static final int DATABASE_VERSION = 4;

    // 1. Table and Column names for USERS
    private static final String TABLE_USERS = "users";
    private static final String COL_ID = "id";
    private static final String COL_NAME = "name";
    private static final String COL_EMAIL = "email";
    private static final String COL_PASSWORD = "password";
    private static final String COL_DOB = "dob";

    // 2. Table and Column names for NOTES
    private static final String TABLE_NOTES = "notes";
    private static final String COL_NOTE_ID = "note_id";
    private static final String COL_NOTE_TITLE = "title";
    private static final String COL_NOTE_CONTENT = "content";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create USERS table
        String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS + "("
                + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COL_NAME + " TEXT,"
                + COL_EMAIL + " TEXT UNIQUE,"
                + COL_PASSWORD + " TEXT,"
                + COL_DOB + " TEXT" + ")";
        db.execSQL(CREATE_USERS_TABLE);

        // Create NOTES table
        String CREATE_NOTES_TABLE = "CREATE TABLE " + TABLE_NOTES + "("
                + COL_NOTE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COL_NOTE_TITLE + " TEXT,"
                + COL_NOTE_CONTENT + " TEXT" + ")";
        db.execSQL(CREATE_NOTES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older tables if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTES);
        onCreate(db);
    }

    // --- USER METHODS ---

    public boolean addUser(String name, String email, String password, String dob) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_NAME, name);
        values.put(COL_EMAIL, email);
        values.put(COL_PASSWORD, password);
        values.put(COL_DOB, dob);

        long result = db.insert(TABLE_USERS, null, values);
        return result != -1;
    }

    public boolean checkSecurityAnswer(String email, String dob) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_USERS + " WHERE " + COL_EMAIL + " = ? AND " + COL_DOB + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{email, dob});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    public boolean updatePassword(String email, String newHashedPassword) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_PASSWORD, newHashedPassword);
        int result = db.update(TABLE_USERS, values, COL_EMAIL + " = ?", new String[]{email});
        return result > 0;
    }

    public boolean checkUser(String email, String hashedPassword) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_USERS + " WHERE " + COL_EMAIL + " = ? AND " + COL_PASSWORD + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{email, hashedPassword});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    // --- NOTE METHODS ---

    // NEW: Method to add a note to the database
    public boolean addNote(String title, String content) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_NOTE_TITLE, title);
        values.put(COL_NOTE_CONTENT, content);

        long result = db.insert(TABLE_NOTES, null, values);
        return result != -1; // returns true if success
    }
}