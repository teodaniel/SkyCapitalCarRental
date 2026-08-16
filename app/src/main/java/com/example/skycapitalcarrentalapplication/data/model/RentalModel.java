package com.example.skycapitalcarrentalapplication.data.model;
import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

/**
 * Model for a rental booking
 * A rental links a user (by email) to a car (by id).
 * created when the user confirms a rental on the detail screen.
 */
@Entity(tableName = "rentals", indices = {@Index("userEmail")})
public class RentalModel {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @NonNull
    public String userEmail = "";

    public int carId;

    public RentalModel() { }

    @Ignore
    public RentalModel(@NonNull String userEmail, int carId) {
        this.userEmail = userEmail;
        this.carId = carId;
    }
}