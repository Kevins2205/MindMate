package com.example.mindmate;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class EventsListFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_events_list, container, false);
        LinearLayout eventsList = view.findViewById(R.id.events_list);

        // Esempio di eventi statici
        for (int i = 1; i <= 3; i++) {
            LinearLayout row = new LinearLayout(getContext());
            row.setOrientation(LinearLayout.HORIZONTAL);
            row.setPadding(0, 16, 0, 16);
            row.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

            ImageView preview = new ImageView(getContext());
            preview.setLayoutParams(new LinearLayout.LayoutParams(90, 90));
            preview.setImageResource(R.drawable.ic_event_marker); // Placeholder
            preview.setScaleType(ImageView.ScaleType.CENTER_CROP);

            LinearLayout textCol = new LinearLayout(getContext());
            textCol.setOrientation(LinearLayout.VERTICAL);
            textCol.setPadding(24, 0, 0, 0);
            textCol.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

            TextView title = new TextView(getContext());
            title.setText("Evento " + i);
            title.setTextSize(18);
            title.setTextColor(0xFF222222);
            TextView date = new TextView(getContext());
            date.setText("24/09/2025, 18:00");
            date.setTextSize(14);
            date.setTextColor(0xFF444444);
            TextView place = new TextView(getContext());
            place.setText("Luogo " + i);
            place.setTextSize(14);
            place.setTextColor(0xFF444444);

            textCol.addView(title);
            textCol.addView(date);
            textCol.addView(place);
            row.addView(preview);
            row.addView(textCol);
            eventsList.addView(row);

            row.setOnClickListener(v -> {
                // Apri EventDetail2Fragment
                Fragment detail = new EventDetail2Fragment();
                requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, detail)
                    .addToBackStack(null)
                    .commit();
            });
        }
        return view;
    }
}
