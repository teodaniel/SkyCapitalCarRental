package com.example.skycapitalcarrentalapplication.data;

import com.example.skycapitalcarrentalapplication.CarModel;
import com.example.skycapitalcarrentalapplication.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Static, in-app catalogue of cars available for rent.
 */
public final class CarRepository {

    private CarRepository() {
        // Utility class — no instances.
    }

    public static List<CarModel> getCars() {
        List<CarModel> cars = new ArrayList<>();

        cars.add(new CarModel(
                1, "Audi Q3", "SUV", "Grey",
                R.drawable.audi_q3_1_l,
                "Petrol", "Automatic", 2.0,
                5, 3,
                Arrays.asList("Bluetooth", "GPS", "Backup Camera", "Apple CarPlay", "Cruise Control"), 4.7,
                72.0, 300.0, "Full to Full",
                true, "Changi Airport"));

        cars.add(new CarModel(
                2, "BMW 4 Series Gran Coupe", "Sedan", "Red",
                R.drawable.bmw_4_series_gran_coupe_l,
                "Petrol", "Automatic", 2.0,
                5, 3,
                Arrays.asList("Bluetooth", "GPS", "Backup Camera", "Sunroof", "Leather Seats", "Apple CarPlay"), 4.8,
                95.0, 400.0, "Full to Full",
                true, "Toa Payoh Branch"));

        cars.add(new CarModel(
                3, "BYD Atto 3", "SUV", "Black",
                R.drawable.byd_atto_3_2024_l,
                "Electric", "Automatic", 0.0,
                5, 3,
                Arrays.asList("Bluetooth", "GPS", "Backup Camera", "Apple CarPlay", "Android Auto", "360 Camera"), 4.6,
                68.0, 350.0, "Full Charge to Full Charge",
                true, "Queensway Branch"));

        cars.add(new CarModel(
                4, "Daihatsu Move", "Hatchback", "Light Blue",
                R.drawable.daihatsu_move_2025_l,
                "Petrol", "Automatic", 0.66,
                4, 1,
                Arrays.asList("Bluetooth", "GPS", "Backup Camera"), 4.4,
                28.0, 120.0, "Full to Full",
                true, "Jurong West Branch"));

        cars.add(new CarModel(
                5, "Ford Bronco", "SUV", "White",
                R.drawable.ford_bronco_1_l,
                "Petrol", "Automatic", 2.3,
                5, 3,
                Arrays.asList("Bluetooth", "GPS", "Backup Camera", "Apple CarPlay", "Android Auto", "4WD"), 4.6,
                98.0, 450.0, "Full to Full",
                false, "Bedok Branch"));

        cars.add(new CarModel(
                6, "Ford Mustang", "Coupe", "Black",
                R.drawable.ford_mustang_2024_l,
                "Petrol", "Automatic", 5.0,
                4, 2,
                Arrays.asList("Bluetooth", "GPS", "Backup Camera", "Apple CarPlay", "Leather Seats"), 4.7,
                125.0, 600.0, "Full to Full",
                true, "Choa Chu Kang Branch"));

        cars.add(new CarModel(
                7, "Honda Fit", "Hatchback", "Silver",
                R.drawable.honda_fit_2024_l,
                "Hybrid", "Automatic", 1.5,
                5, 2,
                Arrays.asList("Bluetooth", "GPS", "Backup Camera", "Apple CarPlay"), 4.5,
                40.0, 150.0, "Full to Full",
                true, "Changi Airport"));

        cars.add(new CarModel(
                8, "Honda Freed", "Minivan", "DarkBlue",
                R.drawable.honda_freed_2024_l,
                "Hybrid", "Automatic", 1.5,
                7, 3,
                Arrays.asList("Bluetooth", "GPS", "Backup Camera", "Sliding Doors", "Apple CarPlay"), 4.7,
                56.0, 220.0, "Full to Full",
                true, "Bishan Branch"));

        cars.add(new CarModel(
                9, "Honda Freed 2", "Minivan", "Light Blue",
                R.drawable.honda_freed_2_new_l,
                "Petrol", "Automatic", 1.5,
                7, 3,
                Arrays.asList("Bluetooth", "GPS", "Backup Camera", "Sliding Doors"), 4.8,
                52.0, 220.0, "Full to Full",
                false, "Bishan Branch"));

        cars.add(new CarModel(
                10, "Honda Freed", "Minivan", "White",
                R.drawable.honda_freed_l,
                "Petrol", "Automatic", 1.5,
                6, 3,
                Arrays.asList("Bluetooth", "GPS", "Sliding Doors"), 4.4,
                50.0, 200.0, "Full to Full",
                true, "Bukit Batok Branch"));

        cars.add(new CarModel(
                11, "Honda N-Box II", "Hatchback", "White",
                R.drawable.honda_n_box_2_l,
                "Petrol", "Automatic", 0.66,
                4, 1,
                Arrays.asList("Bluetooth", "GPS", "Backup Camera", "Sliding Doors"), 4.5,
                33.0, 130.0, "Full to Full",
                true, "Jurong West Branch"));

        cars.add(new CarModel(
                12, "Honda N-Box", "Hatchback", "Baby Blue",
                R.drawable.honda_n_box_l,
                "Petrol", "Automatic", 0.66,
                4, 1,
                Arrays.asList("Bluetooth", "GPS", "Backup Camera"), 4.3,
                32.0, 130.0, "Full to Full",
                true, "Changi Airport"));

        cars.add(new CarModel(
                13, "Honda N-Box", "Hatchback", "White",
                R.drawable.honda_n_box_white_l,
                "Petrol", "Automatic", 0.66,
                4, 1,
                Arrays.asList("Bluetooth", "GPS"), 4.5,
                32.0, 130.0, "Full to Full",
                false, "Toa Payoh Branch"));

        cars.add(new CarModel(
                14, "Honda Odyssey", "Minivan", "White",
                R.drawable.honda_odyssey_2023_l,
                "Hybrid", "Automatic", 2.0,
                7, 4,
                Arrays.asList("Bluetooth", "GPS", "Backup Camera", "Sliding Doors", "Leather Seats", "Apple CarPlay"), 4.6,
                78.0, 300.0, "Full to Full",
                true, "Toa Payoh Branch"));

        cars.add(new CarModel(
                15, "Honda Spike", "Minivan", "White",
                R.drawable.honda_spike_1_l,
                "Petrol", "Automatic", 1.5,
                5, 3,
                Arrays.asList("Bluetooth", "GPS", "Sliding Doors"), 4.8,
                44.0, 180.0, "Full to Full",
                true, "Bukit Batok Branch"));

        cars.add(new CarModel(
                16, "Honda Stepwagon", "Minivan", "Black",
                R.drawable.honda_stepwagon_new_l,
                "Hybrid", "Automatic", 1.5,
                8, 4,
                Arrays.asList("Bluetooth", "GPS", "Backup Camera", "Sliding Doors", "Apple CarPlay"), 3.9,
                62.0, 250.0, "Full to Full",
                true, "Choa Chu Kang Branch"));

        cars.add(new CarModel(
                17, "Honda Vezel", "SUV", "Green",
                R.drawable.honda_vezel_2024_l,
                "Hybrid", "Automatic", 1.5,
                5, 2,
                Arrays.asList("Bluetooth", "GPS", "Backup Camera", "Apple CarPlay", "Cruise Control"), 4.7,
                52.0, 200.0, "Full to Full",
                true, "Changi Airport"));

        cars.add(new CarModel(
                18, "Mazda 6 Atenza", "Sedan", "Grey",
                R.drawable.mazda6_atenza_l,
                "Diesel", "Automatic", 2.2,
                5, 3,
                Arrays.asList("Bluetooth", "GPS", "Backup Camera", "Leather Seats", "Apple CarPlay"), 4.7,
                60.0, 250.0, "Full to Full",
                true, "Bedok Branch"));

        cars.add(new CarModel(
                19, "Mazda 2", "Hatchback", "Silver",
                R.drawable.mazda_2_2023_l,
                "Petrol", "Automatic", 1.5,
                5, 2,
                Arrays.asList("Bluetooth", "GPS", "Backup Camera"), 4.2,
                36.0, 150.0, "Full to Full",
                true, "Queensway Branch"));

        cars.add(new CarModel(
                20, "Mazda 3", "Sedan", "White",
                R.drawable.mazda_3_2018_l,
                "Petrol", "Automatic", 2.0,
                5, 2,
                Arrays.asList("Bluetooth", "GPS", "Backup Camera", "Cruise Control"), 4.1,
                45.0, 180.0, "Full to Full",
                true, "Bishan Branch"));

        cars.add(new CarModel(
                21, "Mazda Biante", "Minivan", "Black",
                R.drawable.mazda_biante_new_l,
                "Petrol", "Automatic", 2.0,
                8, 3,
                Arrays.asList("Bluetooth", "GPS", "Sliding Doors"), 4.5,
                55.0, 220.0, "Full to Full",
                false, "Bukit Batok Branch"));

        cars.add(new CarModel(
                22, "Mazda CX-5 2013", "SUV", "White",
                R.drawable.mazda_cx5_2013_l,
                "Diesel", "Automatic", 2.2,
                5, 3,
                Arrays.asList("Bluetooth", "GPS", "Backup Camera", "Cruise Control"), 4.7,
                45.0, 200.0, "Full to Full",
                true, "Jurong West Branch"));

        cars.add(new CarModel(
                23, "Mazda CX-3 2021", "SUV", "White",
                R.drawable.mazda_cx_3_2021_l,
                "Petrol", "Automatic", 2.0,
                5, 2,
                Arrays.asList("Bluetooth", "GPS", "Backup Camera", "Apple CarPlay"), 4.5,
                48.0, 200.0, "Full to Full",
                true, "Changi Airport"));

        cars.add(new CarModel(
                24, "Mazda CX-5 2024", "SUV", "White",
                R.drawable.mazda_cx_5_2024_l,
                "Petrol", "Automatic", 2.5,
                5, 3,
                Arrays.asList("Bluetooth", "GPS", "Backup Camera", "Apple CarPlay", "Android Auto", "Leather Seats"), 4.2,
                66.0, 280.0, "Full to Full",
                true, "Toa Payoh Branch"));

        cars.add(new CarModel(
                25, "Mazda Demio", "Hatchback", "Silver",
                R.drawable.mazda_demio_l,
                "Petrol", "Automatic", 1.3,
                5, 2,
                Arrays.asList("Bluetooth", "GPS", "Backup Camera"), 4.3,
                35.0, 150.0, "Full to Full",
                true, "Choa Chu Kang Branch"));

        cars.add(new CarModel(
                26, "Mazda Premacy 2019", "Minivan", "Red",
                R.drawable.mazda_premacy_2019_l,
                "Petrol", "Automatic", 2.0,
                7, 3,
                Arrays.asList("Bluetooth", "GPS", "Backup Camera", "Sliding Doors"), 4.5,
                50.0, 200.0, "Full to Full",
                true, "Bishan Branch"));

        cars.add(new CarModel(
                28, "Mercedes-Benz B-Class", "Hatchback", "Black",
                R.drawable.mercedes_benz_b_class_1_l,
                "Petrol", "Automatic", 1.6,
                5, 2,
                Arrays.asList("Bluetooth", "GPS", "Backup Camera", "Leather Seats", "Apple CarPlay", "Cruise Control"), 4.2,
                70.0, 300.0, "Full to Full",
                true, "Queensway Branch"));

        cars.add(new CarModel(
                29, "Mercedes-Benz SL-Class", "Convertible", "Silver",
                R.drawable.mercedes_benz_sl_class_l,
                "Petrol", "Automatic", 3.0,
                2, 1,
                Arrays.asList("Bluetooth", "GPS", "Backup Camera", "Leather Seats", "Apple CarPlay", "Convertible Roof"), 4.9,
                150.0, 800.0, "Full to Full",
                true, "Toa Payoh Branch"));

        return cars;
    }

    /**
     * Finds a car by its unique id, or returns null if none matches.
     * Used by the detail screen to re-fetch the car passed via Intent.
     */
    public static CarModel findById(int id) {
        for (CarModel car : getCars()) {
            if (car.getId() == id) {
                return car;
            }
        }
        return null;
    }
}