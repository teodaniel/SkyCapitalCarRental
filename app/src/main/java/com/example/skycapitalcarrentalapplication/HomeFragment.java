package com.example.skycapitalcarrentalapplication;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.skycapitalcarrentalapplication.data.CarRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    private List<CarModel> carModelArrayList = new ArrayList<>();


    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerView = view.findViewById(R.id.carsRecyclerView); // your RecyclerView's id
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        CarsRecyclerViewAdapter adapter = new CarsRecyclerViewAdapter(car -> {

             Intent intent = new Intent(requireContext(), CarDetailActivity.class);
             intent.putExtra(CarDetailActivity.EXTRA_CAR_ID, car.getId());
             startActivity(intent);
        });

        recyclerView.setAdapter(adapter);
        adapter.submitList(CarRepository.getCars());
    }

    private void setupCarArrayList () {
        carModelArrayList.clear();
        carModelArrayList.addAll(CarRepository.getCars());
    }
}