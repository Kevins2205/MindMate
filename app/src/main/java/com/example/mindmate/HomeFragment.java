package com.example.mindmate;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class HomeFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        View task1 = root.findViewById(R.id.task1);
        // Cambia l'immagine della pillola se la nota di oggi è già stata inserita
        ImageView pillImage = root.findViewById(R.id.task1_img);
        String dataOggi = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        boolean notaPresente = false;
        if (DiarioFragment.noteTemporanee != null) {
            for (DiarioFragment.NotaTemp n : DiarioFragment.noteTemporanee) {
                if (n.data.equals(dataOggi)) {
                    notaPresente = true;
                    break;
                }
            }
        }
        if (notaPresente) {
            pillImage.setImageResource(R.drawable.pillgreen);
            if (task1 != null) {
                task1.setOnClickListener(v -> {
                    android.widget.Toast.makeText(getContext(), "Hai già inserito una nota oggi. Puoi visualizzarla nel diario.", android.widget.Toast.LENGTH_LONG).show();
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
        // Gestione frecce per cambiare giorno
        TextView arrowLeft = root.findViewById(R.id.arrowLeft);
        TextView arrowRight = root.findViewById(R.id.arrowRight);
        TextView textDate = root.findViewById(R.id.textDate);
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");
        Calendar cal = Calendar.getInstance();
        textDate.setText(sdf.format(cal.getTime()));
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
        return root;
    }
}
