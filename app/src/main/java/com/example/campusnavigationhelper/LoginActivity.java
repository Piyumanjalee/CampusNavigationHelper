package com.example.campusnavigationhelper;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    EditText etEmail, etPassword;
    Button btnLogin;
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etEmail = findViewById(R.id.etLoginEmail);
        etPassword = findViewById(R.id.etLoginPassword);
        btnLogin = findViewById(R.id.btnLogin);
        dbHelper = new DatabaseHelper(this);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etEmail.getText().toString();
                String pass = etPassword.getText().toString();

                if(email.isEmpty() || pass.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Please enter credentials", Toast.LENGTH_SHORT).show();
                } else {
                    if(checkUser(email, pass)) {
                        Toast.makeText(LoginActivity.this, "Login Successful!", Toast.LENGTH_SHORT).show();
                        // සාර්ථක නම් මෙතැනදී යූසර්ව මීළඟ පිටුවට යැවිය හැකියි
                    } else {
                        Toast.makeText(LoginActivity.this, "Invalid Email or Password", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private boolean checkUser(String email, String pass) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        // ඊයේ කළ ආකාරයටම password එක hash කර සසඳන්න
        String hashedPassword = String.valueOf(pass.hashCode());

        String query = "SELECT * FROM " + DatabaseHelper.TABLE_USERS +
                " WHERE " + DatabaseHelper.COL_USER_EMAIL + " = ? AND " +
                DatabaseHelper.COL_USER_PASSWORD + " = ?";

        Cursor cursor = db.rawQuery(query, new String[]{email, hashedPassword});
        boolean exists = (cursor.getCount() > 0);
        cursor.close();
        return exists;
    }
}