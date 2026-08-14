package com.example.skycapitalcarrentalapplication.data.entity;

import android.content.Context;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.skycapitalcarrentalapplication.data.model.CarModel;

import java.util.List;

/**
 * Room row for a car. (Kachow!)
 * Kept separate from CarModel so the domain class, adapter, and detail screen stay unchanged.
 * The image is stored as a drawable NAME (text) rather than an int resId
 * because resource ids aren't stable across builds.
 */
@Entity(tableName = "cars")
public class CarEntity {

    @PrimaryKey
    public int id;

    public String makeAndModel;
    public String bodyType;
    public String color;
    public String imageName;        // drawable entry name, e.g. "audi_q3"
    public String fuelType;
    public String transmission;
    public double engineSize;
    public int seats;
    public int luggageCapacity;
    public List<String> features;   // persisted via Converters
    public double pricePerDay;
    public double deposit;
    public String fuelPolicy;
    public boolean available;
    public String pickupLocation;
    public double rating;

    public CarEntity() {
    }

    /**
     * CarModel -> CarEntity. Uses the resId to look up its stable drawable name.
     */
    public static CarEntity fromModel(CarModel c, Context ctx) {
        CarEntity e = new CarEntity();
        e.id = c.getId();
        e.makeAndModel = c.getMakeAndModel();
        e.bodyType = c.getBodyType();
        e.color = c.getColor();
        e.imageName = ctx.getResources().getResourceEntryName(c.getImageResId());
        e.fuelType = c.getFuelType();
        e.transmission = c.getTransmission();
        e.engineSize = c.getEngineSize();
        e.seats = c.getSeats();
        e.luggageCapacity = c.getLuggageCapacity();
        e.features = c.getFeatures();
        e.rating = c.getRating();
        e.pricePerDay = c.getPricePerDay();
        e.deposit = c.getDeposit();
        e.fuelPolicy = c.getFuelPolicy();
        e.available = c.isAvailable();
        e.pickupLocation = c.getPickupLocation();
        return e;
    }

    /**
     * CarEntity -> CarModel. Resolves the drawable name back to a resId.
     */
    public CarModel toModel(Context ctx) {
        int resId = ctx.getResources()
                .getIdentifier(imageName, "drawable", ctx.getPackageName());
        return new CarModel(id, makeAndModel, bodyType, color, resId, fuelType, transmission,
                engineSize, seats, luggageCapacity, features, rating, pricePerDay, deposit, fuelPolicy,
                available, pickupLocation);
    }
}