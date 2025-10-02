package com.example.mindmate;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import java.util.Locale;

public class ObiettiviAdapter extends RecyclerView.Adapter<ObiettiviAdapter.ObiettivoViewHolder> {
    private final List<Obiettivo> obiettivi;
    private final List<Obiettivo> giaAggiunti;
    private final List<Obiettivo> selezionati;
    private final OnObiettivoClickListener listener;

    public interface OnObiettivoClickListener {
        void onClick(Obiettivo obiettivo);
    }

    public ObiettiviAdapter(List<Obiettivo> obiettivi, List<Obiettivo> giaAggiunti, List<Obiettivo> selezionati, OnObiettivoClickListener listener) {
        this.obiettivi = obiettivi;
        this.giaAggiunti = giaAggiunti;
        this.selezionati = selezionati;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ObiettivoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_obiettivo, parent, false);
        return new ObiettivoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ObiettivoViewHolder holder, int position) {
        Obiettivo obiettivo = obiettivi.get(position);
        holder.bind(obiettivo, giaAggiunti.contains(obiettivo), selezionati.contains(obiettivo), listener, position);
    }

    @Override
    public int getItemCount() {
        return obiettivi.size();
    }

    static class ObiettivoViewHolder extends RecyclerView.ViewHolder {
        private final TextView titolo;
        private final ImageView icona;
        private final View card;

        public ObiettivoViewHolder(@NonNull View itemView) {
            super(itemView);
            titolo = itemView.findViewById(R.id.titoloObiettivo);
            icona = itemView.findViewById(R.id.iconaObiettivo);

            card = itemView;
        }

        public void bind(Obiettivo obiettivo, boolean giaAggiunto, boolean selezionato, OnObiettivoClickListener listener, int position) {
            // Mostra titolo: sintetizza se >4 parole, altrimenti wrap a 2 parole/linea
            titolo.setText(summarizeOrFormat(obiettivo.getTitolo(), 2, 4));
            icona.setImageResource(obiettivo.getIconaResId());
            if (giaAggiunto) {
                card.setBackgroundResource(R.drawable.bg_obiettivo_gia_aggiunto);
                card.setOnClickListener(null);
                card.setClickable(false);
                card.setAlpha(0.6f); // opzionale: visivamente "disabilitato"
            } else if (selezionato) {
                card.setBackgroundResource(R.drawable.bg_obiettivo_selezionato);
                card.setOnClickListener(v -> listener.onClick(obiettivo));
                card.setClickable(true);
                card.setAlpha(1.0f);
            } else {
                card.setBackgroundResource(R.drawable.bg_obiettivo_default);
                card.setOnClickListener(v -> listener.onClick(obiettivo));
                card.setClickable(true);
                card.setAlpha(1.0f);
            }
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
            // altrimenti avvolge ogni wordsPerLine parole
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < parts.length; i++) {
                sb.append(parts[i]);
                if (i < parts.length - 1) {
                    if ((i + 1) % wordsPerLine == 0) sb.append('\n');
                    else sb.append(' ');
                }
            }
            return sb.toString();
        }

        private String capitalize(String w) {
            if (w == null || w.isEmpty()) return w;
            if (w.length() == 1) return w.toUpperCase(Locale.ROOT);
            return w.substring(0,1).toUpperCase(Locale.ROOT) + w.substring(1);
        }
    }
}
