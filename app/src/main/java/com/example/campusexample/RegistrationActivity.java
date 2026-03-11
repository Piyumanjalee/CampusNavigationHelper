package com.example.campusexample; // Correct package based on your folder

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

// Classes needed for Hashing and Regex
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Pattern;

public class RegistrationActivity extends AppCompatActivity {

    // 1. Declare UI variables and DatabaseHelper
    EditText etRegName, etRegEmail, etRegPassword, etRegConfirmPassword;
    Button btnRegister;
    TextView tvLoginLink;
    DatabaseHelper dbHelper;

    // 2. Regex Pattern for a Strong Password
    // Requirements: Minimum 8 characters, at least 1 uppercase, 1 lowercase, 1 number, and 1 special character.
    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    "(?=.*[0-9])" +         // at least 1 digit
                    "(?=.*[a-z])" +         // at least 1 lower case letter
                    "(?=.*[A-Z])" +         // at least 1 upper case letter
                    "(?=.*[@#$%^&+=!])" +   // at least 1 special character
                    "(?=\\S+$)" +           // no white spaces allowed
                    ".{8,}" +               // at least 8 characters
                    "$");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        // Initialize DatabaseHelper
        dbHelper = new DatabaseHelper(this);

        // 3. Initialize UI elements from layout
        etRegName = findViewById(R.id.etRegName);
        etRegEmail = findViewById(R.id.etRegEmail);
        etRegPassword = findViewById(R.id.etRegPassword);
        etRegConfirmPassword = findViewById(R.id.etRegConfirmPassword);
        btnRegister = findViewById(R.id.btnRegister);
        tvLoginLink = findViewById(R.id.tvLoginLink);

        // 4. Set Click Listener for Register Button
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etRegName.getText().toString().trim();
                String email = etRegEmail.getText().toString().trim();
                String password = etRegPassword.getText().toString().trim();
                String confirmPassword = etRegConfirmPassword.getText().toString().trim();

                // Validation Step 1: Check if any field is empty
                if (name.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                    Toast.makeText(RegistrationActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                    return; // Stop execution if validation fails
                }

                // Validation Step 2: Validate Email format
                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    etRegEmail.setError("Please enter a valid email address");
                    etRegEmail.requestFocus();
                    return;
                }

                // Validation Step 3: Validate Password strength
                if (!PASSWORD_PATTERN.matcher(password).matches()) {
                    etRegPassword.setError("Password is too weak. Need 8+ characters, including upper/lowercase, numbers and symbols.");
                    etRegPassword.requestFocus();
                    return;
                }

                // Validation Step 4: Check if passwords match
                if (!password.equals(confirmPassword)) {
                    etRegConfirmPassword.setError("Passwords do not match");
                    etRegConfirmPassword.requestFocus();
                    return;
                }

                // Validation Step 5: Hash the password (SHA-256) before saving to database
                String hashedPassword = hashPassword(password);

                if (hashedPassword == null) {
                    Toast.makeText(RegistrationActivity.this, "Error in hashing password", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Final Step: Insert data into SQLite Database
                boolean isInserted = dbHelper.addUser(name, email, hashedPassword);

                if (isInserted) {
                    Toast.makeText(RegistrationActivity.this, "Registration Successful!", Toast.LENGTH_SHORT).show();
                    // Redirect to Login Page
                    Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish(); // Close registration activity
                } else {
                    Toast.makeText(RegistrationActivity.this, "Registration Failed! Email might already exist.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Click Listener for navigating to Login Page
        tvLoginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    // Utility Method to Hash the Password using SHA-256 Algorithm
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