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

public class EventDetailFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_event_detail, container, false);

        ImageView eventImage = view.findViewById(R.id.event_image);
        TextView eventTitle = view.findViewById(R.id.event_title);
        TextView eventDate = view.findViewById(R.id.event_date);
        TextView eventLocation = view.findViewById(R.id.event_location);
        TextView eventDescription = view.findViewById(R.id.event_description);
        Button btnParticipate = view.findViewById(R.id.btn_participate);
        Button btnSave = view.findViewById(R.id.btn_save);

        // Esempio di dati statici, da sostituire con dati reali tramite Bundle/argomenti
        eventTitle.setText("Evento di esempio");
        eventDate.setText("24 Settembre 2025, 18:00");
        eventLocation.setText("Milano, Piazza Duomo");
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

