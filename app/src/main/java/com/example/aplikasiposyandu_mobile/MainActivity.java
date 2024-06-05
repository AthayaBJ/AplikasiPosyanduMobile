package com.example.aplikasiposyandu_mobile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener {

    private BottomNavigationView bottomNavigationView;
    private HomeFragment homeFragment = new HomeFragment();
    private CalendarFragment calendarFragment = new CalendarFragment();
    private ProfileFragment profileFragment = new ProfileFragment();

    public static final String SHARED_PREF_NAME = "myPref";
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);

        Boolean masuk = sharedPreferences.getBoolean("masuk", false);
        if (!masuk) {
            Intent login = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(login);
            finish();
            return;  // Ensure no further code is executed
        }

        bottomNavigationView = findViewById(R.id.bottomView);
        bottomNavigationView.setOnItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.home); // Set the default selected item

        // Show the home fragment by default
        getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, homeFragment).commit();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        if (menuItem.getItemId() == R.id.home) {
            getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, homeFragment).commit();
            return true;
        } else if (menuItem.getItemId() == R.id.calendar) {
            getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, calendarFragment).commit();
            return true;
        } else if (menuItem.getItemId() == R.id.profile) {
            getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, profileFragment).commit();
            return true;
        }

        return false;
    }
}
