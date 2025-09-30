package com.example.mindmate;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.util.Calendar;
import java.util.HashMap;

public class DiarioFragment extends Fragment {

    private TextView textMeseAnno;
    private GridLayout calendarioGrid;
    private Calendar calendario;

    // 🔹 Giorni con diario preimpostato (demo)
    private HashMap<String, VoceDiario> diarioFinto;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_diario, container, false);

        TextView textMeseAnno = view.findViewById(R.id.monthText);
        GridLayout calendarioGrid = view.findViewById(R.id.calendarGrid);
        TextView btnPrev = view.findViewById(R.id.arrowLeft);
        TextView btnNext = view.findViewById(R.id.arrowRight);


        calendario = Calendar.getInstance();

        // 🔹 Dati finti
        diarioFinto = new HashMap<>();
        diarioFinto.put("2025-05-03", new VoceDiario("😰", "Oggi mi sento abbastanza ansiosa perché…"));
        diarioFinto.put("2025-04-13", new VoceDiario("😊", "Giornata positiva, sono riuscita a rilassarmi."));
        diarioFinto.put("2025-04-20", new VoceDiario("😡", "Un po' di stress, ma sto migliorando."));

        aggiornaCalendario();

        btnPrev.setOnClickListener(v -> {
            calendario.add(Calendar.MONTH, -1);
            aggiornaCalendario();
        });

        btnNext.setOnClickListener(v -> {
            calendario.add(Calendar.MONTH, 1);
            aggiornaCalendario();
        });

        return view;
    }

    private void aggiornaCalendario() {
        // Mostra mese/anno
        String[] mesi = {"Gennaio","Febbraio","Marzo","Aprile","Maggio","Giugno",
                "Luglio","Agosto","Settembre","Ottobre","Novembre","Dicembre"};
        textMeseAnno.setText(mesi[calendario.get(Calendar.MONTH)] + " " + calendario.get(Calendar.YEAR));

        calendarioGrid.removeAllViews();

        // Calcolo giorni
        Calendar temp = (Calendar) calendario.clone();
        temp.set(Calendar.DAY_OF_MONTH, 1);
        int primoGiornoSettimana = temp.get(Calendar.DAY_OF_WEEK) - 1; // domenica=1
        int giorniMese = calendario.getActualMaximum(Calendar.DAY_OF_MONTH);

        // Vuoti prima del primo giorno
        for (int i = 0; i < primoGiornoSettimana; i++) {
            TextView empty = new TextView(getContext());
            calendarioGrid.addView(empty);
        }

        // Giorni
        for (int giorno = 1; giorno <= giorniMese; giorno++) {
            TextView tv = new TextView(getContext());
            tv.setText(String.valueOf(giorno));
            tv.setGravity(android.view.Gravity.CENTER);
            tv.setTextSize(16);
            tv.setPadding(8, 8, 8, 8);

            String key = calendario.get(Calendar.YEAR) + "-" +
                    String.format("%02d", calendario.get(Calendar.MONTH) + 1) + "-" +
                    String.format("%02d", giorno);

            if (diarioFinto.containsKey(key)) {
                tv.setBackgroundResource(R.drawable.bg_day_filled);

                tv.setOnClickListener(v -> {
                    // Apri dettaglio
                    VoceDiario voce = diarioFinto.get(key);
                    DettaglioDiarioFragment dettaglio = DettaglioDiarioFragment.newInstance(key, voce.emoji, voce.testo);

                    FragmentTransaction ft = getParentFragmentManager().beginTransaction();
                    ft.replace(R.id.fragment_container, dettaglio);
                    ft.addToBackStack(null);
                    ft.commit();
                });
            } else {
                tv.setBackgroundResource(R.drawable.bg_day_empty);
            }

            calendarioGrid.addView(tv);
        }
    }
}
