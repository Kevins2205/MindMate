package com.example.mindmate;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class UserDetailsFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_details, container, false);
        Button btnEdit = view.findViewById(R.id.btn_edit_user_details);
        btnEdit.setOnClickListener(v -> {
            // Logica per modificare i dettagli utente
        });
        // Puoi aggiungere qui la logica per caricare i dati reali dell'utente
        return view;
    }
}

