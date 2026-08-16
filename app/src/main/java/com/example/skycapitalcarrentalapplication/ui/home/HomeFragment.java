package com.example.skycapitalcarrentalapplication.ui.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.skycapitalcarrentalapplication.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.slider.Slider;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

/**
 * A {@link Fragment} subclass for the Home Page ui.
 *
 * <p>
 * Holds the logic for pulling cars data and showing car details modal.
 * </p>
 * <p>
 * Refactored on 15/08/26
 */
public class HomeFragment extends Fragment {
    private HomeViewModel viewModel;
    private CarsRecyclerViewAdapter carsRecyclerViewAdapter;


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

        viewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        //==== initialize RecyclerView and adapter ====
        RecyclerView recyclerView = view.findViewById(R.id.carsRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        carsRecyclerViewAdapter = new CarsRecyclerViewAdapter(car -> {
            Intent intent = new Intent(requireContext(), CarDetailActivity.class);
            intent.putExtra(CarDetailActivity.EXTRA_CAR_ID, car.getId());
            startActivity(intent);
        });

        recyclerView.setAdapter(carsRecyclerViewAdapter);

        //==== observe the filtered list (Room + ViewModel push updates here) ====
        viewModel.getFilteredCars().observe(getViewLifecycleOwner(),
                carsRecyclerViewAdapter::submitList);

        //==== init Search Field ====
        TextInputLayout searchLayout = view.findViewById(R.id.searchLayout);
        TextInputEditText searchInput = view.findViewById(R.id.searchInput);

        searchInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                viewModel.setQuery(s.toString());
            }

            @Override
            public void afterTextChanged(android.text.Editable s) {
            }
        });

        //=== Init listeners for search and search filter ===
        searchLayout.setStartIconOnClickListener(v -> hideKeyboard(searchInput));
        searchLayout.setEndIconOnClickListener(v -> showFilterSheet());
    }


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

        // Restore state from the ViewModel
        checkChips(fuelGroup, viewModel.getSelectedFuels());
        checkChips(transmissionGroup, viewModel.getSelectedTransmissions());

        // Label live updates
        Locale locale = new Locale("en", "SG");
        seatsSlider.addOnChangeListener((s, seatCount, fromUser) ->
                labelSeats.setText(String.format(locale, "Minimum seats: %d", (int) seatCount)));
        luggageSlider.addOnChangeListener((s, luggageCount, fromUser) ->
                labelLuggage.setText(String.format(locale, "Minimum luggage: %d", (int) luggageCount)));
        priceSlider.addOnChangeListener((s, price, fromUser) ->
                labelPrice.setText(String.format(Locale.getDefault(), "Maximum price: S$%d/day", (int) price)));

        seatsSlider.setValue((float) viewModel.getMinSeats());
        luggageSlider.setValue((float) viewModel.getMinLuggage());
        priceSlider.setValue(viewModel.getMaxPrice());

        // Ensure labels show initial values before setValue changes
        labelSeats.setText(String.format(locale, "Minimum seats: %d", viewModel.getMinSeats()));
        labelLuggage.setText(String.format(locale, "Minimum luggage: %d", viewModel.getMinLuggage()));
        labelPrice.setText(String.format(Locale.getDefault(), "Maximum price: S$%d/day", (int) viewModel.getMaxPrice()));

        buttonApply.setOnClickListener(view -> {
            viewModel.setFilters(
                    checkedChips(fuelGroup),
                    checkedChips(transmissionGroup),
                    (int) seatsSlider.getValue(),
                    (int) luggageSlider.getValue(),
                    priceSlider.getValue());
            dialog.dismiss();
        });

        buttonReset.setOnClickListener(view -> {
            fuelGroup.clearCheck();
            transmissionGroup.clearCheck();
            seatsSlider.setValue(seatsSlider.getValueFrom());
            luggageSlider.setValue(luggageSlider.getValueFrom());
            priceSlider.setValue(priceSlider.getValueTo());
            viewModel.resetFilters();
        });

        dialog.show();
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
     * <p>Example usage:</p>
     * <pre>{@code selected = {"Auto"};
     * checkChips(chipGroup, selected);
     * // only sets the "Auto" chip to selected view}
     * </pre>
     */
    private void checkChips(ChipGroup group, Set<String> selected) {
        for (int i = 0; i < group.getChildCount(); i++) {
            Chip chip = (Chip) group.getChildAt(i);
            chip.setChecked(selected.contains(chip.getText().toString()));
        }
    }

    private void hideKeyboard(View v) {
        InputMethodManager imm = (InputMethodManager) requireContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
    }
}