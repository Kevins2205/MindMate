package com.example.mindmate;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ProssimiEventiFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_prossimi_eventi, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerProssimiEventi);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Mostra la lista reale degli eventi prenotati
        List<Evento> eventi = Evento.eventiPrenotati;
        ProssimiEventiAdapter adapter = new ProssimiEventiAdapter(getContext(), eventi);
        recyclerView.setAdapter(adapter);

        return view;
    }
}
