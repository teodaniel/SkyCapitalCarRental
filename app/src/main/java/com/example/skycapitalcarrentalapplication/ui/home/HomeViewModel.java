package com.example.skycapitalcarrentalapplication.ui.home;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Transformations;

import com.example.skycapitalcarrentalapplication.data.AppDatabase;
import com.example.skycapitalcarrentalapplication.data.CarRepository;
import com.example.skycapitalcarrentalapplication.data.dao.CarDao;
import com.example.skycapitalcarrentalapplication.data.entity.CarEntity;
import com.example.skycapitalcarrentalapplication.data.model.CarModel;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

/**
 * Owns the car list from Room and the current filter state.
 *
 * <p>
 * Seeding: on first creation, if the table is empty, it fills the DB from {@link CarRepository} on a background thread.
 * Room's LiveData then emits the rows.
 * </p>
 *
 * <p>
 * Filtering happens here and not in the fragment so the selected filters survive
 * configuration changes and navigation.
 * </p>
 *
 * Refactored on 15/08/26
 */
public class HomeViewModel extends AndroidViewModel {


    //=== Default Filter State ===//
    static final int DEFAULT_MIN_SEATS = 1;
    static final int DEFAULT_MIN_LUGGAGE = 0;
    static final float DEFAULT_MAX_PRICE = 200f;

    private final LiveData<List<CarModel>> allCars;
    private final MediatorLiveData<List<CarModel>> filteredCars = new MediatorLiveData<>();

    // ----- filter state -----
    private String query = "";
    private Set<String> selectedFuels = new HashSet<>();
    private Set<String> selectedTransmissions = new HashSet<>();  // "Auto" / "Manual"
    private int minSeats = DEFAULT_MIN_SEATS;
    private int minLuggage = DEFAULT_MIN_LUGGAGE;
    private float maxPrice = DEFAULT_MAX_PRICE;

    public HomeViewModel(@NonNull Application app) {
        super(app);
        AppDatabase db = AppDatabase.getInstance(app);
        CarDao carDao = db.carDao();

        // Seed data once, when the home screen is first created.
        // This happens on a background thread so the ui does not hang.
        AppDatabase.getDbExecutor().execute(() -> {
            if (carDao.count() == 0) {
                List<CarEntity> entities = new ArrayList<>();
                for (CarModel c : CarRepository.getCars()) {
                    entities.add(CarEntity.fromModel(c, app));
                }
                carDao.insertAll(entities);
            }
        });

        // Room entities convert to domain models to make sure room can use them.
        allCars = Transformations.map(carDao.getAllCars(), entities -> {
            List<CarModel> models = new ArrayList<>();
            for (CarEntity e : entities) {
                models.add(e.toModel(app));
            }
            return models;
        });

        filteredCars.addSource(allCars, cars -> recompute());
    }

    public LiveData<List<CarModel>> getFilteredCars() {
        return filteredCars;
    }

    public void setQuery(String q) {
        query = (q == null) ? "" : q.trim();
        recompute();
    }

    public void setFilters(Set<String> fuels, Set<String> transmissions,
                           int minSeats, int minLuggage, float maxPrice) {
        this.selectedFuels = fuels;
        this.selectedTransmissions = transmissions;
        this.minSeats = minSeats;
        this.minLuggage = minLuggage;
        this.maxPrice = maxPrice;
        recompute();
    }

    public void resetFilters() {
        selectedFuels = new HashSet<>();
        selectedTransmissions = new HashSet<>();
        minSeats = DEFAULT_MIN_SEATS;
        minLuggage = DEFAULT_MIN_LUGGAGE;
        maxPrice = DEFAULT_MAX_PRICE;
        recompute();
    }

    // ----- getters so the filter sheet can restore its state -----
    public Set<String> getSelectedFuels() {
        return selectedFuels;
    }

    public Set<String> getSelectedTransmissions() {
        return selectedTransmissions;
    }

    public int getMinSeats() {
        return minSeats;
    }

    public int getMinLuggage() {
        return minLuggage;
    }

    public float getMaxPrice() {
        return maxPrice;
    }

    private void recompute() {
        List<CarModel> source = allCars.getValue();
        if (source == null) {
            filteredCars.setValue(new ArrayList<>());
            return;
        }
        String q = query.toLowerCase(Locale.getDefault());
        List<CarModel> out = new ArrayList<>();
        for (CarModel c : source) {
            if (!q.isEmpty()
                    && !c.getMakeAndModel().toLowerCase(Locale.getDefault()).contains(q)) continue;
            if (!selectedFuels.isEmpty() && !selectedFuels.contains(c.getFuelType())) continue;
            if (!selectedTransmissions.isEmpty()
                    && !selectedTransmissions.contains(normalizeTransmission(c.getTransmission())))
                continue;
            if (c.getSeats() < minSeats) continue;
            if (c.getLuggageCapacity() < minLuggage) continue;
            if (c.getPricePerDay() > maxPrice) continue;
            out.add(c);
        }
        filteredCars.setValue(out);
    }

    private String normalizeTransmission(String t) {
        return (t != null && t.toLowerCase(Locale.getDefault()).startsWith("auto")) ? "Auto" : "Manual";
    }
}