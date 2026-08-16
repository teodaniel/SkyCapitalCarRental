package com.example.skycapitalcarrentalapplication.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.skycapitalcarrentalapplication.R;
import com.example.skycapitalcarrentalapplication.data.CarRepository;
import com.example.skycapitalcarrentalapplication.data.model.CarModel;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.Locale;

public class CarDetailActivity extends AppCompatActivity {
    /** Intent extra key: the id of the car to show. */
    public static final String EXTRA_CAR_ID = "carId";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_car_detail);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main_car_detail), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        int carId = getIntent().getIntExtra(EXTRA_CAR_ID, -1);
        CarModel car = CarRepository.findById(carId);

        if (car == null) {
            Toast.makeText(this, "Error: Car not found", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        bindCar(car);

        findViewById(R.id.buttonBack).setOnClickListener(v -> finish());
    }

    private void bindCar(CarModel car) {
        ImageView image = findViewById(R.id.detailCarImage);
        image.setImageResource(car.getImageResId());

        // Header Section
        ((TextView) findViewById(R.id.detailTitle)).setText(String.valueOf(car.getMakeAndModel()));
        ((TextView) findViewById(R.id.detailSubtitle))
                .setText(String.format("%s  •  %s", car.getColor(), car.getBodyType()));

        // Description
        ((TextView) findViewById(R.id.detailDescription)).setText(buildDescription(car));

        // Performance Flow Section
        ((TextView) findViewById(R.id.detailSeats)).setText(String.valueOf(car.getSeats()));
        ((TextView) findViewById(R.id.detailLuggage)).setText(String.valueOf(car.getLuggageCapacity()));
        ((TextView) findViewById(R.id.detailTransmission)).setText(car.getTransmission());
        ((TextView) findViewById(R.id.detailFuel)).setText(car.getFuelType());

        // Pricing Section
        ((TextView) findViewById(R.id.detailDeposit)).setText(String.format(Locale.getDefault(), "S$%.0f", car.getDeposit()));
        ((TextView) findViewById(R.id.detailFuelPolicy)).setText(car.getFuelPolicy());
        ((TextView) findViewById(R.id.detailPickup)).setText(car.getPickupLocation());

        // Feature chips
        ChipGroup chipGroup = findViewById(R.id.detailFeaturesChipGroup);
        chipGroup.removeAllViews();
        for (String feature : car.getFeatures()) {
            Chip chip = new Chip(this);
            chip.setText(feature);
            chip.setClickable(false);
            chip.setCheckable(false);
            chipGroup.addView(chip);
        }

        // Primary action
        MaterialButton rentButton = findViewById(R.id.buttonRent);
        rentButton.setText(String.format(Locale.getDefault(), "Rent  •  S$%.0f/day", car.getPricePerDay()));
        rentButton.setOnClickListener(v -> showRentDialog(car));
    }

    /** Builds the short description; engine size is omitted for electric cars. */
    private String buildDescription(CarModel car) {
        StringBuilder sb = new StringBuilder();
        sb.append("The ").append(car.getColor()).append(" ").append(car.getMakeAndModel())
                .append(" is a ").append(car.getBodyType())
                .append(" that seats ").append(car.getSeats())
                .append(". It runs on ").append(car.getFuelType().toLowerCase(Locale.getDefault()));
        if (!"Electric".equalsIgnoreCase(car.getFuelType())) {
            sb.append(" with a ").append(car.getEngineSize()).append("L engine");
        }
        sb.append(".");
        return sb.toString();
    }

    /** Shows the rent-confirmation modal with a price breakdown. */
    private void showRentDialog(CarModel car) {
        View content = getLayoutInflater().inflate(R.layout.dialog_rent_confirmation, null);

        int rentalDays = 1;   // placeholder until you add a day picker
        double perDayTotal = car.getPricePerDay() * rentalDays;
        double total = perDayTotal + car.getDeposit();

        ((TextView) content.findViewById(R.id.dialogPerDayLabel))
                .setText(String.format(Locale.getDefault(),"%s%d)", getString(R.string.price_per_day_1), rentalDays));
        ((TextView) content.findViewById(R.id.dialogPerDayValue))
                .setText(String.format(Locale.getDefault(), "S$%.0f", perDayTotal));
        ((TextView) content.findViewById(R.id.dialogDepositValue))
                .setText(String.format(Locale.getDefault(), "S$%.0f", car.getDeposit()));
        ((TextView) content.findViewById(R.id.dialogTotalValue))
                .setText(String.format(Locale.getDefault(), "S$%.0f", total));

        new MaterialAlertDialogBuilder(this)
                .setTitle("Confirm Rental")
                .setView(content)
                .setNegativeButton("Cancel", null)
                .setPositiveButton("Confirm", (dialog, which) -> {
                    // TODO: handle the booking (e.g. mark car unavailable, show a receipt)
                    Toast.makeText(this, "Booking confirmed!", Toast.LENGTH_SHORT).show();
                })
                .show();
    }

}