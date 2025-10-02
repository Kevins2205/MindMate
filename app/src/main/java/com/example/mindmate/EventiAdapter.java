package com.example.mindmate;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class EventiAdapter extends RecyclerView.Adapter<EventiAdapter.EventoViewHolder> {
    private final List<Evento> eventi;
    private final OnEventoClickListener listener;

    public interface OnEventoClickListener {
        void onEventoClick(Evento evento);
    }

    public EventiAdapter(List<Evento> eventi, OnEventoClickListener listener) {
        this.eventi = eventi;
        this.listener = listener;
    }

    @NonNull
    @Override
    public EventoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_evento, parent, false);
        return new EventoViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull EventoViewHolder holder, int position) {
        Evento evento = eventi.get(position);
        holder.titolo.setText(evento.titolo);
        holder.descrizione.setText(evento.descrizione);
        holder.puntina.setImageResource(R.drawable.image);
        holder.itemView.setOnClickListener(v -> listener.onEventoClick(evento));
    }

    @Override
    public int getItemCount() {
        return eventi.size();
    }

    class EventoViewHolder extends RecyclerView.ViewHolder {
        ImageView puntina;
        TextView titolo;
        TextView descrizione;
        EventoViewHolder(View itemView) {
            super(itemView);
            puntina = itemView.findViewById(R.id.imgPuntina);
            titolo = itemView.findViewById(R.id.tvTitoloEvento);
            descrizione = itemView.findViewById(R.id.tvDescrizioneEvento);
        }
    }
}
