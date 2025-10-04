package com.example.mindmate;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class IspirazioneFragment extends Fragment {
    public static boolean ispirazioneCompletataOggi = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_ispirazione, container, false);

        ConstraintLayout audioItem1 = root.findViewById(R.id.audioItem1);
        ConstraintLayout audioItem2 = root.findViewById(R.id.audioItem2);
        ConstraintLayout audioItem3 = root.findViewById(R.id.audioItem3);
        audioItem1.setOnClickListener(v -> apriDettaglio("audio", "audio1", "Andrea Giuliodori - Cosa ti impedisce di MIGLIORARE?"));
        audioItem2.setOnClickListener(v -> apriDettaglio("audio", "audio2", "Gianluca Gotto - Come scegliere, ogni giorno, se stessi"));
        audioItem3.setOnClickListener(v -> apriDettaglio("audio", "audio3", "One More Time - Paolo Crepet e gli psichiatri"));

        // Tasto indietro: torna alla home
        View btnIndietro = root.findViewById(R.id.buttonIndietroIspirazione);
        if (btnIndietro != null) {
            btnIndietro.setOnClickListener(v -> {
                requireActivity().getSupportFragmentManager().popBackStackImmediate(null, 1); // Torna direttamente alla home
            });
        }

        // Gestione click sulle copertine e pulsanti play
        ImageView coverPrincipale = root.findViewById(R.id.imgCoverPrincipale);
        ImageView btnPlayPrincipale = root.findViewById(R.id.btnPlayPrincipale);
        ImageView coverCorrelato1 = root.findViewById(R.id.imgCoverCorrelato1);
        ImageView btnPlayCorrelato1 = root.findViewById(R.id.btnPlayCorrelato1);
        ImageView coverCorrelato2 = root.findViewById(R.id.imgCoverCorrelato2);
        ImageView btnPlayCorrelato2 = root.findViewById(R.id.btnPlayCorrelato2);

        View.OnClickListener openDettaglioPrincipale = v -> apriDettaglio("video", "videoPrincipale", "INARRESTABILE");
        View.OnClickListener openDettaglioCorrelato1 = v -> apriDettaglio("video", "videoCorrelato1", "Perchè cadiamo");
        View.OnClickListener openDettaglioCorrelato2 = v -> apriDettaglio("video", "videoCorrelato2", "Ritrova la tua vera forza, non arrenderti mai!");

        if (coverPrincipale != null) coverPrincipale.setOnClickListener(openDettaglioPrincipale);
        if (btnPlayPrincipale != null) btnPlayPrincipale.setOnClickListener(openDettaglioPrincipale);
        if (coverCorrelato1 != null) coverCorrelato1.setOnClickListener(openDettaglioCorrelato1);
        if (btnPlayCorrelato1 != null) btnPlayCorrelato1.setOnClickListener(openDettaglioCorrelato1);
        if (coverCorrelato2 != null) coverCorrelato2.setOnClickListener(openDettaglioCorrelato2);
        if (btnPlayCorrelato2 != null) btnPlayCorrelato2.setOnClickListener(openDettaglioCorrelato2);

        // Imposta titoli video con massimo 2 parole
        TextView tvTitoloPrincipale = root.findViewById(R.id.tvTitoloPrincipale);
        TextView tvTitoloCorrelato1 = root.findViewById(R.id.tvTitoloCorrelato1);
        TextView tvTitoloCorrelato2 = root.findViewById(R.id.tvTitoloCorrelato2);
        if (tvTitoloPrincipale != null) tvTitoloPrincipale.setText(getFirstWords(getString(R.string.titolo_video_principale), 2));
        if (tvTitoloCorrelato1 != null) tvTitoloCorrelato1.setText(getFirstWords(getString(R.string.titolo_video_correlato1), 2));
        if (tvTitoloCorrelato2 != null) tvTitoloCorrelato2.setText(getFirstWords(getString(R.string.titolo_video_correlato2), 2));

        return root;
    }

    private void apriDettaglio(String tipo, String id, String titolo) {
        DettaglioIspirazioneFragment dettaglio = new DettaglioIspirazioneFragment();
        Bundle args = new Bundle();
        args.putString("tipo", tipo);
        args.putString("id", id);
        args.putString("titolo", titolo);
        dettaglio.setArguments(args);
        FragmentTransaction ft = requireActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_container, dettaglio);
        ft.addToBackStack(null);
        ft.commit();
    }

    private String getFirstWords(String text, int maxWords) {
        if (text == null) return "";
        String[] parts = text.trim().split("\\s+");
        StringBuilder sb = new StringBuilder();
        int limit = Math.min(parts.length, maxWords);
        for (int i = 0; i < limit; i++) {
            if (i > 0) sb.append(" ");
            sb.append(parts[i]);
        }
        if (parts.length > maxWords) sb.append("...");
        return sb.toString();
    }
}
