package com.example.skycapitalcarrentalapplication.ui;


import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

import com.example.skycapitalcarrentalapplication.data.AppDatabase;
import com.example.skycapitalcarrentalapplication.data.SessionManager;
import com.example.skycapitalcarrentalapplication.data.dao.RentalDao;
import com.example.skycapitalcarrentalapplication.data.entity.CarEntity;
import com.example.skycapitalcarrentalapplication.data.model.CarModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Exposes the logged-in user's rented cars as LiveData, mapped from CarEntity to CarModel.
 * If no one is logged in, the query simply matches no rows.
 */
public class ProfileViewModel extends AndroidViewModel {

    private final LiveData<List<CarModel>> rentedCars;

    public ProfileViewModel(@NonNull Application app) {
        super(app);
        String email = new SessionManager(app).getEmail();
        RentalDao rentalDao = AppDatabase.getInstance(app).rentalDao();

        LiveData<List<CarEntity>> entities = rentalDao.getRentedCars(email == null ? "" : email);
        rentedCars = Transformations.map(entities, list -> {
            List<CarModel> models = new ArrayList<>();
            for (CarEntity e : list) {
                models.add(e.toModel(app));
            }
            return models;
        });
    }

    public LiveData<List<CarModel>> getRentedCars() {
        return rentedCars;
    }
}
