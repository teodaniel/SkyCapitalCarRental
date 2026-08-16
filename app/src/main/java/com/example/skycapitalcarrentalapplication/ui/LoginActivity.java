package com.example.skycapitalcarrentalapplication.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.skycapitalcarrentalapplication.R;
import com.example.skycapitalcarrentalapplication.data.SessionManager;
import com.example.skycapitalcarrentalapplication.data.UserRepository;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

/**
 * Login / sign-up screen.
 *
 * <p>
 *     Authenticates against the Room users table via {@link UserRepository}
 * (passwords are BCrypt-hashed) :).
 * </p>
 * <p>
 *     The same email/password fields drive both actions:
 * </p>
 * <p>
 *     "Login" verifies an existing account, "Sign Up" creates a new one.
 * </p>
 * <p>
 *     Sign-up checks that the email isn't already registered.
 * </p>
 */
public class LoginActivity extends AppCompatActivity {

    private TextInputLayout emailInputLayout;
    private TextInputEditText editTextEmailAddress;
    private TextInputEditText editTextPassword;
    private MaterialButton buttonLogin;
    private MaterialButton buttonSignup;
    private UserRepository userRepository;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userRepository = new UserRepository(this);

        emailInputLayout = findViewById(R.id.emailInputLayout);
        editTextEmailAddress = findViewById(R.id.editTextEmailAddress);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.buttonLogin);
        buttonSignup = findViewById(R.id.buttonSignup);

        // Clear email errors as soon as the user edits the field.
        editTextEmailAddress.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int a, int b, int c) { }
            @Override public void onTextChanged(CharSequence s, int a, int b, int c) { }
            @Override public void afterTextChanged(Editable s) {
                emailInputLayout.setError(null);
            }
        });

        buttonLogin.setOnClickListener(view -> attemptLogin());
        buttonSignup.setOnClickListener(view -> attemptSignup());
    }

    private void attemptLogin() {
        String email = textOf(editTextEmailAddress);
        String password = textOf(editTextPassword);
        if (!validate(email, password)) return;

        setInProgress(true);
        userRepository.login(email, password, (success, message) -> {
            setInProgress(false);
            toast(message);
            if (success) {
                new SessionManager(this).setLoggedIn(email);
                goToMain();
            }
        });
    }

    private void attemptSignup() {
        String email = textOf(editTextEmailAddress);
        String password = textOf(editTextPassword);
        if (!validate(email, password)) return;
        if (password.length() < 6) {
            toast("Password must be at least 6 characters");
            return;
        }

        setInProgress(true);
        // First check the email isn't taken, then register.
        userRepository.emailExists(email, exists -> {
            if (exists) {
                setInProgress(false);
                emailInputLayout.setError("That email is already registered");
                return;
            }
            userRepository.register(email, password, (success, message) -> {
                setInProgress(false);
                toast(message);
                if (success) {
                    new SessionManager(this).setLoggedIn(email);
                    goToMain();
                }
            });
        });
    }

    private void goToMain() {
        Intent intent = new Intent(this, MainActivity.class);
        // Clear the back stack so Back doesn't return to the login screen.
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    /** Disable buttons while an auth request is pending to stop multiple clicks. */
    private void setInProgress(boolean inProgress) {
        buttonLogin.setEnabled(!inProgress);
        buttonSignup.setEnabled(!inProgress);
    }


    /** field validation for both actions. */
    private boolean validate(String email, String password) {
        if (email.isEmpty()) {
            emailInputLayout.setError("Please fill in your email");
            return false;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailInputLayout.setError("Please enter a valid email");
            return false;
        }
        if (password.isEmpty()) {
            toast("Please fill in your password");
            return false;
        }
        return true;
    }


    private String textOf(TextInputEditText field) {
        return Objects.requireNonNull(field.getText()).toString().trim();
    }

    private void toast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}