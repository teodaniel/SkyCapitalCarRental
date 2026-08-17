package com.example.skycapitalcarrentalapplication.ui.profile;

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

public class RentedCarsRecyclerViewAdapter extends ListAdapter<CarModel, RentedCarsRecyclerViewAdapter.RentedViewHolder> {

    public RentedCarsRecyclerViewAdapter() {
        super(DIFF_CALLBACK);
    }


    private static final DiffUtil.ItemCallback<CarModel> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<>() {
                @Override
                public boolean areItemsTheSame(@NonNull CarModel oldItem, @NonNull CarModel newItem) {
                    return oldItem.getId() == newItem.getId();
                }

                @Override
                public boolean areContentsTheSame(@NonNull CarModel oldItem, @NonNull CarModel newItem) {
                    return oldItem.getImageResId() == newItem.getImageResId()
                            && oldItem.getMakeAndModel().equals(newItem.getMakeAndModel())
                            && oldItem.getBodyType().equals(newItem.getBodyType());
                }
            };

    @NonNull
    @Override
    public RentedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rented_cars_recycler_view_row, parent, false);
        return new RentedViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RentedViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    public static class RentedViewHolder extends RecyclerView.ViewHolder {
        private final TextView rentedMakeModel;
        private final TextView rentedBodyType;
        private final ImageView rentedImage;

        RentedViewHolder(@NonNull View itemView) {
            super(itemView);
            rentedMakeModel = itemView.findViewById(R.id.rentedMakeModel);
            rentedBodyType = itemView.findViewById(R.id.rentedBodyType);
            rentedImage = itemView.findViewById(R.id.rentedImage);
        }

        void bind(CarModel car) {
            rentedMakeModel.setText(car.getMakeAndModel());
            rentedBodyType.setText(car.getBodyType());
            rentedImage.setImageResource(car.getImageResId());
            rentedImage.setContentDescription(car.getMakeAndModel());
        }
    }
}
