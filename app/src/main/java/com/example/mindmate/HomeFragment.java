package com.example.mindmate;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class HomeFragment extends Fragment {

    // Variabile statica per lo stato temporaneo della respirazione
    protected static boolean respirazioneCompletataOggi = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        // --- LOGICA PILLOLE (nota e respirazione) ---
        View task1 = root.findViewById(R.id.task1);
        ImageView pillImage = root.findViewById(R.id.task1_img);
        String dataOggi = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        boolean notaPresente = false;
        if (DiarioFragment.noteTemporanee != null) {
            for (DiarioFragment.NotaTemp n : DiarioFragment.noteTemporanee) {
                if (n.data.equals(dataOggi)) {
                    notaPresente = true;
                    break;
                }
            }
        }
        if (notaPresente) {
            pillImage.setImageResource(R.drawable.pillgreen);
            if (task1 != null) {
                task1.setOnClickListener(v -> {
                    Toast.makeText(getContext(), "Hai già inserito una nota oggi. Puoi visualizzarla nel diario.", Toast.LENGTH_LONG).show();
                });
            }
        } else {
            pillImage.setImageResource(R.drawable.pill);
            if (task1 != null) {
                task1.setOnClickListener(v -> {
                    FragmentTransaction ft = requireActivity().getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.fragment_container, new NuovaNotaFragment());
                    ft.addToBackStack(null);
                    ft.commit();
                });
            }
        }

        // --- LOGICA RESPIRAZIONE TEMPORANEA ---
        ImageView pillRespira = root.findViewById(R.id.task2_img);
        View task2 = root.findViewById(R.id.task2);
        if (respirazioneCompletataOggi) {
            pillRespira.setImageResource(R.drawable.pillgreen);
            if (task2 != null) {
                task2.setOnClickListener(v -> {
                    Toast.makeText(getContext(), "Hai già completato la respirazione oggi!", Toast.LENGTH_LONG).show();
                });
            }
        } else {
            pillRespira.setImageResource(R.drawable.pill);
            if (task2 != null) {
                task2.setOnClickListener(v -> {
                    FragmentTransaction ft = requireActivity().getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.fragment_container, new RespirazioneFragment());
                    ft.addToBackStack(null);
                    ft.commit();
                });
            }
        }

        // --- LOGICA GIORNO (frecce) ---
        TextView arrowLeft = root.findViewById(R.id.arrowLeft);
        TextView arrowRight = root.findViewById(R.id.arrowRight);
        TextView textDate = root.findViewById(R.id.textDate);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Calendar cal = Calendar.getInstance();
        textDate.setText(sdf.format(cal.getTime()));
        arrowLeft.setOnClickListener(v -> {
            cal.add(Calendar.DAY_OF_MONTH, -1);
            requireActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, GiornoFragment.newInstance(cal.getTime()))
                    .addToBackStack(null)
                    .commit();
        });
        arrowRight.setOnClickListener(v -> {
            cal.add(Calendar.DAY_OF_MONTH, 1);
            requireActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, GiornoFragment.newInstance(cal.getTime()))
                    .addToBackStack(null)
                    .commit();
        });

        // --- LOGICA ATTIVITÀ FISICA (navigazione verso AttivitaFragment) ---
        View taskattivita = root.findViewById(R.id.attivitaFisicaWrapper);
        if (taskattivita != null) {
            taskattivita.setOnClickListener(v -> {
                FragmentTransaction ft = requireActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.fragment_container, new AttivitaFragment());
                ft.addToBackStack(null);
                ft.commit();
            });
        }

        return root;
    }
}
