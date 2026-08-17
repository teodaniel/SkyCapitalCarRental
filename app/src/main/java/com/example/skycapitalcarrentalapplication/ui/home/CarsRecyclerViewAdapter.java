package com.example.skycapitalcarrentalapplication.ui.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.skycapitalcarrentalapplication.R;
import com.example.skycapitalcarrentalapplication.data.model.CarModel;

import java.util.Locale;

/**
 * RecyclerView adapter for the list of rental cars.
 *
 * <p>Extends {@link ListAdapter}, which wraps {@link DiffUtil} internally: push data
 * with {@link #submitList(java.util.List)} and it animates only what changed — no manual
 * notifyItemXxx() calls.</p>
 *
 * <p>Wired to the ConstraintLayout card (carProfileImage, carMakeAndModel, carBodyType,
 * carSeatingAmount, carLuggageAmount, carTransmissionType, carFuelType, reviewText,
 * priceText). The whole card is clickable since there's no dedicated button.</p>
 */
public class CarsRecyclerViewAdapter extends ListAdapter<CarModel, CarsRecyclerViewAdapter.CarViewHolder> {

    /** Callback so the host (fragment/activity) decides what a tap does. */
    public interface OnCarClickListener {
        void onViewDetailsClick(CarModel car);
    }

    private final OnCarClickListener listener;

    public CarsRecyclerViewAdapter(@NonNull OnCarClickListener listener) {
        super(DIFF_CALLBACK);
        this.listener = listener;
    }

    private static final DiffUtil.ItemCallback<CarModel> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<>() {

                @Override
                public boolean areItemsTheSame(@NonNull CarModel oldItem, @NonNull CarModel newItem) {
                    // Same underlying car? Compare the stable, unique id.
                    return oldItem.getId() == newItem.getId();
                }

                @Override
                public boolean areContentsTheSame(@NonNull CarModel oldItem, @NonNull CarModel newItem) {
                    // Compare only the fields this card actually renders.
                    return oldItem.getImageResId() == newItem.getImageResId()
                            && oldItem.getSeats() == newItem.getSeats()
                            && oldItem.getLuggageCapacity() == newItem.getLuggageCapacity()
                            && Double.compare(oldItem.getPricePerDay(), newItem.getPricePerDay()) == 0
                            && Double.compare(oldItem.getRating(), newItem.getRating()) == 0
                            && oldItem.getMakeAndModel().equals(newItem.getMakeAndModel())
                            && oldItem.getBodyType().equals(newItem.getBodyType())
                            && oldItem.getTransmission().equals(newItem.getTransmission())
                            && oldItem.getFuelType().equals(newItem.getFuelType());
                }
            };

    @NonNull
    @Override
    public CarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Change item_car_card to your actual layout file name if different.
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cars_recycler_view_row, parent, false);
        return new CarViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CarViewHolder holder, int position) {
        holder.bind(getItem(position), listener);
    }

    // ---------------------------------------------------------------------------------

    public static class CarViewHolder extends RecyclerView.ViewHolder {

        private final ImageView carProfileImage;
        private final TextView carMakeAndModel;
        private final TextView carBodyType;
        private final TextView carSeatingAmount;
        private final TextView carLuggageAmount;
        private final TextView carTransmissionType;
        private final TextView carFuelType;
        private final TextView reviewText;
        private final TextView priceText;

        CarViewHolder(@NonNull View itemView) {
            super(itemView);
            carProfileImage = itemView.findViewById(R.id.carProfileImage);
            carMakeAndModel = itemView.findViewById(R.id.carMakeAndModel);
            carBodyType = itemView.findViewById(R.id.carBodyType);
            carSeatingAmount = itemView.findViewById(R.id.carSeatingAmount);
            carLuggageAmount = itemView.findViewById(R.id.carLuggageAmount);
            carTransmissionType = itemView.findViewById(R.id.carTransmissionType);
            carFuelType = itemView.findViewById(R.id.carFuelType);
            reviewText = itemView.findViewById(R.id.ratingText);
            priceText = itemView.findViewById(R.id.priceText);
        }

        void bind(final CarModel car, final OnCarClickListener listener) {
            carProfileImage.setImageResource(car.getImageResId());
            carProfileImage.setContentDescription(car.getMakeAndModel());

            carMakeAndModel.setText(car.getMakeAndModel());
            carBodyType.setText(car.getBodyType());
            carSeatingAmount.setText(String.valueOf(car.getSeats()));
            carLuggageAmount.setText(String.valueOf(car.getLuggageCapacity()));
            carTransmissionType.setText(shortTransmission(car.getTransmission()));
            carFuelType.setText(car.getFuelType());

            priceText.setText(String.format(Locale.getDefault(), "S$%.0f/day", car.getPricePerDay()));

            // Rating shown with a star, one decimal place (e.g. "★ 4.5").
            reviewText.setText(String.format(Locale.getDefault(), "★ %.1f", car.getRating()));


            itemView.setOnClickListener(v -> listener.onViewDetailsClick(car));
        }

        /** Keeps the icon row compact: "Automatic" -> "Auto". */
        private String shortTransmission(String transmission) {
            return "Automatic".equalsIgnoreCase(transmission) ? "Auto" : transmission;
        }
    }
}