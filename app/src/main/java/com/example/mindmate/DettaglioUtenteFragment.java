package com.example.mindmate;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mindmate.ObiettiviRaggiuntiAdapter;

import java.util.ArrayList;
import java.util.List;

public class DettaglioUtenteFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dettaglio_utente, container, false);

        // Popola i dati utente statici
        ((TextView) view.findViewById(R.id.tvNomeUtente)).setText("Sofia");
        ((TextView) view.findViewById(R.id.tvEtaUtente)).setText("14 anni");
        ((TextView) view.findViewById(R.id.tvOccupazioneUtente)).setText("Studentessa");

        // Obiettivi raggiunti: prendo i due richiesti
        List<Obiettivo> obiettiviRaggiunti = new ArrayList<>();
        List<Obiettivo> all = Obiettivo.getAllObiettivi();
        if (all.size() > 7) obiettiviRaggiunti.add(all.get(7)); // Superare gli attacchi di panico
        if (all.size() > 3) obiettiviRaggiunti.add(all.get(3)); // Avere più motivazione

        RecyclerView recyclerObiettivi = view.findViewById(R.id.recyclerObiettiviRaggiunti);
        recyclerObiettivi.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerObiettivi.setAdapter(new ObiettiviRaggiuntiAdapter(getContext(), obiettiviRaggiunti));

        view.findViewById(R.id.btnIndietro).setOnClickListener(v -> requireActivity().onBackPressed());

        return view;
    }
}
