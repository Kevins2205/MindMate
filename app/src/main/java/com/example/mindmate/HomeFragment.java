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
import com.example.mindmate.Obiettivo;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class HomeFragment extends Fragment {

    // Variabile statica per lo stato temporaneo della respirazione
    protected static boolean respirazioneCompletataOggi = false;
    private List<Obiettivo> obiettiviUtente = new ArrayList<>();
    private ImageView obiettivo1Img, obiettivo2Img, obiettivo3Img;
    private ImageView xTask1, xTask2, xTask3, btnAddObiettivo;

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
        // --- LOGICA OBIETTIVI ---
        obiettivo1Img = root.findViewById(R.id.obiettivo1_img);
        obiettivo2Img = root.findViewById(R.id.obiettivo2_img);
        obiettivo3Img = root.findViewById(R.id.obiettivo3_img);
        xTask1 = root.findViewById(R.id.x_task1);
        xTask2 = root.findViewById(R.id.x_task2);
        xTask3 = root.findViewById(R.id.x_task3);
        btnAddObiettivo = root.findViewById(R.id.btn_add_obiettivo);

        // Inizializza la lista obiettivi utente (esempio: vuota o da storage)
        if (obiettiviUtente.isEmpty()) {
            // Protezione: se la lista totale ha meno di 3 elementi, non usare subList che lancia eccezione
            List<Obiettivo> all = Obiettivo.getAllObiettivi();
            int take = Math.min(3, all.size());
            obiettiviUtente.addAll(all.subList(0, take)); // Demo: primi N
        }
        aggiornaObiettiviUI();

        xTask1.setOnClickListener(v -> {
            if (obiettiviUtente.size() > 0) {
                obiettiviUtente.remove(0);
                aggiornaObiettiviUI();
            }
        });
        xTask2.setOnClickListener(v -> {
            if (obiettiviUtente.size() > 1) {
                obiettiviUtente.remove(1);
                aggiornaObiettiviUI();
            }
        });
        xTask3.setOnClickListener(v -> {
            if (obiettiviUtente.size() > 2) {
                obiettiviUtente.remove(2);
                aggiornaObiettiviUI();
            }
        });

        // Riceve il risultato dall'ObiettiviFragment
        getParentFragmentManager().setFragmentResultListener(ObiettiviFragment.RESULT_KEY, getViewLifecycleOwner(), (requestKey, bundle) -> {
            Object ser = bundle.getSerializable("obiettivi_scelti");
            if (ser instanceof List<?>) {
                // Calcola quali obiettivi sono stati aggiunti rispetto alla lista precedente
                List<Obiettivo> nuovi = new ArrayList<>();
                for (Object o : (List<?>) ser) {
                    if (o instanceof Obiettivo) nuovi.add((Obiettivo) o);
                }
                List<Obiettivo> aggiunti = new ArrayList<>();
                for (Obiettivo o : nuovi) {
                    if (!obiettiviUtente.contains(o)) {
                        aggiunti.add(o);
                    }
                }
                // Aggiorna la UI con la nuova lista
                obiettiviUtente.clear();
                obiettiviUtente.addAll(nuovi);
                aggiornaObiettiviUI();

                // Mostra il toast per ogni obiettivo appena aggiunto
                for (Obiettivo a : aggiunti) {
                    String msg = "Hai aggiunto \"" + a.getTitolo() + "\"! Bravo!";
                    Toast.makeText(requireContext(), msg, Toast.LENGTH_LONG).show();
                }
            }
        });

        // Apri il fragment Obiettivi invece dell'activity
        // NOTE: il listener per btnAddObiettivo viene gestito da aggiornaObiettiviUI() per mantenere la visibilità corretta

        return root;
    }

    private void aggiornaObiettiviUI() {
        // Aggiorna immagini e visibilità in base agli obiettivi scelti
        ImageView[] imgs = {obiettivo1Img, obiettivo2Img, obiettivo3Img};
        ImageView[] xs = {xTask1, xTask2, xTask3};
        for (int i = 0; i < 3; i++) {
            if (i < obiettiviUtente.size()) {
                imgs[i].setVisibility(View.VISIBLE);
                imgs[i].setImageResource(obiettiviUtente.get(i).getIconaResId());
                xs[i].setVisibility(View.VISIBLE);
            } else {
                // Usa GONE così lo spazio viene rimosso e il bottone + si posiziona subito dopo l'ultimo obiettivo
                imgs[i].setVisibility(View.GONE);
                xs[i].setVisibility(View.GONE);
            }
        }

        // Nasconde il tasto + se ci sono già 3 obiettivi, altrimenti lo mostra e assegna il listener
        boolean full = obiettiviUtente.size() >= 3;
        if (btnAddObiettivo != null) {
            if (full) {
                btnAddObiettivo.setVisibility(View.GONE);
            } else {
                btnAddObiettivo.setVisibility(View.VISIBLE);
                btnAddObiettivo.setAlpha(1.0f);
                btnAddObiettivo.setEnabled(true);
                btnAddObiettivo.setClickable(true);
                // Imposta il comportamento per aprire ObiettiviFragment
                btnAddObiettivo.setOnClickListener(v -> {
                    FragmentTransaction ft = requireActivity().getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.fragment_container, ObiettiviFragment.newInstance(new ArrayList<>(obiettiviUtente)));
                    ft.addToBackStack(null);
                    ft.commit();
                });
            }
        }
    }
}
