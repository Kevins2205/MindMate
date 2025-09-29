package com.example.mindmate;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import java.util.Arrays;
import java.util.List;

public class ReminderFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reminder, container, false);
        LinearLayout reminderList = view.findViewById(R.id.reminder_list);
        Button btnAddReminder = view.findViewById(R.id.btn_add_reminder);

        // Esempio di promemoria statici
        List<String> reminders = Arrays.asList("08:00 - Bere acqua", "12:30 - Pranzo", "18:00 - Attività fisica");
        for (String reminder : reminders) {
            LinearLayout row = new LinearLayout(getContext());
            row.setOrientation(LinearLayout.HORIZONTAL);
            row.setPadding(0, 16, 0, 16);
            row.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

            TextView tv = new TextView(getContext());
            tv.setText(reminder);
            tv.setTextSize(18);
            tv.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1));

            Switch sw = new Switch(getContext());
            sw.setChecked(true);

            row.addView(tv);
            row.addView(sw);
            reminderList.addView(row);
        }

        btnAddReminder.setOnClickListener(v -> {
            // Logica per aggiungere un nuovo promemoria
        });

        return view;
    }
}

