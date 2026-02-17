package com.example.campusnavigationhelper;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

public class MainActivity extends AppCompatActivity {

    EditText etUsername, etEmail, etPassword;
    Button btnRegister;
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etUsername = findViewById(R.id.etUsername);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnRegister = findViewById(R.id.btnRegister);
        dbHelper = new DatabaseHelper(this);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etUsername.getText().toString();
                String email = etEmail.getText().toString();
                String pass = etPassword.getText().toString();

                if(name.isEmpty() || email.isEmpty() || pass.isEmpty()){
                    Toast.makeText(MainActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                } else {
                    saveUser(name, email, pass);
                }
            }
        });
    }

    private void saveUser(String name, String email, String pass) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COL_USER_NAME, name);
        values.put(DatabaseHelper.COL_USER_EMAIL, email);

        // සරල Encryption එකක් ලෙස පාස්වර්ඩ් එක hash කිරීම මෙහිදී කළ යුතුයි
        values.put(DatabaseHelper.COL_USER_PASSWORD, String.valueOf(pass.hashCode()));

        long id = db.insert(DatabaseHelper.TABLE_USERS, null, values);
        if (id != -1) {
            Toast.makeText(this, "Registration Successful!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Registration Failed!", Toast.LENGTH_SHORT).show();
        }
    }
}