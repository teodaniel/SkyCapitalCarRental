package com.example.skycapitalcarrentalapplication.data.dao;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.skycapitalcarrentalapplication.data.entity.CarEntity;
import com.example.skycapitalcarrentalapplication.data.model.RentalModel;

import java.util.List;

@Dao
public interface RentalDao {

    // Synchronous — call off the main ui thread.
    @Insert
    void insert(RentalModel rental);

    // The cars this user has rented
    @Query("SELECT cars.* FROM cars " +
            "INNER JOIN rentals ON cars.id = rentals.carId " +
            "WHERE rentals.userEmail = :email " +
            "ORDER BY rentals.id DESC")
    LiveData<List<CarEntity>> getRentedCars(String email);
}
