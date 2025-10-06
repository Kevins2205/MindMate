package com.example.mindmate;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class PreferitiAdapter extends RecyclerView.Adapter<PreferitiAdapter.ViewHolder> {
    private final Context context;
    private final List<Ispirazione> preferiti;

    public PreferitiAdapter(Context context, List<Ispirazione> preferiti) {
        this.context = context;
        this.preferiti = preferiti;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_preferito, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Ispirazione ispirazione = preferiti.get(position);
        holder.tvTitolo.setText(ispirazione.titolo);
        holder.tvTipo.setText("Tipo: " + ispirazione.tipo);
        holder.tvDescrizione.setText("Contenuto preferito salvato");
        if ("audio".equalsIgnoreCase(ispirazione.tipo)) {
            holder.imgTipo.setImageResource(R.drawable.ic_audio);
        } else if ("video".equalsIgnoreCase(ispirazione.tipo)) {
            holder.imgTipo.setImageResource(R.drawable.ic_event); // Usa ic_event come icona video alternativa
        } else {
            holder.imgTipo.setImageResource(R.drawable.ic_favorite);
        }
        holder.itemView.setOnClickListener(v -> {
            DettaglioIspirazioneFragment dettaglio = new DettaglioIspirazioneFragment();
            Bundle args = new Bundle();
            args.putString("tipo", ispirazione.tipo);
            args.putString("id", ispirazione.id);
            args.putString("titolo", ispirazione.titolo);
            dettaglio.setArguments(args);
            if (context instanceof androidx.fragment.app.FragmentActivity) {
                ((androidx.fragment.app.FragmentActivity) context).getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, dettaglio)
                        .addToBackStack(null)
                        .commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return preferiti.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgTipo;
        TextView tvTitolo, tvTipo, tvDescrizione;
        ViewHolder(View itemView) {
            super(itemView);
            imgTipo = itemView.findViewById(R.id.imgTipoPreferito);
            tvTitolo = itemView.findViewById(R.id.tvTitoloPreferito);
            tvTipo = itemView.findViewById(R.id.tvTipoPreferito);
            tvDescrizione = itemView.findViewById(R.id.tvDescrizionePreferito);
        }
    }
}
