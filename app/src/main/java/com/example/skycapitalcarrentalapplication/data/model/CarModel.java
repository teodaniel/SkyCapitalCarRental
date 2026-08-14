package com.example.skycapitalcarrentalapplication.data.model;

import java.util.List;

public class CarModel {
    private final int id;
    private final String makeAndModel;            // Toyota Corolla
    private final String bodyType;        // SUV, Sedan, Hatchback
    private final String color;
    private final int imageResId;        // or a drawable resource name

    // Performance
    private final String fuelType;        // Petrol, Diesel, Hybrid, Electric
    private final String transmission;    // Automatic, Manual
    private final double engineSize;      // in litres, e.g. 1.8

    // Capacity & comfort
    private final int seats;
    private final int luggageCapacity;    // number of large bags

    // Features
    private final List<String> features;  // ["Bluetooth", "GPS", "Backup Camera"]
    private final double rating;

    // Rental terms
    private double pricePerDay;
    private double deposit;
    private String fuelPolicy;      // "Full to Full"

    // Availability & logistics
    private boolean available;
    private String pickupLocation;

    public CarModel(int id, String makeAndModel, String bodyType, String color, int imageResId, String fuelType, String transmission, double engineSize, int seats, int luggageCapacity, List<String> features, double rating, double pricePerDay, double deposit, String fuelPolicy, boolean available, String pickupLocation) {
        this.id = id;
        this.makeAndModel = makeAndModel;
        this.bodyType = bodyType;
        this.color = color;
        this.imageResId = imageResId;
        this.fuelType = fuelType;
        this.transmission = transmission;
        this.engineSize = engineSize;
        this.seats = seats;
        this.luggageCapacity = luggageCapacity;
        this.features = features;
        this.rating = rating;
        this.pricePerDay = pricePerDay;
        this.deposit = deposit;
        this.fuelPolicy = fuelPolicy;
        this.available = available;
        this.pickupLocation = pickupLocation;
    }

    public int getId() {
        return id;
    }

    public String getMakeAndModel() {
        return makeAndModel;
    }

    public String getBodyType() {
        return bodyType;
    }

    public String getColor() {
        return color;
    }

    public int getImageResId() {
        return imageResId;
    }

    public String getFuelType() {
        return fuelType;
    }

    public String getTransmission() {
        return transmission;
    }

    public double getEngineSize() {
        return engineSize;
    }

    public int getSeats() {
        return seats;
    }

    public int getLuggageCapacity() {
        return luggageCapacity;
    }

    public List<String> getFeatures() {
        return features;
    }

    public double getRating() {
        return rating;
    }

    public double getPricePerDay() {
        return pricePerDay;
    }

    public double getDeposit() {
        return deposit;
    }

    public String getFuelPolicy() {
        return fuelPolicy;
    }

    public boolean isAvailable() {
        return available;
    }

    public String getPickupLocation() {
        return pickupLocation;
    }
}
