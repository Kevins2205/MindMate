package com.example.mindmate;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ObiettiviFragment extends Fragment {
    public static final String ARG_GIA_SCELTI = "gia_scelti";
    public static final String RESULT_KEY = "obiettivi_result";
    public static final int MAX_OBIETTIVI = 3;

    private RecyclerView recyclerObiettivi;
    private ObiettiviAdapter adapter;
    private Button btnAggiungi, btnIndietro;

    private List<Obiettivo> obiettiviList;
    private List<Obiettivo> obiettiviGiaAggiunti;
    private List<Obiettivo> obiettiviSelezionati;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_obiettivi, container, false);

        recyclerObiettivi = root.findViewById(R.id.recyclerObiettivi);
        btnAggiungi = root.findViewById(R.id.btnAggiungi);
        btnIndietro = root.findViewById(R.id.btnIndietro);

        obiettiviGiaAggiunti = new ArrayList<>();
        Serializable ser = null;
        if (getArguments() != null) ser = getArguments().getSerializable(ARG_GIA_SCELTI);
        if (ser instanceof List<?>) {
            for (Object o : (List<?>) ser) {
                if (o instanceof Obiettivo) obiettiviGiaAggiunti.add((Obiettivo) o);
            }
        }

        obiettiviSelezionati = new ArrayList<>();
        obiettiviList = Obiettivo.getAllObiettivi();

        adapter = new ObiettiviAdapter(obiettiviList, obiettiviGiaAggiunti, obiettiviSelezionati, this::onObiettivoClick);

        if (recyclerObiettivi != null) {
            recyclerObiettivi.setLayoutManager(new GridLayoutManager(requireContext(), 2));
            recyclerObiettivi.setAdapter(adapter);
        }

        btnAggiungi.setOnClickListener(v -> {
            int allowed = MAX_OBIETTIVI - obiettiviGiaAggiunti.size();
            if (obiettiviSelezionati.size() > allowed) {
                Toast.makeText(requireContext(), "Puoi selezionare massimo " + allowed + " obiettivi!", Toast.LENGTH_SHORT).show();
                return;
            }
            if (obiettiviSelezionati.isEmpty()) {
                // Non dovrebbe mai essere cliccabile, ma per sicurezza
                Toast.makeText(requireContext(), "Seleziona almeno un obiettivo.", Toast.LENGTH_SHORT).show();
                return;
            }
            ArrayList<Obiettivo> result = new ArrayList<>(obiettiviGiaAggiunti);
            result.addAll(obiettiviSelezionati);
            Bundle bundle = new Bundle();
            bundle.putSerializable("obiettivi_scelti", result);
            getParentFragmentManager().setFragmentResult(RESULT_KEY, bundle);
            // Torna alla Home
            FragmentManager fm = getParentFragmentManager();
            fm.popBackStack();
        });

        btnIndietro.setOnClickListener(v -> getParentFragmentManager().popBackStack());

        updateAggiungiButton();
        return root;
    }

    private void updateAggiungiButton() {
        if (btnAggiungi != null) {
            btnAggiungi.setEnabled(!obiettiviSelezionati.isEmpty());
            btnAggiungi.setAlpha(obiettiviSelezionati.isEmpty() ? 0.5f : 1.0f);
        }
    }

    private void onObiettivoClick(Obiettivo obiettivo) {
        if (obiettiviGiaAggiunti.contains(obiettivo)) {
            Toast.makeText(requireContext(), "Hai già aggiunto quest'obiettivo!", Toast.LENGTH_SHORT).show();
            return;
        }
        int allowed = MAX_OBIETTIVI - obiettiviGiaAggiunti.size();
        if (obiettiviSelezionati.contains(obiettivo)) {
            obiettiviSelezionati.remove(obiettivo);
        } else {
            if (obiettiviSelezionati.size() >= allowed) {
                Toast.makeText(requireContext(), "Puoi selezionare massimo " + allowed + " obiettivi!", Toast.LENGTH_SHORT).show();
                return;
            }
            obiettiviSelezionati.add(obiettivo);
        }
        adapter.notifyDataSetChanged();
        updateAggiungiButton();
    }

    // Factory helper per passare gli obiettivi già scelti
    public static ObiettiviFragment newInstance(ArrayList<Obiettivo> giaScelti) {
        ObiettiviFragment f = new ObiettiviFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_GIA_SCELTI, giaScelti);
        f.setArguments(args);
        return f;
    }
}
