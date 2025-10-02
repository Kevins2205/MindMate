package com.example.mindmate;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.util.HashMap;
import java.util.Map;

public class AttivitaFragment extends Fragment {

    static class Attivita {
        String titolo;
        String descrizione;
        Attivita(String titolo, String descrizione) {
            this.titolo = titolo;
            this.descrizione = descrizione;
        }
    }

    private final Map<Integer, Attivita> attivitaMap = new HashMap<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_attivita, container, false);

        // Popoliamo la mappa con descrizioni dettagliate
        attivitaMap.put(R.id.cardYoga, new Attivita("Yoga 🧘",
                "Lo Yoga combina stretching, respirazione e concentrazione per rilassare corpo e mente. " +
                        "Aiuta a migliorare flessibilità, equilibrio e postura, oltre a ridurre stress e tensione muscolare.\n\n" +
                        "⏱ Durata consigliata: 20–40 minuti"));

        attivitaMap.put(R.id.cardBallare, new Attivita("Ballare 💃",
                "Un'attività divertente e dinamica che combina esercizio fisico e ritmo musicale. " +
                        "Migliora coordinazione, resistenza, equilibrio e aiuta a bruciare calorie in modo piacevole.\n\n" +
                        "⏱ Durata consigliata: 15–30 minuti"));

        attivitaMap.put(R.id.cardCorsa, new Attivita("Corsa 🏃",
                "Allenamento cardio classico che rafforza gambe, cuore e capacità respiratoria. " +
                        "Ideale per scaricare stress, migliorare resistenza e mantenere il cuore in salute.\n\n" +
                        "⏱ Durata consigliata: 20–45 minuti"));

        attivitaMap.put(R.id.cardHiit, new Attivita("HIIT 🔥",
                "Allenamento ad alta intensità che alterna brevi esplosioni di esercizio intenso a pause ridotte. " +
                        "Perfetto per migliorare forza, resistenza e metabolismo, ottimizzando i risultati in poco tempo.\n\n" +
                        "⏱ Durata consigliata: 10–20 minuti"));

        attivitaMap.put(R.id.cardPilates, new Attivita("Pilates 🤸",
                "Metodo di allenamento che unisce respirazione, postura e controllo muscolare. " +
                        "Aiuta a rafforzare il core, migliorare flessibilità, prevenire dolori alla schiena e tonificare il corpo.\n\n" +
                        "⏱ Durata consigliata: 20–40 minuti"));

        attivitaMap.put(R.id.cardPasseggiata, new Attivita("Passeggiata col cane 🐕",
                "Attività leggera e rilassante, ideale per godere dell’aria aperta e ridurre stress. " +
                        "Favorisce circolazione, tono muscolare e benessere psicologico.\n\n" +
                        "⏱ Durata consigliata: 20–30 minuti"));

        attivitaMap.put(R.id.cardBici, new Attivita("Giro in bici 🚴",
                "Allenamento a basso impatto per gambe e sistema cardiovascolare. " +
                        "Può essere svolto all'aperto o su cyclette, ottimo per tonificare gambe e migliorare resistenza.\n\n" +
                        "⏱ Durata consigliata: 30–60 minuti"));

        attivitaMap.put(R.id.cardAllenamentoCorsa, new Attivita("Corsa allenamento 🏃‍♂️",
                "Corsa strutturata con variazioni di ritmo (lento, medio, sprint) per migliorare resistenza e capacità cardiovascolare. " +
                        "Perfetta per chi vuole allenarsi in modo mirato e monitorare i progressi.\n\n" +
                        "⏱ Durata consigliata: 25–50 minuti"));


        // Listener unico
        View.OnClickListener listener = v -> {
            Attivita attivita = attivitaMap.get(v.getId());
            if (attivita != null) {
                // Apriamo il DettaglioAttivitaFragment con bundle
                DettaglioAttivitaFragment dettaglioFragment = new DettaglioAttivitaFragment();
                Bundle bundle = new Bundle();
                bundle.putString("TITOLO", attivita.titolo);
                bundle.putString("DESCRIZIONE", attivita.descrizione);
                dettaglioFragment.setArguments(bundle);

                FragmentTransaction ft = getParentFragmentManager().beginTransaction();
                ft.replace(R.id.fragment_container, dettaglioFragment);
                ft.addToBackStack(null); // consente il tasto indietro
                ft.commit();
            }
        };

        // Assegniamo il listener a tutte le card
        for (int id : attivitaMap.keySet()) {
            CardView card = view.findViewById(id);
            if (card != null) card.setOnClickListener(listener);
        }

        return view;
    }
}
