package com.example.campusexample; // Consistent with your folder structure

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Database constants
    private static final String DATABASE_NAME = "CampusNav.db";
    // Incremented version to 3 to apply schema changes (adding DOB column)
    private static final int DATABASE_VERSION = 3;

    // Table and Column names
    private static final String TABLE_USERS = "users";
    private static final String COL_ID = "id";
    private static final String COL_NAME = "name";
    private static final String COL_EMAIL = "email";
    private static final String COL_PASSWORD = "password";
    private static final String COL_DOB = "dob"; // New column for Security Question (Birthday)

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create table with Name, Email, Password, and DOB columns
        String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS + "("
                + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COL_NAME + " TEXT,"
                + COL_EMAIL + " TEXT UNIQUE,"
                + COL_PASSWORD + " TEXT,"
                + COL_DOB + " TEXT" + ")"; // Added DOB column to schema
        db.execSQL(CREATE_USERS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed and create a fresh one
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(db);
    }

    // UPDATED: Now accepts name, email, password, and DOB
    public boolean addUser(String name, String email, String password, String dob) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COL_NAME, name);
        values.put(COL_EMAIL, email);
        values.put(COL_PASSWORD, password); // Hashed password from RegistrationActivity
        values.put(COL_DOB, dob); // Save birthday as security answer

        long result = db.insert(TABLE_USERS, null, values);
        return result != -1;
    }

    // NEW METHOD: Verify if Email and Birthday match for Password Reset
    public boolean checkSecurityAnswer(String email, String dob) {
        SQLiteDatabase db = this.getReadableDatabase();

        // Query to find a user where both email and birthday match
        String query = "SELECT * FROM " + TABLE_USERS + " WHERE " + COL_EMAIL + " = ? AND " + COL_DOB + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{email, dob});

        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    // NEW METHOD: Update the password for a specific email
    public boolean updatePassword(String email, String newHashedPassword) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_PASSWORD, newHashedPassword);

        // Update row where email matches
        int result = db.update(TABLE_USERS, values, COL_EMAIL + " = ?", new String[]{email});
        return result > 0;
    }

    // Method to check user credentials for login
    public boolean checkUser(String email, String hashedPassword) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_USERS + " WHERE " + COL_EMAIL + " = ? AND " + COL_PASSWORD + " = ?";
        String[] selectionArgs = {email, hashedPassword};

        Cursor cursor = db.rawQuery(query, selectionArgs);
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }
}