package com.example.skycapitalcarrentalapplication.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.skycapitalcarrentalapplication.R;
import com.example.skycapitalcarrentalapplication.data.SessionManager;
import com.example.skycapitalcarrentalapplication.databinding.ActivityMainBinding;
import com.example.skycapitalcarrentalapplication.ui.contact.ContactFragment;
import com.example.skycapitalcarrentalapplication.ui.home.HomeFragment;
import com.example.skycapitalcarrentalapplication.ui.profile.ProfileFragment;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new HomeFragment());
        binding.headerToolbar.inflateMenu(R.menu.profile_toolbar_menu);

        binding.headerToolbar.setOnMenuItemClickListener(menuItem -> {
            if (menuItem.getItemId() == R.id.action_logout) {

                Toast.makeText(MainActivity.this, "Logging out...", Toast.LENGTH_SHORT).show();

                new SessionManager(this).logout();
                Intent intent = new Intent(this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
                return true;
            }
            return false;
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            Fragment selectedFragment = null;

            int itemId = item.getItemId();
            if (itemId == R.id.home) {
                selectedFragment = new HomeFragment();
            } else if (itemId == R.id.contact) {
                ContactFragment contactFragment = new ContactFragment();
                contactFragment.show(getSupportFragmentManager(), "ThreeQuarterSheet");

                return false;
            } else if (itemId == R.id.profile) {
                selectedFragment = new ProfileFragment();
            }

            if (selectedFragment != null) {
                replaceFragment(selectedFragment);
                return true;
            }

            return false;
        });
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }
}