package com.example.campusexample;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Pattern; // Necessary for Regex validation

public class ForgotPasswordActivity extends AppCompatActivity {

    EditText etForgotEmail, etForgotDOB, etNewPassword;
    Button btnResetPassword;
    DatabaseHelper dbHelper;

    // 1. Strong Password Regex Pattern (Same as Registration for consistency)
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

            // 2. Check if any field is empty
            if (email.isEmpty() || dob.isEmpty() || newPassword.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            // 3. NEW: Validate Strong Password Requirement
            if (!PASSWORD_PATTERN.matcher(newPassword).matches()) {
                etNewPassword.setError("Password too weak! Need 8+ chars, upper/lower case, numbers and symbols.");
                etNewPassword.requestFocus();
                return;
            }

            // 4. Verify Email and Date of Birth
            if (dbHelper.checkSecurityAnswer(email, dob)) {

                // 5. Hash the new password
                String hashedNewPassword = hashPassword(newPassword);

                if (hashedNewPassword != null) {
                    // 6. Update in Database
                    boolean isUpdated = dbHelper.updatePassword(email, hashedNewPassword);
                    if (isUpdated) {
                        Toast.makeText(this, "Password reset successfully!", Toast.LENGTH_LONG).show();
                        finish();
                    } else {
                        Toast.makeText(this, "Failed to update password", Toast.LENGTH_SHORT).show();
                    }
                }
            } else {
                Toast.makeText(this, "Email or Birthday does not match our records", Toast.LENGTH_LONG).show();
            }
        });
    }

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