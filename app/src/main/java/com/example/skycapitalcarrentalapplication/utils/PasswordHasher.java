package com.example.skycapitalcarrentalapplication.utils;


import org.mindrot.jbcrypt.BCrypt;

/**
 * BCrypt password hashing
 */
public final class PasswordHasher {

    private PasswordHasher() { }

    public static String hash(String plainPassword) {
        // according to this discussion, https://stackoverflow.com/questions/43253392/how-many-rounds-is-the-recommended-for-bcrypt-password-hasing
        // i am setting the log_rounds to 12
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt(12));
    }

    public static boolean verify(String plainPassword, String storedHash) {
        if (storedHash == null || storedHash.isEmpty()) {
            return false;
        }
        return BCrypt.checkpw(plainPassword, storedHash);
    }
}