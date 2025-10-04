package com.example.mindmate;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.mindmate.ui.CerchioAnimatoView;

public class RespirazioneFragment extends Fragment {
    private TextView textIstruzione;
    private TextView textCicliRimanenti;
    private CerchioAnimatoView cerchioAnimato;
    private Button buttonStart;
    private Button buttonIndietro;
    private CountDownTimer timer;
    private int fase = 0;
    private int NUM_CICLI = 7;
    private final FaseRespiro[] fasi = new FaseRespiro[] {
            new FaseRespiro("Inspira 5 secondi...", CerchioAnimatoView.StatoRespiro.INSPIRA, 5000L),
            new FaseRespiro("Trattieni 5 secondi...", CerchioAnimatoView.StatoRespiro.TRATTIENI, 5000L),
            new FaseRespiro("Espira 5 secondi...", CerchioAnimatoView.StatoRespiro.ESPIRA, 5000L)
    };
    private long tempoTotale = 60 * 1000L; // 1 minuto
    private long tempoTrascorso = 0L;

    private int getCicliPerEmoji() {
        // Indici: 0=😔, 1=😍, 2=😊, 3=😰, 4=😭, 5=🤔, 6=😡, 7=😴, 8=😅
        int emojiIndex = -1;
        String dataOggi = new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date());
        if (com.example.mindmate.DiarioFragment.noteTemporanee != null) {
            for (Object obj : com.example.mindmate.DiarioFragment.noteTemporanee) {
                if (obj instanceof com.example.mindmate.DiarioFragment.NotaTemp) {
                    com.example.mindmate.DiarioFragment.NotaTemp n = (com.example.mindmate.DiarioFragment.NotaTemp) obj;
                    if (n.data.equals(dataOggi)) {
                        emojiIndex = n.emojiIndex;
                        break;
                    }
                }
            }
        }
        // Felici: 1,2,8 | Tristi: 0,3,4,6 | Neutre: 5,7
        if (emojiIndex == 1 || emojiIndex == 2 || emojiIndex == 8) return 2; // Felice
        if (emojiIndex == 0 || emojiIndex == 3 || emojiIndex == 4 || emojiIndex == 6) return 12; // Triste/ansioso
        if (emojiIndex == 5 || emojiIndex == 7) return 7; // Neutro
        return 7; // Default
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        NUM_CICLI = getCicliPerEmoji();
        View view = inflater.inflate(R.layout.fragment_respirazione, container, false);
        textIstruzione = view.findViewById(R.id.textIstruzione);
        textCicliRimanenti = view.findViewById(R.id.textCicliRimanenti);
        cerchioAnimato = view.findViewById(R.id.cerchioAnimato);
        buttonStart = view.findViewById(R.id.buttonStart);
        buttonIndietro = view.findViewById(R.id.buttonIndietro);
        buttonStart.setOnClickListener(v -> avviaRespirazione());
        buttonIndietro.setOnClickListener(v -> requireActivity().getSupportFragmentManager().popBackStack());
        resetSchermata();
        return view;
    }

    private void avviaRespirazione() {
        /*if(HomeFragment.respirazioneCompletataOggi) {
            // Se già completata oggi, mostra messaggio e non far ripartire
            android.widget.Toast.makeText(getContext(), "Hai completato già l'esercizio di respirazione ma puoi rifarlo!", android.widget.Toast.LENGTH_LONG).show();
            return;
        }*/ // Ora si può rifare più volte al giorno perche secondo me è utile e non crea problemi
        fase = 0;
        tempoTrascorso = 0L;
        buttonStart.setEnabled(false);
        buttonStart.setAlpha(0.5f);
        aggiornaCicliRimanenti();
        eseguiFase();

    }

    private void eseguiFase() {
        if (tempoTrascorso >= tempoTotale || fase / fasi.length >= NUM_CICLI) {
            textIstruzione.setText("Esercizio completato!");
            cerchioAnimato.impostaStato(CerchioAnimatoView.StatoRespiro.FERMO, 0, "");
            buttonStart.setEnabled(true);
            textCicliRimanenti.setText("Cicli rimanenti: 0");
            salvaCompletamentoRespirazione();
            buttonStart.setAlpha(1.0f);
            return;
        }
        FaseRespiro faseAttuale = fasi[fase % fasi.length];
        textIstruzione.setText(faseAttuale.testo);
        cerchioAnimato.impostaStato(faseAttuale.stato, faseAttuale.durata, "");
        aggiornaCicliRimanenti();
        if (timer != null) timer.cancel();
        timer = new CountDownTimer(faseAttuale.durata, 100) {
            @Override
            public void onTick(long millisUntilFinished) {
                int secondi = (int) (millisUntilFinished / 1000);
                int centesimi = (int) ((millisUntilFinished % 1000) / 100);
                cerchioAnimato.aggiornaTimer(String.format("-%02d:%02d", secondi, centesimi));
            }
            @Override
            public void onFinish() {
                tempoTrascorso += faseAttuale.durata;
                fase++;
                eseguiFase();
            }
        };
        timer.start();
    }

    private void resetSchermata() {
        textIstruzione.setText("Premi Start per iniziare!");
        cerchioAnimato.impostaStato(CerchioAnimatoView.StatoRespiro.FERMO, 0, "");
        cerchioAnimato.aggiornaTimer("");
        buttonStart.setEnabled(true);
        textCicliRimanenti.setText("Cicli rimanenti: " + NUM_CICLI);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (timer != null) timer.cancel();
    }

    private void aggiornaCicliRimanenti() {
        int cicliRimasti = NUM_CICLI - (fase / fasi.length);
        if (cicliRimasti < 0) cicliRimasti = 0;
        textCicliRimanenti.setText("Cicli rimanenti: " + cicliRimasti);
    }

    private void salvaCompletamentoRespirazione() {
        String dataOggi = new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date());
        android.content.SharedPreferences prefs = requireContext().getSharedPreferences("mindmate_prefs", android.content.Context.MODE_PRIVATE);
        prefs.edit().putBoolean("respirazione_completata_" + dataOggi, true).apply();
        HomeFragment.respirazioneCompletataOggi = true;
    }

    private static class FaseRespiro {
        String testo;
        CerchioAnimatoView.StatoRespiro stato;
        long durata;
        FaseRespiro(String testo, CerchioAnimatoView.StatoRespiro stato, long durata) {
            this.testo = testo;
            this.stato = stato;
            this.durata = durata;
        }
    }
}
