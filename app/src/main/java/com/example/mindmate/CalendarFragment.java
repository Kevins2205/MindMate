package com.example.mindmate;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import java.util.Arrays;
import java.util.List;

public class CalendarFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calendar, container, false);
        GridView calendarGrid = view.findViewById(R.id.calendar_grid);
        LinearLayout eventsList = view.findViewById(R.id.calendar_events_list);

        // Placeholder: giorni del mese (1-30)
        String[] days = new String[30];
        for (int i = 0; i < 30; i++) days[i] = String.valueOf(i + 1);
        calendarGrid.setAdapter(new android.widget.ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, days));

        // Placeholder: eventi del giorno
        List<String> events = Arrays.asList("Yoga al parco", "Meditazione guidata", "Passeggiata di gruppo");
        for (String event : events) {
            TextView tv = new TextView(getContext());
            tv.setText(event);
            tv.setTextSize(18);
            tv.setPadding(0, 16, 0, 16);
            eventsList.addView(tv);
        }
        return view;
    }
}

