package com.example.campusexample;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class ForgotPasswordActivity extends AppCompatActivity {

    EditText etForgotEmail, etForgotDOB, etNewPassword;
    Button btnResetPassword;
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        dbHelper = new DatabaseHelper(this);

        etForgotEmail = findViewById(R.id.etForgotEmail);
        etForgotDOB = findViewById(R.id.etForgotDOB);
        etNewPassword = findViewById(R.id.etNewPassword);
        btnResetPassword = findViewById(R.id.btnResetPassword);

        btnResetPassword.setOnClickListener(v -> {
            String email = etForgotEmail.getText().toString().trim();
            String dob = etForgotDOB.getText().toString().trim();
            String newPassword = etNewPassword.getText().toString().trim();

            // 1. Basic Validation
            if (email.isEmpty() || dob.isEmpty() || newPassword.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            // 2. Verify Email and Date of Birth from Database
            if (dbHelper.checkSecurityAnswer(email, dob)) {

                // 3. Hash the new password before updating
                String hashedNewPassword = hashPassword(newPassword);

                if (hashedNewPassword != null) {
                    // 4. Update the password in Database
                    boolean isUpdated = dbHelper.updatePassword(email, hashedNewPassword);
                    if (isUpdated) {
                        Toast.makeText(this, "Password reset successfully!", Toast.LENGTH_LONG).show();
                        finish(); // Return to Login Screen
                    } else {
                        Toast.makeText(this, "Failed to update password", Toast.LENGTH_SHORT).show();
                    }
                }
            } else {
                Toast.makeText(this, "Email or Birthday does not match our records", Toast.LENGTH_LONG).show();
            }
        });
    }

    // SHA-256 Hashing Method (Must be same as Registration)
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