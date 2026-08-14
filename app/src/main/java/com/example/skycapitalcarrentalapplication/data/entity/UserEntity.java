package com.example.skycapitalcarrentalapplication.data.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

/** Room row for a login user. Email is the identifier (primary key). Password is stored HASHED. */
@Entity(tableName = "users")
public class UserEntity {

    @PrimaryKey
    @NonNull
    public String email = "";

    public String passwordHash;
    public String displayName;

    public UserEntity() { }

    @Ignore
    public UserEntity(@NonNull String email, String passwordHash) {
        this.email = email;
        this.passwordHash = passwordHash;
    }
}
