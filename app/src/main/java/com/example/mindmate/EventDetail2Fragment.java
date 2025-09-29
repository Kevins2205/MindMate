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

public class EventDetail2Fragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_event_detail2, container, false);
        ImageView eventImage = view.findViewById(R.id.event_detail_image);
        TextView eventTitle = view.findViewById(R.id.event_detail_title);
        TextView eventDate = view.findViewById(R.id.event_detail_date);
        TextView eventPlace = view.findViewById(R.id.event_detail_place);
        TextView eventDescription = view.findViewById(R.id.event_detail_description);
        Button btnParticipate = view.findViewById(R.id.btn_event_participate);
        Button btnSave = view.findViewById(R.id.btn_event_save);

        // Esempio dati statici
        eventTitle.setText("Evento di esempio");
        eventDate.setText("24/09/2025, 18:00");
        eventPlace.setText("Milano, Piazza Duomo");
        eventDescription.setText("Descrizione dettagliata dell'evento.");
        eventImage.setImageResource(R.drawable.ic_event_marker);

        btnParticipate.setOnClickListener(v -> {
            // Azione per partecipare all'evento
        });
        btnSave.setOnClickListener(v -> {
            // Azione per salvare l'evento
        });

        return view;
    }
}

