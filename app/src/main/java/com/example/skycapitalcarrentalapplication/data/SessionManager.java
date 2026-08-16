package com.example.skycapitalcarrentalapplication.data;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Tracks the currently logged-in user by email in SharedPreferences.
 * Set on a successful login/sign-up, cleared on logout.
 */
public class SessionManager {

    private static final String PREFS = "session_prefs";
    private static final String KEY_EMAIL = "logged_in_email";

    private final SharedPreferences prefs;

    public SessionManager(Context context) {
        prefs = context.getApplicationContext().getSharedPreferences(PREFS, Context.MODE_PRIVATE);
    }

    public void setLoggedIn(String email) {
        prefs.edit().putString(KEY_EMAIL, email).apply();
    }

    public String getEmail() {
        return prefs.getString(KEY_EMAIL, null);
    }

    public boolean isLoggedIn() {
        return getEmail() != null;
    }

    public void logout() {
        prefs.edit().remove(KEY_EMAIL).apply();
    }
}
