package com.example.mindmate;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class HomeFragment extends Fragment {

    // Variabile statica per lo stato temporaneo della respirazione
    protected static boolean respirazioneCompletataOggi = false;
    protected static boolean attivitaFisicaCompletataOggi = false;
    protected static boolean notaCompletataOggi = false;
    private List<Obiettivo> obiettiviUtente = new ArrayList<>();
    private ImageView obiettivo1Img, obiettivo2Img, obiettivo3Img;
    private TextView obiettivo1Title, obiettivo2Title, obiettivo3Title;
    private TextView obiettivo1Id, obiettivo2Id, obiettivo3Id;
    private ImageView xTask1, xTask2, xTask3;
    private ImageView imgTerra;

    private static final String PREFS_NAME = "obiettivi_prefs";
    private static final String KEY_OBIETTIVI = "obiettivi_utenti_titoli";
    private static final String PREFS_ATTIVITA = "attivita_prefs";
    private static final String KEY_ATTIVITA_COMPLETATA = "attivita_completata_data";
    private static final String KEY_ATTIVITA_DATA = "attivita_data";

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
                    notaCompletataOggi = true;
                    break;
                }
                else notaCompletataOggi = false;
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
                    FragmentTransaction ft = requireActivity().getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.fragment_container, new RespirazioneFragment());
                    ft.addToBackStack(null);
                    ft.commit();
                });
            }
            /*if (task2 != null) {
                task2.setOnClickListener(v -> {
                    Toast.makeText(getContext(), "Hai già completato la respirazione oggi!", Toast.LENGTH_LONG).show();
                });
            }*/ // secondo me da togliere perche che senso avrebbe di privare l'utente di accedere agli esercizi di respirazione
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
        textDate.setText("Oggi, " + sdf.format(cal.getTime()));
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
        obiettivo1Title = root.findViewById(R.id.obiettivo1_title);
        obiettivo2Title = root.findViewById(R.id.obiettivo2_title);
        obiettivo3Title = root.findViewById(R.id.obiettivo3_title);
        obiettivo1Id = root.findViewById(R.id.obiettivo1_id);
        obiettivo2Id = root.findViewById(R.id.obiettivo2_id);
        obiettivo3Id = root.findViewById(R.id.obiettivo3_id);
        xTask1 = root.findViewById(R.id.x_task1);
        xTask2 = root.findViewById(R.id.x_task2);
        xTask3 = root.findViewById(R.id.x_task3);
        View task3 = root.findViewById(R.id.task3);
        if (task3 != null) {
            task3.setOnClickListener(v -> {
                FragmentTransaction ft = requireActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.fragment_container, new IspirazioneFragment());
                ft.addToBackStack(null);
                ft.commit();
            });
        }
        ImageView btnAddObiettivoEmpty = root.findViewById(R.id.btn_add_obiettivo_empty);
        // Click su bottone add centrato
        if (btnAddObiettivoEmpty != null) {
            btnAddObiettivoEmpty.setOnClickListener(v -> {
                FragmentTransaction ft = requireActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.fragment_container, new ObiettiviFragment());
                ft.addToBackStack(null);
                ft.commit();
            });
        }

        // Carica la lista obiettivi utente da SharedPreferences
        obiettiviUtente = caricaObiettiviUtente();
        aggiornaObiettiviUI();

        if (xTask1 != null) {
            xTask1.setOnClickListener(v -> {
                if (obiettiviUtente.size() > 0) {
                    obiettiviUtente.remove(0);
                    aggiornaObiettiviUI();
                }
            });
        }
        if (xTask2 != null) {
            xTask2.setOnClickListener(v -> {
                if (obiettiviUtente.size() > 1) {
                    obiettiviUtente.remove(1);
                    aggiornaObiettiviUI();
                }
            });
        }
        if (xTask3 != null) {
            xTask3.setOnClickListener(v -> {
                if (obiettiviUtente.size() > 2) {
                    obiettiviUtente.remove(2);
                    aggiornaObiettiviUI();
                }
            });
        }
        // Riceve il risultato dall'ObiettiviFragment
        getParentFragmentManager().setFragmentResultListener(ObiettiviFragment.RESULT_KEY, getViewLifecycleOwner(), (requestKey, bundle) -> {
            Object ser = bundle.getSerializable("obiettivi_scelti");
            if (ser instanceof List<?>) {
                List<Obiettivo> nuovi = new ArrayList<>();
                for (Object o : (List<?>) ser) {
                    if (o instanceof Obiettivo) nuovi.add((Obiettivo) o);
                }
                // non servono array temporanei qui: usiamo direttamente la lista 'nuovi'
                // Aggiorna la UI con la nuova lista
                obiettiviUtente.clear();
                obiettiviUtente.addAll(nuovi);
                aggiornaObiettiviUI();
                salvaObiettiviUtente(obiettiviUtente); // Salva sempre la lista aggiornata


                // Mostra sempre il messaggio con gli obiettivi selezionati
                StringBuilder msg = new StringBuilder("I tuoi obiettivi attuali ora sono:\n- ");
                for (int i = 0; i < nuovi.size(); i++) {
                    msg.append(nuovi.get(i).getTitolo());
                    if (i < nuovi.size() - 1) msg.append("\n- ");
                }
                msg.append("\n\nContinua così!");
                new androidx.appcompat.app.AlertDialog.Builder(requireContext())
                        .setTitle("I tuoi Obiettivi")
                        .setMessage(msg.toString())
                        .setPositiveButton("OK", null)
                        .show();
            }
        });

        // Apri il fragment Obiettivi invece dell'activity
        // NOTE: il listener per btnAddObiettivo viene gestito da aggiornaObiettiviUI() per mantenere la visibilità corretta

        // Ripristino la logica per il tasto + orizzontale
        ImageView btnAddObiettivo = root.findViewById(R.id.btn_add_obiettivo);
        if (btnAddObiettivo != null) {
            btnAddObiettivo.setOnClickListener(v -> {
                FragmentTransaction ft = requireActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.fragment_container, new ObiettiviFragment());
                ft.addToBackStack(null);
                ft.commit();
            });
        }

        // --- LOGICA ISPIRAZIONE (pillola verde se completata oggi, solo temporanea) ---
        ImageView pillIspirazione = root.findViewById(R.id.task3_img);
        if (pillIspirazione != null) {
            if (com.example.mindmate.IspirazioneFragment.ispirazioneCompletataOggi) {
                pillIspirazione.setImageResource(R.drawable.pillgreen);
                if (task3 != null) {
                    task3.setOnClickListener(v -> {
                        FragmentTransaction ft = requireActivity().getSupportFragmentManager().beginTransaction();
                        ft.replace(R.id.fragment_container, new IspirazioneFragment());
                        ft.addToBackStack(null);
                        ft.commit();
                    });
                }
                /*if (task3 != null) {
                    task3.setOnClickListener(v -> {
                        Toast.makeText(getContext(), "Hai già completato l'ispirazione oggi!", Toast.LENGTH_LONG).show();
                    });
                }   secondo me da togliere perche che senso avrebbe di privare l'utente di accedere ai video o audio  */
            } else {
                pillIspirazione.setImageResource(R.drawable.pill);
                if (task3 != null) {
                    task3.setOnClickListener(v -> {
                        FragmentTransaction ft = requireActivity().getSupportFragmentManager().beginTransaction();
                        ft.replace(R.id.fragment_container, new IspirazioneFragment());
                        ft.addToBackStack(null);
                        ft.commit();
                    });
                }
            }
        }
        imgTerra = root.findViewById(R.id.imgTerra);
        caricaStatoAttivitaFisica();
        aggiornaImmagineTerra();

        return root;
    }

    private void aggiornaImmagineTerra() {
        int completate = 0;
        if (notaCompletataOggi) completate++;
        if (respirazioneCompletataOggi) completate++;
        if (com.example.mindmate.IspirazioneFragment.ispirazioneCompletataOggi) completate++;
        if (attivitaFisicaCompletataOggi) completate++;
        if (imgTerra != null) {
            int marginBottom=dpToPx(-32);
            int height=dpToPx(90);
            if(completate >= 1) {marginBottom=dpToPx(-15);}
            if(completate >= 2) {marginBottom=dpToPx(-5);}
            if(completate >= 3) {marginBottom=dpToPx(0); height=dpToPx(110);}
            if(completate >= 4) {marginBottom=dpToPx(0); height=dpToPx(145);}
            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) imgTerra.getLayoutParams();
            params.bottomMargin = marginBottom;
            params.height = height;
            imgTerra.setLayoutParams(params);
            if (completate == 1) imgTerra.setImageResource(R.drawable.foglie1);
            else if (completate == 2) imgTerra.setImageResource(R.drawable.foglie2);
            else if (completate == 3) imgTerra.setImageResource(R.drawable.foglie3);
            else if (completate == 4) imgTerra.setImageResource(R.drawable.tuttofatto);
            else imgTerra.setImageResource(R.drawable.terra);
        }
    }

    private int dpToPx(int dp) {
        float density = getResources().getDisplayMetrics().density;
        return Math.round((float) dp * density);
    }

    private void caricaStatoAttivitaFisica() {
        // Ora controlla solo la lista temporanea e la variabile in memoria
        String oggi = new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date());
        attivitaFisicaCompletataOggi = false;
        for (AttivitaCompletataTemp a : attivitaCompletateTemporanee) {
            if (a.titolo.equals("Attività Fisica") && a.data.equals(oggi)) {
                attivitaFisicaCompletataOggi = true;
                break;
            }
        }
    }

    // Classe statica per attività completate temporanee
    public static class AttivitaCompletataTemp {
        public String titolo;
        public String data;
        public AttivitaCompletataTemp(String titolo, String data) {
            this.titolo = titolo;
            this.data = data;
        }
    }
    // Lista statica per attività completate temporanee
    public static List<AttivitaCompletataTemp> attivitaCompletateTemporanee = new ArrayList<>();

    // Quando un'attività viene completata, aggiungi solo alla lista temporanea
    private void segnaAttivitaCompletata(String titolo) {
        String dataOggi = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        attivitaCompletateTemporanee.add(new AttivitaCompletataTemp(titolo, dataOggi));
        // Non salvare più persistentemente
        // SharedPreferences prefs = requireContext().getSharedPreferences(PREFS_ATTIVITA, Context.MODE_PRIVATE);
        // prefs.edit().putString(KEY_ATTIVITA_COMPLETATA, dataOggi).apply();
    }

    // Quando controlli se un'attività è stata completata oggi, verifica solo nella lista temporanea
    private boolean isAttivitaCompletataOggi(String titolo) {
        String dataOggi = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        for (AttivitaCompletataTemp a : attivitaCompletateTemporanee) {
            if (a.titolo.equals(titolo) && a.data.equals(dataOggi)) {
                return true;
            }
        }
        return false;
    }

    private String wrapTitle(String title, int wordsPerLine) {
        if (title == null || title.isEmpty()) return title;
        String[] parts = title.split("\\s+");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < parts.length; i++) {
            sb.append(parts[i]);
            // se non ultimo, decidere se break
            if (i < parts.length - 1) {
                if ((i + 1) % wordsPerLine == 0) sb.append('\n');
                else sb.append(' ');
            }
        }
        return sb.toString();
    }

    // Se il titolo ha più di maxWords parole, lo sintetizza con regole semantiche
    private String summarizeOrFormat(String title, int wordsPerLine, int maxWords) {
        if (title == null || title.trim().isEmpty()) return title;
        String trimmed = title.trim();
        String[] parts = trimmed.split("\\s+");
        if (parts.length > maxWords) {
            String lower = trimmed.toLowerCase(Locale.ROOT);
            if (lower.contains("sonno")) return "Migliorare il Sonno";
            if (lower.contains("autostima")) return "Aumentare l'Autostima";
            if (lower.contains("motivaz")) return "Avere più Motivazione";
            if (lower.contains("stress")) return "Ridurre lo Stress";
            if (lower.contains("rabbia")) return "Gestire la Rabbia";
            if (lower.contains("panico")) return "Gestire il Panico";
            if (lower.contains("rilass")) return "Rilassarsi di più";
            if (lower.contains("prendersi") || lower.contains("cura") || lower.contains("curarsi")) return "Cura di se stessi";
            // fallback: usa prime parole chiave (2 parole) capitalizzate
            StringBuilder sb = new StringBuilder();
            int take = Math.min(2, parts.length);
            for (int i = 0; i < take; i++) {
                if (i > 0) sb.append(' ');
                sb.append(capitalize(parts[i]));
            }
            return sb.toString();
        }
        // altrimenti usa il wrap normale
        return wrapTitle(trimmed, wordsPerLine);
    }

    private String capitalize(String w) {
        if (w == null || w.isEmpty()) return w;
        if (w.length() == 1) return w.toUpperCase(Locale.ROOT);
        return w.substring(0,1).toUpperCase(Locale.ROOT) + w.substring(1);
    }

    private void aggiornaObiettiviUI() {
        // Aggiorna immagini, titoli, id e visibilità in base agli obiettivi scelti
        ImageView[] imgs = {obiettivo1Img, obiettivo2Img, obiettivo3Img};
        ImageView[] xs = {xTask1, xTask2, xTask3};
        TextView[] titles = {obiettivo1Title, obiettivo2Title, obiettivo3Title};
        TextView[] ids = {obiettivo1Id, obiettivo2Id, obiettivo3Id};
        for (int i = 0; i < 3; i++) {
            if (i < obiettiviUtente.size()) {
                imgs[i].setVisibility(View.VISIBLE);
                imgs[i].setImageResource(obiettiviUtente.get(i).getIconaResId());
                xs[i].setVisibility(View.VISIBLE);
                titles[i].setVisibility(View.VISIBLE);
                titles[i].setText(summarizeOrFormat(obiettiviUtente.get(i).getTitolo(), 2, 4));
                ids[i].setVisibility(View.GONE);
            } else {
                imgs[i].setVisibility(View.GONE);
                xs[i].setVisibility(View.GONE);
                titles[i].setVisibility(View.GONE);
                ids[i].setVisibility(View.GONE);
            }
        }
        salvaObiettiviUtente(obiettiviUtente);
        // Ripristino la logica per il tasto + orizzontale
        // Mostra il tasto + solo se ci sono meno di 3 obiettivi
        View viewRoot = getView();
        if (viewRoot != null) {
            ImageView btnAddObiettivo = viewRoot.findViewById(R.id.btn_add_obiettivo);
            if (btnAddObiettivo != null) {
                if (obiettiviUtente.size() < 3 && !obiettiviUtente.isEmpty()) {
                    btnAddObiettivo.setVisibility(View.VISIBLE);
                } else {
                    btnAddObiettivo.setVisibility(View.GONE);
                }
            }
            LinearLayout obiettiviWrapper = viewRoot.findViewById(R.id.obiettiviWrapper);
            androidx.constraintlayout.widget.ConstraintLayout obiettiviEmptyWrapper = viewRoot.findViewById(R.id.obiettiviEmptyWrapper);
            if (obiettiviUtente.isEmpty()) {
                if (obiettiviWrapper != null) obiettiviWrapper.setVisibility(View.GONE);
                if (obiettiviEmptyWrapper != null) obiettiviEmptyWrapper.setVisibility(View.VISIBLE);
            } else {
                if (obiettiviWrapper != null) obiettiviWrapper.setVisibility(View.VISIBLE);
                if (obiettiviEmptyWrapper != null) obiettiviEmptyWrapper.setVisibility(View.GONE);
            }
        }
        // Rimuovo la gestione della visibilità di addHintLabel classica: ora la logica è solo nei due wrapper
    }

    // Salva la lista dei titoli degli obiettivi utente in SharedPreferences
    private void salvaObiettiviUtente(List<Obiettivo> lista) {
        SharedPreferences prefs = requireContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < lista.size(); i++) {
            sb.append(lista.get(i).getTitolo());
            if (i < lista.size() - 1) sb.append(",");
        }
        prefs.edit().putString(KEY_OBIETTIVI, sb.toString()).apply();
    }

    // Carica la lista degli obiettivi utente da SharedPreferences
    private List<Obiettivo> caricaObiettiviUtente() {
        SharedPreferences prefs = requireContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String titoli = prefs.getString(KEY_OBIETTIVI, "");
        List<Obiettivo> result = new ArrayList<>();
        if (!titoli.isEmpty()) {
            String[] split = titoli.split(",");
            List<Obiettivo> all = Obiettivo.getAllObiettivi();
            for (String t : split) {
                for (Obiettivo o : all) {
                    if (o.getTitolo().equals(t)) {
                        result.add(o);
                        break;
                    }
                }
            }
        }
        return result;
    }
}
