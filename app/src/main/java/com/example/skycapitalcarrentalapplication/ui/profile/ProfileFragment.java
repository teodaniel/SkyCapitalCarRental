package com.example.skycapitalcarrentalapplication.ui.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.skycapitalcarrentalapplication.R;
import com.example.skycapitalcarrentalapplication.data.SessionManager;

/**
 * Profile screen. Shows the signed-in user's email and a list of the cars they've rented.
 *
 * <p>
 *     The email comes from {@link SessionManager};
 *     Rented cars are observed from {@link ProfileViewModel} as LiveData,
 *     so the list updates automatically when a new rental is saved.
 * </p>
 */
public class ProfileFragment extends Fragment {

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ProfileViewModel viewModel = new ViewModelProvider(this).get(ProfileViewModel.class);

        // Email from the session; password stays masked (only the hash is stored).
        TextView emailText = view.findViewById(R.id.profileEmail);
        String email = new SessionManager(requireContext()).getEmail();
        emailText.setText(email != null ? email : "Not signed in");

        // Rented cars list
        RecyclerView recyclerView = view.findViewById(R.id.rentedCarsRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        RentedCarsRecyclerViewAdapter adapter = new RentedCarsRecyclerViewAdapter();
        recyclerView.setAdapter(adapter);
        viewModel.getRentedCars().observe(getViewLifecycleOwner(), adapter::submitList);
    }
}