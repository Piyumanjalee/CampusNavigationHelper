package com.example.campusnavigationhelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Database විස්තර
    private static final String DATABASE_NAME = "CampusNav.db";
    private static final int DATABASE_VERSION = 1;

    // 1. User Table එකේ විස්තර
    public static final String TABLE_USERS = "users";
    public static final String COL_USER_ID = "id";
    public static final String COL_USER_NAME = "username";
    public static final String COL_USER_EMAIL = "email";
    public static final String COL_USER_PASSWORD = "password";

    // 2. Locations Table එකේ විස්තර
    public static final String TABLE_LOCATIONS = "locations";
    public static final String COL_LOC_ID = "loc_id";
    public static final String COL_LOC_NAME = "name";
    public static final String COL_LOC_DESCRIPTION = "description";

    // 3. Favorites Table එකේ විස්තර (Foreign Keys සහිතව) [cite: 132]
    public static final String TABLE_FAVORITES = "favorites";
    public static final String COL_FAV_ID = "fav_id";
    public static final String COL_FAV_USER_ID = "user_id";
    public static final String COL_FAV_LOC_ID = "loc_id";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Users table එක සෑදීම
        db.execSQL("CREATE TABLE " + TABLE_USERS + " (" +
                COL_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_USER_NAME + " TEXT, " +
                COL_USER_EMAIL + " TEXT, " +
                COL_USER_PASSWORD + " TEXT)");

        // Locations table එක සෑදීම
        db.execSQL("CREATE TABLE " + TABLE_LOCATIONS + " (" +
                COL_LOC_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_LOC_NAME + " TEXT, " +
                COL_LOC_DESCRIPTION + " TEXT)");

        // Favorites table එක සෑදීම (පරිශීලකයාට අදාළ දත්ත පමණක් පෙන්වීමට filtering සදහා) [cite: 132, 134]
        db.execSQL("CREATE TABLE " + TABLE_FAVORITES + " (" +
                COL_FAV_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_FAV_USER_ID + " INTEGER, " +
                COL_FAV_LOC_ID + " INTEGER, " +
                "FOREIGN KEY(" + COL_FAV_USER_ID + ") REFERENCES " + TABLE_USERS + "(" + COL_USER_ID + "), " +
                "FOREIGN KEY(" + COL_FAV_LOC_ID + ") REFERENCES " + TABLE_LOCATIONS + "(" + COL_LOC_ID + "))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOCATIONS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FAVORITES);
        onCreate(db);
    }
}