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

public class DettaglioEventoFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dettaglio_evento, container, false);

        Bundle args = getArguments();
        String titolo = args != null ? args.getString("TITOLO") : "Titolo Evento";
        String descrizione = args != null ? args.getString("DESCRIZIONE") : "Descrizione evento";
        String posizione = args != null ? args.getString("POSIZIONE") : "Posizione evento";
        String data = args != null ? args.getString("DATA") : "Data evento";

        TextView tvTitolo = view.findViewById(R.id.tvTitoloEventoDettaglio);
        TextView tvDescrizione = view.findViewById(R.id.tvDescrizioneEventoDettaglio);
        TextView tvPosizione = view.findViewById(R.id.tvPosizioneEventoDettaglio);
        TextView tvData = view.findViewById(R.id.tvDataEventoDettaglio);
        Button btnPartecipa = view.findViewById(R.id.btnPartecipaEvento);
        Button btnIndietro = view.findViewById(R.id.btnIndietroEvento);

        tvTitolo.setText(titolo);
        tvDescrizione.setText(descrizione);
        tvPosizione.setText(posizione);
        tvData.setText(data);

        btnPartecipa.setOnClickListener(v -> {
            // Azione di partecipazione (da implementare)
        });

        btnIndietro.setOnClickListener(v -> requireActivity().getSupportFragmentManager().popBackStack());

        return view;
    }
}

