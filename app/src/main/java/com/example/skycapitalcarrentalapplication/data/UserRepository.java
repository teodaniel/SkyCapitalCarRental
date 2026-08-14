package com.example.skycapitalcarrentalapplication.data;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.example.skycapitalcarrentalapplication.data.dao.UserDao;
import com.example.skycapitalcarrentalapplication.data.entity.UserEntity;
import com.example.skycapitalcarrentalapplication.utils.PasswordHasher;

import java.util.Locale;
import java.util.concurrent.ExecutorService;

/**
 * Handles registration and login.
 * All DB work runs on Room
 * results are posted back to the main thread via the callback.
 */
public class UserRepository {

    public interface AuthCallback {
        void onResult(boolean success, String message);
    }

    private final UserDao userDao;
    private final ExecutorService executor;
    private final Handler mainHandler = new Handler(Looper.getMainLooper());

    public UserRepository(Context context) {
        AppDatabase db = AppDatabase.getInstance(context);
        this.userDao = db.userDao();
        this.executor = AppDatabase.getDbExecutor();
    }

    public void register(String email, String password, AuthCallback callback) {
        executor.execute(() -> {
            String normalized = normalize(email);
            if (normalized.isEmpty() || password == null || password.isEmpty()) {
                post(callback, false, "Email and password are required");
                return;
            }
            if (userDao.countByEmail(normalized) > 0) {
                post(callback, false, "That email is already registered");
                return;
            }
            userDao.insert(new UserEntity(normalized, PasswordHasher.hash(password)));
            post(callback, true, "Account created");
        });
    }

    public void login(String email, String password, AuthCallback callback) {
        executor.execute(() -> {
            UserEntity user = userDao.findByEmail(normalize(email));
            boolean ok = user != null && PasswordHasher.verify(password, user.passwordHash);
            post(callback, ok, ok ? "Welcome back" : "Invalid email or password");
        });
    }

    private String normalize(String email) {
        return email == null ? "" : email.trim().toLowerCase(Locale.getDefault());
    }

    private void post(AuthCallback cb, boolean success, String message) {
        mainHandler.post(() -> cb.onResult(success, message));
    }
}