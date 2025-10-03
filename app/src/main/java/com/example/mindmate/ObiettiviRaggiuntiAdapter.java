package com.example.mindmate;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ObiettiviRaggiuntiAdapter extends RecyclerView.Adapter<ObiettiviRaggiuntiAdapter.ObiettivoViewHolder> {
    private final List<Obiettivo> obiettivi;
    private final Context context;

    public ObiettiviRaggiuntiAdapter(Context context, List<Obiettivo> obiettivi) {
        this.context = context;
        this.obiettivi = obiettivi;
    }

    @NonNull
    @Override
    public ObiettivoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_obiettivo_raggiunto, parent, false);
        return new ObiettivoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ObiettivoViewHolder holder, int position) {
        Obiettivo obiettivo = obiettivi.get(position);
        holder.titolo.setText(obiettivo.getTitolo());
        holder.icona.setImageResource(obiettivo.getIconaResId());
    }

    @Override
    public int getItemCount() {
        return obiettivi.size();
    }

    static class ObiettivoViewHolder extends RecyclerView.ViewHolder {
        ImageView icona;
        TextView titolo;
        ObiettivoViewHolder(@NonNull View itemView) {
            super(itemView);
            icona = itemView.findViewById(R.id.imgObiettivo);
            titolo = itemView.findViewById(R.id.tvTitoloObiettivo);
        }
    }
}
