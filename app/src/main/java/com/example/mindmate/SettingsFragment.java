package com.example.mindmate;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Switch;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class SettingsFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        Switch switchNotifications = view.findViewById(R.id.switch_notifications);
        Switch switchDarkTheme = view.findViewById(R.id.switch_dark_theme);
        Spinner spinnerLanguage = view.findViewById(R.id.spinner_language);
        Button btnLogout = view.findViewById(R.id.btn_logout);

        // Logica base (da completare con preferenze reali)
        switchNotifications.setOnCheckedChangeListener((buttonView, isChecked) -> {
            // Attiva/disattiva notifiche
        });
        switchDarkTheme.setOnCheckedChangeListener((buttonView, isChecked) -> {
            // Attiva/disattiva tema scuro
        });
        btnLogout.setOnClickListener(v -> {
            // Logica di logout
        });

        return view;
    }
}

