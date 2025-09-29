package com.example.mindmate;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Profile2Fragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile2, container, false);
        ImageView avatar = view.findViewById(R.id.profile_avatar);
        TextView name = view.findViewById(R.id.profile_name);
        TextView email = view.findViewById(R.id.profile_email);
        Button btnUserDetails = view.findViewById(R.id.btn_user_details);
        Button btnSaved = view.findViewById(R.id.btn_saved);
        Button btnSettings = view.findViewById(R.id.btn_settings);

        // Esempio dati statici
        name.setText("Kevin Rossi");
        email.setText("kevin.rossi@email.com");
        avatar.setImageResource(R.drawable.ic_profile);

        btnUserDetails.setOnClickListener(v -> {
            // Apri dettagli utente
            Fragment details = new UserDetailsFragment();
            requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, details)
                .addToBackStack(null)
                .commit();
        });
        btnSaved.setOnClickListener(v -> {
            // Apri salvati
            Fragment saved = new SavedFragment();
            requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, saved)
                .addToBackStack(null)
                .commit();
        });
        btnSettings.setOnClickListener(v -> {
            // Apri impostazioni
            Fragment settings = new SettingsFragment();
            requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, settings)
                .addToBackStack(null)
                .commit();
        });
        return view;
    }
}

