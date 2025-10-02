package com.example.mindmate;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

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
        holder.bind(obiettivo, giaAggiunti.contains(obiettivo), selezionati.contains(obiettivo), listener);
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
        public void bind(Obiettivo obiettivo, boolean giaAggiunto, boolean selezionato, OnObiettivoClickListener listener) {
            titolo.setText(obiettivo.getTitolo());
            icona.setImageResource(obiettivo.getIconaResId());
            if (giaAggiunto) {
                card.setBackgroundResource(R.drawable.bg_obiettivo_gia_aggiunto);
            } else if (selezionato) {
                card.setBackgroundResource(R.drawable.bg_obiettivo_selezionato);
            } else {
                card.setBackgroundResource(R.drawable.bg_obiettivo_default);
            }
            card.setOnClickListener(v -> listener.onClick(obiettivo));
        }
    }
}
