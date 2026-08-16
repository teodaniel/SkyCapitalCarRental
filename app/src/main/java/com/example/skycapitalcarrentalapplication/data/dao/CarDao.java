package com.example.skycapitalcarrentalapplication.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.skycapitalcarrentalapplication.data.entity.CarEntity;

import java.util.List;

@Dao
public interface CarDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<CarEntity> cars);

    // LiveData: the UI observes this and updates automatically when the table changes.
    @Query("SELECT * FROM cars ORDER BY makeAndModel ASC")
    LiveData<List<CarEntity>> getAllCars();

    // Synchronous — call off the main ui thread (used to decide whether to seed).
    @Query("SELECT COUNT(*) FROM cars")
    int count();

    // Synchronous — call off the main ui thread.
    @Query("UPDATE cars SET available = :available WHERE id = :id")
    void updateAvailability(int id, boolean available);
}