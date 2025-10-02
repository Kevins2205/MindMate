package com.example.mindmate;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ModalitaImmersivaFragment extends Fragment {

    private TextView tvMotivational;
    private Handler handler = new Handler();
    private int currentIndex = 0;

    private final String[] phrases = new String[]{
            "Respira profondamente e rilassati.",
            "Ogni piccolo passo ti avvicina all'obiettivo.",
            "Hai la forza dentro di te!",
            "Concentrati sul momento presente.",
            "Sei capace di cose incredibili!"
    };

    private Runnable phraseRunnable = new Runnable() {
        @Override
        public void run() {
            // fade out → cambia testo → fade in
            tvMotivational.animate()
                    .alpha(0f)
                    .setDuration(500)
                    .withEndAction(() -> {
                        tvMotivational.setText(phrases[currentIndex]);
                        tvMotivational.animate().alpha(1f).setDuration(500).start();
                    });
            currentIndex = (currentIndex + 1) % phrases.length;
            handler.postDelayed(this, 4000); // cambia ogni 4s
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_modalita_immersiva, container, false);

        tvMotivational = view.findViewById(R.id.tvMotivationalPhrase);
        Button btnStop = view.findViewById(R.id.btnStop);

        // Toast all'avvio
        Toast.makeText(getContext(), "Modalità immersiva attivata", Toast.LENGTH_SHORT).show();

        // Avvia ciclo frasi
        handler.post(phraseRunnable);

        btnStop.setOnClickListener(v -> {
            handler.removeCallbacks(phraseRunnable);

            // Torna a AttivitaFragment
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new AttivitaFragment())
                    .commit();

            // Toast motivazionale
            Toast.makeText(getContext(),
                    "Complimenti! Hai fatto qualcosa per te e hai completato l'attività.",
                    Toast.LENGTH_LONG).show();
        });

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        handler.removeCallbacks(phraseRunnable);
    }
}
