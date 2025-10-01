package com.example.mindmate;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

public class AttivitaFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_attivita, container, false);

        // 5 card attività
        CardView cardYoga = view.findViewById(R.id.cardYoga);
        CardView cardBallare = view.findViewById(R.id.cardBallare);
        CardView cardCorsa = view.findViewById(R.id.cardCorsa);
        CardView cardHiit = view.findViewById(R.id.cardHiit);
        CardView cardPilates = view.findViewById(R.id.cardPilates);
        CardView cardPasseggiata = view.findViewById(R.id.cardPasseggiata);
        CardView cardBici = view.findViewById(R.id.cardBici);
        CardView cardAllenamentoCorsa = view.findViewById(R.id.cardAllenamentoCorsa);

        cardYoga.setOnClickListener(v ->
                Toast.makeText(getContext(), "Hai scelto Yoga 🧘", Toast.LENGTH_SHORT).show());

        cardBallare.setOnClickListener(v ->
                Toast.makeText(getContext(), "Hai scelto Ballare 💃", Toast.LENGTH_SHORT).show());

        cardCorsa.setOnClickListener(v ->
                Toast.makeText(getContext(), "Hai scelto Corsa 🏃", Toast.LENGTH_SHORT).show());

        cardHiit.setOnClickListener(v ->
                Toast.makeText(getContext(), "Hai scelto HIIT 🔥", Toast.LENGTH_SHORT).show());

        cardPilates.setOnClickListener(v ->
                Toast.makeText(getContext(), "Hai scelto Pilates 🤸", Toast.LENGTH_SHORT).show());

        cardPasseggiata.setOnClickListener(v ->
                Toast.makeText(getContext(), "Hai scelto Passeggiata col cane 🐕", Toast.LENGTH_SHORT).show());

        cardBici.setOnClickListener(v ->
                Toast.makeText(getContext(), "Hai scelto Giro in bici 🚴", Toast.LENGTH_SHORT).show());

        cardAllenamentoCorsa.setOnClickListener(v ->
                Toast.makeText(getContext(), "Hai scelto Corsa allenamento 🏃‍♂️", Toast.LENGTH_SHORT).show());





        return view;
    }
}
