package com.example.skycapitalcarrentalapplication.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.skycapitalcarrentalapplication.R;
import com.example.skycapitalcarrentalapplication.data.CarRepository;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.slider.Slider;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

/**
 * A {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    private CarsRecyclerViewAdapter carsRecyclerViewAdapter;
    private List<CarModel> carlArrayList = new ArrayList<>();

    //=== Default Filter State ===//
    private String query = "";
    private final Set<String> selectedFuels = new HashSet<>();
    private final Set<String> selectedTransmissions = new HashSet<>();
    private int minSeats = 1;
    private int minLuggage = 0;
    private float maxPrice = 200f;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        carlArrayList = CarRepository.getCars();

        //==== initialize RecyclerView and adapter ====
        RecyclerView recyclerView = view.findViewById(R.id.carsRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        carsRecyclerViewAdapter = new CarsRecyclerViewAdapter(car -> {
            Intent intent = new Intent(requireContext(), CarDetailActivity.class);
            intent.putExtra(CarDetailActivity.EXTRA_CAR_ID, car.getId());
            startActivity(intent);
        });

        recyclerView.setAdapter(carsRecyclerViewAdapter);

        //==== init Search Field ====
        TextInputLayout searchLayout = view.findViewById(R.id.searchLayout);
        TextInputEditText searchInput = view.findViewById(R.id.searchInput);

        searchInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                query = s.toString().trim();
                applySearchFilters();
            }

            @Override
            public void afterTextChanged(android.text.Editable s) {
            }
        });

        //=== Init listeners for search and search filter ===
        searchLayout.setStartIconOnClickListener(v -> hideKeyboard(searchInput));
        searchLayout.setEndIconOnClickListener(v -> showFilterSheet());

        applySearchFilters();
    }

    @SuppressLint("DefaultLocale")
    private void showFilterSheet() {
        BottomSheetDialog dialog = new BottomSheetDialog(requireContext());

        FrameLayout container = dialog.findViewById(com.google.android.material.R.id.design_bottom_sheet);
        View bottomSheetView = LayoutInflater.from(requireContext()).inflate(R.layout.search_filter, container, false);
        dialog.setContentView(bottomSheetView);

        ChipGroup fuelGroup = bottomSheetView.findViewById(R.id.chipGroupFuel);
        ChipGroup transmissionGroup = bottomSheetView.findViewById(R.id.chipGroupTransmission);
        Slider seatsSlider = bottomSheetView.findViewById(R.id.sliderSeats);
        Slider luggageSlider = bottomSheetView.findViewById(R.id.sliderLuggage);
        Slider priceSlider = bottomSheetView.findViewById(R.id.sliderPrice);
        TextView labelSeats = bottomSheetView.findViewById(R.id.labelSeats);
        TextView labelLuggage = bottomSheetView.findViewById(R.id.labelLuggage);
        TextView labelPrice = bottomSheetView.findViewById(R.id.labelPrice);
        MaterialButton buttonReset = bottomSheetView.findViewById(R.id.buttonReset);
        MaterialButton buttonApply = bottomSheetView.findViewById(R.id.buttonApply);

        // Label live updates
        checkChips(fuelGroup, selectedFuels);
        checkChips(transmissionGroup, selectedTransmissions);
        seatsSlider.addOnChangeListener((s, seatCount, fromUser) ->
                labelSeats.setText(String.format("Minimum seats: %d", (int) seatCount)));
        luggageSlider.addOnChangeListener((s, luggageCount, fromUser) ->
                labelLuggage.setText(String.format("Minimum luggage: %d", (int) luggageCount)));
        priceSlider.addOnChangeListener((s, price, fromUser) ->
                labelPrice.setText(String.format(Locale.getDefault(), "Maximum price: S$%d/day", (int) price)));

        seatsSlider.setValue((float) minSeats);
        luggageSlider.setValue((float) minLuggage);
        priceSlider.setValue(maxPrice);

        // Ensure labels show initial values before setValue changes
        labelSeats.setText(String.format("Minimum seats: %d", minSeats));
        labelLuggage.setText(String.format("Minimum luggage: %d", minLuggage));
        labelPrice.setText(String.format(Locale.getDefault(), "Maximum price: S$%d/day", (int) maxPrice));

        buttonApply.setOnClickListener(v -> {
            selectedFuels.clear();
            selectedFuels.addAll(checkedChips(fuelGroup));
            selectedTransmissions.clear();
            selectedTransmissions.addAll(checkedChips(transmissionGroup));
            minSeats = (int) seatsSlider.getValue();
            minLuggage = (int) luggageSlider.getValue();
            maxPrice = priceSlider.getValue();
            applySearchFilters();
            dialog.dismiss();
        });

        buttonReset.setOnClickListener(v -> {
            fuelGroup.clearCheck();
            transmissionGroup.clearCheck();
            seatsSlider.setValue(seatsSlider.getValueFrom());
            luggageSlider.setValue(luggageSlider.getValueFrom());
            priceSlider.setValue(priceSlider.getValueTo());

            selectedFuels.clear();
            selectedTransmissions.clear();
            minSeats = (int) seatsSlider.getValueFrom();
            minLuggage = (int) luggageSlider.getValueFrom();
            maxPrice = priceSlider.getValueTo();
        });

        dialog.show();
    }

    /**
     * Rebuilds the list of cars from the full catalogue using query + filters.
     */
    private void applySearchFilters() {
        String searchQuery = query.toLowerCase(Locale.getDefault());
        List<CarModel> filtered = new ArrayList<>();
        for (CarModel car : carlArrayList) {
            if (!searchQuery.isEmpty() && !car.getMakeAndModel().toLowerCase(Locale.getDefault()).contains(searchQuery))
                continue;

            if (!selectedFuels.isEmpty() && !selectedFuels.contains(car.getFuelType())) continue;

            if (!selectedTransmissions.isEmpty() && !selectedTransmissions.contains(normalizeTransmission(car.getTransmission())))
                continue;

            if (car.getSeats() < minSeats) continue;

            if (car.getLuggageCapacity() < minLuggage) continue;

            if (car.getPricePerDay() > maxPrice) continue;

            filtered.add(car);
        }
        carsRecyclerViewAdapter.submitList(filtered);
    }

    /**
     * Maps the model's "Automatic"/"Manual" to the chip labels "Auto"/"Manual".
     */
    private String normalizeTransmission(String inputTransmission) {
        return (inputTransmission != null &&
                inputTransmission.toLowerCase(Locale.getDefault()).startsWith("auto")) ? "Auto" : "Manual";
    }

    /**
     * Returns a set of the checked filter chip based on a filter group.
     */
    private Set<String> checkedChips(ChipGroup group) {
        Set<String> result = new HashSet<>();
        for (int i = 0; i < group.getChildCount(); i++) {
            Chip chip = (Chip) group.getChildAt(i);
            if (chip.isChecked()) {
                result.add(chip.getText().toString());
            }
        }
        return result;
    }

    /**
     * converts the chip view of a chipGroup to appear selected whose text is in the given set.
     *
     * @param group    the chipGroup to check
     * @param selected the set of selected chips in string
     *
     *                 <p>Example usage:
     *                 <pre>
     *                 {@code selected = {"Auto"};
     *                 checkChips(chipGroup, selected);
     *                 // only sets the "Auto" chip to selected view}
     *                 </pre>
     */
    private void checkChips(ChipGroup group, Set<String> selected) {
        for (int i = 0; i < group.getChildCount(); i++) {
            Chip chip = (Chip) group.getChildAt(i);
            chip.setChecked(selected.contains(chip.getText().toString()));
        }
    }

    private void hideKeyboard(View v) {
        InputMethodManager imm = (InputMethodManager)
                requireContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
    }
}