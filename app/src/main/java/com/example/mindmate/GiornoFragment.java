package com.example.mindmate;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class GiornoFragment extends Fragment {
    private static final String ARG_DATE = "date";
    private Date giorno;

    public static GiornoFragment newInstance(Date giorno) {
        GiornoFragment fragment = new GiornoFragment();
        Bundle args = new Bundle();
        args.putLong(ARG_DATE, giorno.getTime());
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Usa lo stesso layout della home
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        if (getArguments() != null) {
            giorno = new Date(getArguments().getLong(ARG_DATE));
        } else {
            giorno = new Date();
        }
        // Imposta la data
        TextView textDate = root.findViewById(R.id.textDate);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        textDate.setText(sdf.format(giorno));
        // Frase del giorno
        TextView dailyPhraseText = root.findViewById(R.id.dailyPhraseText);
        // Task e pillole
        ImageView pill1 = root.findViewById(R.id.task1_img);
        ImageView pill2 = root.findViewById(R.id.task2_img);
        ImageView pill3 = root.findViewById(R.id.task3_img);
        View task1 = root.findViewById(R.id.task1);
        // Se hai altri task, aggiungi qui (es. task2_img, task3_img...)
        // Gestione frecce
        View arrowLeft = root.findViewById(R.id.arrowLeft);
        View arrowRight = root.findViewById(R.id.arrowRight);
        Date oggi = new Date();
        Calendar calGiorno = Calendar.getInstance();
        calGiorno.setTime(giorno);
        Calendar calOggi = Calendar.getInstance();
        calOggi.setTime(oggi);
        // Disabilita tutti gli altri click
        if (task1 != null) task1.setOnClickListener(null);
        // ...aggiungi qui altri setOnClickListener(null) per altri task se presenti...
        // Logica visualizzazione pillole e frase
        if (calGiorno.after(calOggi)) {
            // Giorno futuro: tutte pillole blu, frase "torna domani per scoprirla!"
            if (pill1 != null) pill1.setImageResource(R.drawable.pill);
            // ...aggiungi qui per altri task: pill2.setImageResource(R.drawable.pill); ...
            dailyPhraseText.setText("torna domani per \nscoprirla!");
            // Freccia destra nascosta, sinistra riporta a oggi
            arrowRight.setVisibility(View.GONE);
            arrowLeft.setVisibility(View.VISIBLE);
            arrowLeft.setOnClickListener(v -> {
                requireActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new HomeFragment())
                    .addToBackStack(null)
                    .commit();
            });
        } else if (calGiorno.before(calOggi)) {
            // Giorno passato: copia della home di oggi (stessi task, stesse pillole, stessa frase)
            boolean notaPresente = false;
            if (DiarioFragment.noteTemporanee != null) {
                for (DiarioFragment.NotaTemp n : DiarioFragment.noteTemporanee) {
                    if (n.data.equals(new SimpleDateFormat("yyyy-MM-dd").format(giorno))) {
                        notaPresente = true;
                        break;
                    }
                }
            }
            if (pill1 != null) {
                if (notaPresente) {
                    pill1.setImageResource(R.drawable.pillgreen);
                } else {
                    pill1.setImageResource(R.drawable.pill);
                }
            }
            if (pill2 != null) {
                    pill2.setImageResource(R.drawable.pillgreen);
            }
            if (pill3 != null) {
                    pill3.setImageResource(R.drawable.pillgreen);
            }

            dailyPhraseText.setText(getString(R.string.phrase_text_past));
            // Freccia sinistra nascosta, destra riporta a oggi
            arrowLeft.setVisibility(View.GONE);
            arrowRight.setVisibility(View.VISIBLE);
            arrowRight.setOnClickListener(v -> {
                requireActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new HomeFragment())
                    .addToBackStack(null)
                    .commit();
            });
        } else {
            // Oggi: entrambe le frecce visibili ma disabilitate
            boolean notaPresente = false;
            if (DiarioFragment.noteTemporanee != null) {
                for (DiarioFragment.NotaTemp n : DiarioFragment.noteTemporanee) {
                    if (n.data.equals(new SimpleDateFormat("yyyy-MM-dd").format(giorno))) {
                        notaPresente = true;
                        break;
                    }
                }
            }
            if (pill1 != null) {
                if (notaPresente) {
                    pill1.setImageResource(R.drawable.pillgreen);
                } else {
                    pill1.setImageResource(R.drawable.pill);
                }
            }
            dailyPhraseText.setText(getString(R.string.phrase_text));
            arrowLeft.setVisibility(View.VISIBLE);
            arrowRight.setVisibility(View.VISIBLE);
            arrowLeft.setOnClickListener(null);
            arrowRight.setOnClickListener(null);
        }
        return root;
    }
}
