package com.example.campusexample; // Correct package based on your folder

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

// Classes needed for Hashing
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class LoginActivity extends AppCompatActivity {

    // 1. Declare UI variables and DatabaseHelper
    TextView tvRegisterLink;
    EditText etLoginEmail, etLoginPassword;
    Button btnLogin;
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize DatabaseHelper
        dbHelper = new DatabaseHelper(this);

        // 2. Initialize UI elements from layout
        tvRegisterLink = findViewById(R.id.tvRegisterLink);
        etLoginEmail = findViewById(R.id.etLoginEmail);
        etLoginPassword = findViewById(R.id.etLoginPassword);
        btnLogin = findViewById(R.id.btnLogin);

        // 3. Set Click Listener for Login Button
        btnLogin.setOnClickListener(v -> {
            String email = etLoginEmail.getText().toString().trim();
            String password = etLoginPassword.getText().toString().trim();

            // Validation: Check if fields are empty
            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            } else {
                // IMPORTANT: Hash the entered password before checking it against the database
                String hashedPassword = hashPassword(password);

                if (hashedPassword == null) {
                    Toast.makeText(this, "Error processing password", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Check credentials in SQLite Database
                if (dbHelper.checkUser(email, hashedPassword)) {
                    Toast.makeText(this, "Login Successful!", Toast.LENGTH_SHORT).show();
                    // Redirect to Main Dashboard
                    startActivity(new Intent(this, MainActivity.class));
                    finish(); // Close login activity
                } else {
                    Toast.makeText(this, "Invalid Email or Password", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Click Listener for navigating to Registration Page
        tvRegisterLink.setOnClickListener(v -> {
            startActivity(new Intent(this, RegistrationActivity.class));
            // finish() is not called here so user can press back button to return to login
        });
    }

    // Utility Method to Hash the Password using SHA-256 Algorithm (Must be identical to Registration)
    private String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();

            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
}