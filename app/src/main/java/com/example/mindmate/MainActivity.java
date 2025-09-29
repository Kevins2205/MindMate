package com.example.mindmate;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.example.mindmate.R;
public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        setupBottomNavigation();

        // Carica il fragment home come default
        if (savedInstanceState == null) {
            loadFragment(new HomepageFragment());
        }
    }

    private void initViews() {
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        fragmentManager = getSupportFragmentManager();
    }

    private void setupBottomNavigation() {
        bottomNavigationView.setOnItemSelectedListener(item -> {
            Fragment fragment = null;

            int itemId = item.getItemId();
            if (itemId == R.id.nav_home) {
                fragment = new HomepageFragment();
            } else if (itemId == R.id.nav_diary) {
                fragment = new DiaryFragment();
            } else if (itemId == R.id.nav_events) {
                fragment = new EventsFragment();
            } else if (itemId == R.id.nav_profile) {
                fragment = new Profile2Fragment();
            }

            return loadFragment(fragment);
        });
    }

    private boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.fragment_container, fragment);
            transaction.commit();
            return true;
        }
        return false;
    }

    public void navigateToPhysicalActivity() {
        loadFragment(new PhysicalActivityFragment());
    }

    public void navigateToDiaryWrite() {
        loadFragment(new DiaryWriteFragment());
    }

    public void navigateToDiaryCalendar() {
        loadFragment(new DiaryCalendarFragment());
    }

    public void navigateToVideos() {
        loadFragment(new VideosFragment());
    }

    public void navigateToBreathing() {
        loadFragment(new BreathingFragment());
    }

    public void navigateToDiaryNote() {
        loadFragment(new DiaryWriteFragment());
    }

    public void navigateToInspiration() {
        loadFragment(new VideosFragment());
    }
}