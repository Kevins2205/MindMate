package com.example.mindmate;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class DiarioFragment extends Fragment {

    private TextView textMeseAnno;
    private GridLayout calendarioGrid;
    private Calendar calendario;

    // 🔹 Giorni con diario preimpostato (demo)
    private HashMap<String, VoceDiario> diarioFinto;

    // Lista statica per note temporanee
    public static class NotaTemp {
        public String testo;
        public String data;
        public int emojiIndex;
        public NotaTemp(String testo, String data, int emojiIndex) {
            this.testo = testo;
            this.data = data;
            this.emojiIndex = emojiIndex;
        }
    }
    public static java.util.List<NotaTemp> noteTemporanee = new java.util.ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_diario, container, false);
        textMeseAnno = view.findViewById(R.id.monthText);
        calendarioGrid = view.findViewById(R.id.calendarGrid);
        TextView btnPrev = view.findViewById(R.id.arrowLeft);
        TextView btnNext = view.findViewById(R.id.arrowRight);
        Button btnNuovaNota = view.findViewById(R.id.buttonNuovaNota);
        Button btnIndietro = view.findViewById(R.id.buttonIndietro);
        calendario = Calendar.getInstance();
        diarioFinto = new HashMap<>();
        // Carica note temporanee
        for (NotaTemp n : noteTemporanee) {
            String emoji = getEmojiByIndex(n.emojiIndex);
            diarioFinto.put(n.data, new VoceDiario(emoji, n.testo));
        }
        // Dati finti opzionali
        diarioFinto.put("2025-05-03", new VoceDiario(getString(R.string.emoji1), "Oggi mi sento un po' ansiosa, ma cerco di restare positiva."));
        diarioFinto.put("2025-04-13", new VoceDiario(getString(R.string.emoji2), "Giornata positiva, sono riuscita a rilassarmi."));
        diarioFinto.put("2025-04-20", new VoceDiario(getString(R.string.emoji3), "Un po' di stress, ma sto migliorando."));
        diarioFinto.put("2025-09-05", new VoceDiario(getString(R.string.emoji4), "Settembre è iniziato bene, mi sento motivata!"));
        diarioFinto.put("2025-09-12", new VoceDiario(getString(R.string.emoji5), "Oggi sono un po' stanca, ma soddisfatta del lavoro fatto."));
        diarioFinto.put("2025-09-25", new VoceDiario(getString(R.string.emoji6), "Giornata di riflessione, ho preso decisioni importanti."));
        diarioFinto.put("2025-10-03", new VoceDiario(getString(R.string.emoji7), "Ottobre porta aria fresca, mi sento serena."));
        diarioFinto.put("2025-10-14", new VoceDiario(getString(R.string.emoji8), "Giornata impegnativa, ma sono riuscita a gestire tutto."));
        diarioFinto.put("2025-10-28", new VoceDiario(getString(R.string.emoji9), "Ho passato una bellissima giornata con gli amici."));
        aggiornaCalendario();

        btnPrev.setOnClickListener(v -> {
            calendario.add(Calendar.MONTH, -1);
            aggiornaCalendario();
        });

        btnNext.setOnClickListener(v -> {
            calendario.add(Calendar.MONTH, 1);
            aggiornaCalendario();
        });

        btnNuovaNota.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, new NuovaNotaFragment())
                .addToBackStack(null)
                .commit();
        });

        btnIndietro.setOnClickListener(v -> {
            // Torna direttamente alla Home selezionando la voce Home della bottom navigation
            requireActivity().findViewById(com.example.mindmate.R.id.bottomNavigationView)
                .performClick();
            com.google.android.material.bottomnavigation.BottomNavigationView bottomNav = requireActivity().findViewById(com.example.mindmate.R.id.bottomNavigationView);
            bottomNav.setSelectedItemId(com.example.mindmate.R.id.nav_home);
        });

        return view;
    }

    private void aggiornaCalendario() {
        String[] mesi = {"Gennaio","Febbraio","Marzo","Aprile","Maggio","Giugno",
                "Luglio","Agosto","Settembre","Ottobre","Novembre","Dicembre"};
        textMeseAnno.setText(mesi[calendario.get(Calendar.MONTH)] + " " + calendario.get(Calendar.YEAR));

        calendarioGrid.removeAllViews();

        Calendar temp = (Calendar) calendario.clone();
        temp.set(Calendar.DAY_OF_MONTH, 1);
        int primoGiornoSettimana = temp.get(Calendar.DAY_OF_WEEK) - 2; // lunedì=0, domenica=6
        if (primoGiornoSettimana < 0) primoGiornoSettimana = 6;
        int giorniMese = calendario.getActualMaximum(Calendar.DAY_OF_MONTH);

        int celleTotali = 42; // 6 settimane x 7 giorni
        int giornoCorrente = 1;

        for (int cella = 0; cella < celleTotali; cella++) {
            TextView tv = new TextView(getContext());
            int colonna = cella % 7;
            int riga = cella / 7;
            GridLayout.LayoutParams params = new GridLayout.LayoutParams(
                GridLayout.spec(riga),
                GridLayout.spec(colonna)
            );
            params.setMargins(dpToPx(9), dpToPx(10), dpToPx(10), dpToPx(9));
            params.width = dpToPx(36);
            params.height = dpToPx(36);
            params.setGravity(android.view.Gravity.CENTER);
            tv.setLayoutParams(params);
            tv.setGravity(android.view.Gravity.CENTER);
            tv.setTextSize(15);
            tv.setPadding(0, 0, 0, 0);

            if (cella >= primoGiornoSettimana && giornoCorrente <= giorniMese) {
                tv.setText(String.format("%02d", giornoCorrente));
                String key = calendario.get(Calendar.YEAR) + "-" +
                        String.format("%02d", calendario.get(Calendar.MONTH) + 1) + "-" +
                        String.format("%02d", giornoCorrente);
                if (diarioFinto.containsKey(key)) {
                    tv.setBackgroundResource(R.drawable.bg_day_filled);
                    tv.setTextColor(0xFFFFFFFF);
                    tv.setOnClickListener(v -> {
                        VoceDiario voce = diarioFinto.get(key);
                        DettaglioDiarioFragment dettaglio = DettaglioDiarioFragment.newInstance(key, voce.emoji, voce.testo);
                        FragmentTransaction ft = getParentFragmentManager().beginTransaction();
                        ft.replace(R.id.fragment_container, dettaglio);
                        ft.addToBackStack(null);
                        ft.commit();
                    });
                } else {
                    tv.setBackgroundResource(R.drawable.bg_day_empty);
                    tv.setTextColor(0xFF444444);
                }
                giornoCorrente++;
            } else {
                tv.setText("");
                tv.setBackgroundColor(android.graphics.Color.TRANSPARENT);
            }
            calendarioGrid.addView(tv);
        }
    }

    // Utility per convertire dp in px
    private int dpToPx(int dp) {
        float density = getResources().getDisplayMetrics().density;
        return Math.round((float) dp * density);
    }

    // Utility per ottenere l'emoji dalla posizione
    private String getEmojiByIndex(int idx) {
        String[] emojiList = {
            getString(R.string.emoji1),
            getString(R.string.emoji2),
            getString(R.string.emoji3),
            getString(R.string.emoji4),
            getString(R.string.emoji5),
            getString(R.string.emoji6),
            getString(R.string.emoji7),
            getString(R.string.emoji8),
            getString(R.string.emoji9)
        };
        if (idx >= 0 && idx < emojiList.length) return emojiList[idx];
        return "";
    }
}
