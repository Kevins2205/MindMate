package com.example.mindmate;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ProssimiEventiAdapter extends RecyclerView.Adapter<ProssimiEventiAdapter.EventoViewHolder> {
    private final Context context;
    private final List<Evento> eventi;

    public ProssimiEventiAdapter(Context context, List<Evento> eventi) {
        this.context = context;
        this.eventi = eventi;
    }

    @NonNull
    @Override
    public EventoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_evento, parent, false);
        return new EventoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventoViewHolder holder, int position) {
        Evento evento = eventi.get(position);
        holder.titolo.setText(evento.getTitolo());
        holder.data.setText(evento.getData());
        holder.luogo.setText(evento.getPosizione());
        holder.descrizione.setText(evento.getDescrizione());
        holder.itemView.setOnClickListener(v -> {
            // Apri DettaglioEventoFragment senza possibilità di prenotare
            DettaglioEventoFragment dettaglio = DettaglioEventoFragment.newInstance(evento, true);
            ((MainActivity) context).getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, dettaglio)
                .addToBackStack(null)
                .commit();
        });
    }

    @Override
    public int getItemCount() {
        return eventi.size();
    }

    static class EventoViewHolder extends RecyclerView.ViewHolder {
        TextView titolo, data, luogo, descrizione;
        EventoViewHolder(@NonNull View itemView) {
            super(itemView);
            titolo = itemView.findViewById(R.id.tvTitoloEvento);
            data = itemView.findViewById(R.id.tvDataEvento);
            luogo = itemView.findViewById(R.id.tvLuogoEvento);
            descrizione = itemView.findViewById(R.id.tvDescrizioneEvento);
        }
    }
}
