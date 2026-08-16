package com.example.skycapitalcarrentalapplication.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.skycapitalcarrentalapplication.data.dao.CarDao;
import com.example.skycapitalcarrentalapplication.data.dao.RentalDao;
import com.example.skycapitalcarrentalapplication.data.dao.UserDao;
import com.example.skycapitalcarrentalapplication.data.entity.CarEntity;
import com.example.skycapitalcarrentalapplication.data.entity.UserEntity;
import com.example.skycapitalcarrentalapplication.data.model.RentalModel;
import com.example.skycapitalcarrentalapplication.utils.Converters;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {CarEntity.class, UserEntity.class, RentalModel.class}, version = 2, exportSchema = false)
@TypeConverters(Converters.class)
public abstract class AppDatabase extends RoomDatabase {

    public abstract CarDao carDao();
    public abstract UserDao userDao();
    public abstract RentalDao rentalDao();

    private static volatile AppDatabase INSTANCE;

    /** Single background thread for all writes / synchronous reads. */
    private static final ExecutorService DB_EXECUTOR = Executors.newSingleThreadExecutor();

    public static AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                                    context.getApplicationContext(),
                                    AppDatabase.class,
                                    "skycapital.db")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    public static ExecutorService getDbExecutor() {
        return DB_EXECUTOR;
    }
}
