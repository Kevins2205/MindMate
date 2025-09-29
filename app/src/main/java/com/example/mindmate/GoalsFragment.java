package com.example.mindmate;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import java.util.Arrays;
import java.util.List;

public class GoalsFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_goals, container, false);
        LinearLayout goalsList = view.findViewById(R.id.goals_list);
        Button btnAddGoal = view.findViewById(R.id.btn_add_goal);

        // Esempio di obiettivi statici
        List<String> goals = Arrays.asList("Camminare 10.000 passi al giorno", "Bere 2L di acqua", "Meditare 10 minuti");
        for (String goal : goals) {
            LinearLayout row = new LinearLayout(getContext());
            row.setOrientation(LinearLayout.HORIZONTAL);
            row.setPadding(0, 16, 0, 16);
            row.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

            TextView tv = new TextView(getContext());
            tv.setText(goal);
            tv.setTextSize(18);
            tv.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1));

            Button btnRemove = new Button(getContext());
            btnRemove.setText("Rimuovi");
            btnRemove.setOnClickListener(v -> {
                // Logica per rimuovere l'obiettivo
            });

            row.addView(tv);
            row.addView(btnRemove);
            goalsList.addView(row);
        }

        btnAddGoal.setOnClickListener(v -> {
            // Logica per aggiungere un nuovo obiettivo
        });

        return view;
    }
}

