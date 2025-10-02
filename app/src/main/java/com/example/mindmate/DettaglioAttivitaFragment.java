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

public class DettaglioAttivitaFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_dettaglio_attivita, container, false);

        // Recuperiamo i dati dal bundle
        Bundle args = getArguments();
        String titolo = args != null ? args.getString("TITOLO") : "Titolo";
        String descrizione = args != null ? args.getString("DESCRIZIONE") : "Descrizione";

        // Associa le view
        TextView tvTitolo = view.findViewById(R.id.tvTitle);
        TextView tvDescrizione = view.findViewById(R.id.tvDescription);
        Button btnStart = view.findViewById(R.id.btnStart);
        Button btnBack = view.findViewById(R.id.btnBack);

        tvTitolo.setText(titolo);
        tvDescrizione.setText(descrizione);

        btnStart.setOnClickListener(v -> {
            getParentFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new ModalitaImmersivaFragment())
                    .addToBackStack(null)
                    .commit();
        });


        btnBack.setOnClickListener(v ->
                requireActivity().getSupportFragmentManager().popBackStack()
        );

        return view;
    }
}
