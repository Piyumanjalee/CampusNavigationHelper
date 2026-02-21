package com.example.campusnavigationhelper;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    // Declare view variables
    TextView tvRegisterLink;
    EditText etLoginEmail, etLoginPassword;
    Button btnLogin;

    // 1. Declare DatabaseHelper
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // 2. Initialize DatabaseHelper
        dbHelper = new DatabaseHelper(this);

        // Initialize views
        tvRegisterLink = findViewById(R.id.tvRegisterLink);
        etLoginEmail = findViewById(R.id.etLoginEmail);
        etLoginPassword = findViewById(R.id.etLoginPassword);
        btnLogin = findViewById(R.id.btnLogin);

        // Set Click Listener for Login Button
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etLoginEmail.getText().toString().trim();
                String password = etLoginPassword.getText().toString().trim();

                // Basic Validation
                if (email.isEmpty()) {
                    etLoginEmail.setError("Email is required");
                } else if (password.isEmpty()) {
                    etLoginPassword.setError("Password is required");
                } else {
                    // 3. ACTUAL Database Validation
                    boolean isValid = dbHelper.checkUser(email, password);

                    if (isValid) {
                        Toast.makeText(LoginActivity.this, "Login Successful!", Toast.LENGTH_SHORT).show();

                        // Navigate to MainActivity
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        // If user doesn't exist or wrong password
                        Toast.makeText(LoginActivity.this, "Invalid Email or Password", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        // Set Click Listener for Register Link
        tvRegisterLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
                startActivity(intent);
            }
        });
    }
}
