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
    private static final String ARG_TITOLO = "TITOLO";
    private static final String ARG_DESCRIZIONE = "DESCRIZIONE";
    private static final String ARG_POSIZIONE = "POSIZIONE";
    private static final String ARG_DATA = "DATA";
    private static final String ARG_GIA_PRENOTATO = "GIA_PRENOTATO";

    public static DettaglioEventoFragment newInstance(Evento evento, boolean giaPrenotato) {
        DettaglioEventoFragment fragment = new DettaglioEventoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TITOLO, evento.getTitolo());
        args.putString(ARG_DESCRIZIONE, evento.getDescrizione());
        args.putString(ARG_POSIZIONE, evento.getPosizione());
        args.putString(ARG_DATA, evento.getData());
        args.putBoolean(ARG_GIA_PRENOTATO, giaPrenotato);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dettaglio_evento, container, false);

        Bundle args = getArguments();
        String titolo = args != null ? args.getString(ARG_TITOLO) : "Titolo Evento";
        String descrizione = args != null ? args.getString(ARG_DESCRIZIONE) : "Descrizione evento";
        String posizione = args != null ? args.getString(ARG_POSIZIONE) : "Posizione evento";
        String data = args != null ? args.getString(ARG_DATA) : "Data evento";
        boolean giaPrenotato = args != null && args.getBoolean(ARG_GIA_PRENOTATO, false);

        TextView tvTitolo = view.findViewById(R.id.tvTitoloEventoDettaglio);
        TextView tvDescrizione = view.findViewById(R.id.tvDescrizioneEventoDettaglio);
        TextView tvPosizione = view.findViewById(R.id.tvPosizioneEventoDettaglio);
        TextView tvData = view.findViewById(R.id.tvDataEventoDettaglio);
        Button btnPartecipa = view.findViewById(R.id.btnPartecipaEvento);
        Button btnIndietro = view.findViewById(R.id.btnIndietroEvento);
        Button btnCancella = view.findViewById(R.id.btnCancellaPrenotazione);

        tvTitolo.setText(titolo);
        tvDescrizione.setText(descrizione);
        tvPosizione.setText(posizione);
        tvData.setText(data);

        // Mostra solo due bottoni: Partecipa/Cancella e Indietro
        if (giaPrenotato) {
            btnPartecipa.setVisibility(View.GONE);
            btnCancella.setVisibility(View.VISIBLE);
            btnIndietro.setVisibility(View.VISIBLE);
            btnCancella.setOnClickListener(v -> {
                for (int i = 0; i < Evento.eventiPrenotati.size(); i++) {
                    Evento e = Evento.eventiPrenotati.get(i);
                    if (e.titolo.equals(titolo) && e.data.equals(data) && e.posizione.equals(posizione)) {
                        Evento.eventiPrenotati.remove(i);
                        break;
                    }
                }
                btnCancella.setVisibility(View.GONE);
                btnPartecipa.setVisibility(View.VISIBLE);
                btnIndietro.setVisibility(View.VISIBLE);
            });
        } else {
            btnPartecipa.setVisibility(View.VISIBLE);
            btnCancella.setVisibility(View.GONE);
            btnIndietro.setVisibility(View.VISIBLE);
            btnPartecipa.setOnClickListener(v -> {
                boolean alreadyPresent = false;
                for (Evento e : Evento.eventiPrenotati) {
                    if (e.titolo.equals(titolo) && e.data.equals(data) && e.posizione.equals(posizione)) {
                        alreadyPresent = true;
                        break;
                    }
                }
                if (!alreadyPresent) {
                    Evento.eventiPrenotati.add(new Evento(titolo, descrizione, posizione, data, 0, 0));
                }
                btnPartecipa.setVisibility(View.GONE);
                btnCancella.setVisibility(View.VISIBLE);
                btnIndietro.setVisibility(View.VISIBLE);
            });
        }

        btnIndietro.setOnClickListener(v -> requireActivity().getSupportFragmentManager().popBackStack());

        return view;
    }
}
