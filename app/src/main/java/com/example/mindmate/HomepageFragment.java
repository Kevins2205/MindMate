package com.example.mindmate;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import java.util.Arrays;
import java.util.List;

public class HomepageFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Header: saluto e punto verde
        TextView homeTitle = view.findViewById(R.id.home_title);
        View onlineDot = view.findViewById(R.id.online_dot);
        if (homeTitle != null) homeTitle.setText("Ciao, Kevin!");
        if (onlineDot != null) onlineDot.setBackgroundResource(R.drawable.online_dot_green);

        // Mood
        TextView moodLabel = view.findViewById(R.id.mood_label);
        ImageView moodIcon = view.findViewById(R.id.mood_icon);
        if (moodLabel != null) moodLabel.setText("Oggi ti senti:");
        if (moodIcon != null) moodIcon.setImageResource(R.drawable.ic_triangle_pink);
        else Log.e("HomepageFragment", "mood_icon non trovato nel layout!");

        // Task di oggi (cliccabili)
        LinearLayout taskList = view.findViewById(R.id.home_task_list);
        if (taskList != null) {
            // Task 1: Respirazione
            LinearLayout row1 = new LinearLayout(getContext());
            row1.setOrientation(LinearLayout.HORIZONTAL);
            row1.setPadding(0, 8, 0, 8);
            row1.setClickable(true);
            row1.setBackgroundResource(android.R.drawable.list_selector_background);
            ImageView icon1 = new ImageView(getContext());
            icon1.setImageResource(R.drawable.ic_breathing);
            icon1.setLayoutParams(new LinearLayout.LayoutParams(48, 48));
            TextView tv1 = new TextView(getContext());
            tv1.setText("Respira con me");
            tv1.setTextSize(15);
            tv1.setTextColor(0xFF222222);
            tv1.setPadding(16, 0, 0, 0);
            row1.addView(icon1);
            row1.addView(tv1);
            row1.setOnClickListener(v -> {
                if (getActivity() instanceof MainActivity) {
                    ((MainActivity) getActivity()).navigateToBreathing();
                }
            });
            taskList.addView(row1);

            // Task 2: Scrivi nel diario
            LinearLayout row2 = new LinearLayout(getContext());
            row2.setOrientation(LinearLayout.HORIZONTAL);
            row2.setPadding(0, 8, 0, 8);
            row2.setClickable(true);
            row2.setBackgroundResource(android.R.drawable.list_selector_background);
            ImageView icon2 = new ImageView(getContext());
            icon2.setImageResource(R.drawable.ic_diary);
            icon2.setLayoutParams(new LinearLayout.LayoutParams(48, 48));
            TextView tv2 = new TextView(getContext());
            tv2.setText("Scrivi nel diario la nota e l'emozione");
            tv2.setTextSize(15);
            tv2.setTextColor(0xFF222222);
            tv2.setPadding(16, 0, 0, 0);
            row2.addView(icon2);
            row2.addView(tv2);
            row2.setOnClickListener(v -> {
                if (getActivity() instanceof MainActivity) {
                    ((MainActivity) getActivity()).navigateToDiaryNote();
                }
            });
            taskList.addView(row2);

            // Task 3: Ispirazione
            LinearLayout row3 = new LinearLayout(getContext());
            row3.setOrientation(LinearLayout.HORIZONTAL);
            row3.setPadding(0, 8, 0, 8);
            row3.setClickable(true);
            row3.setBackgroundResource(android.R.drawable.list_selector_background);
            ImageView icon3 = new ImageView(getContext());
            icon3.setImageResource(R.drawable.ic_inspiration);
            icon3.setLayoutParams(new LinearLayout.LayoutParams(48, 48));
            TextView tv3 = new TextView(getContext());
            tv3.setText("Lasciati ispirare: video, audio o frase");
            tv3.setTextSize(15);
            tv3.setTextColor(0xFF222222);
            tv3.setPadding(16, 0, 0, 0);
            row3.addView(icon3);
            row3.addView(tv3);
            row3.setOnClickListener(v -> {
                if (getActivity() instanceof MainActivity) {
                    ((MainActivity) getActivity()).navigateToInspiration();
                }
            });
            taskList.addView(row3);
        } else {
            Log.e("HomepageFragment", "home_task_list non trovato nel layout!");
        }

        // Obiettivi
        LinearLayout goalsList = view.findViewById(R.id.home_goals_list);
        if (goalsList != null) {
            List<String> goals = Arrays.asList("10.000 passi", "2L acqua", "Meditazione");
            for (String goal : goals) {
                TextView tv = new TextView(getContext());
                tv.setText(goal);
                tv.setTextSize(15);
                tv.setTextColor(0xFF388e3c); // verde
                tv.setPadding(0, 4, 0, 4);
                goalsList.addView(tv);
            }
        } else {
            Log.e("HomepageFragment", "home_goals_list non trovato nel layout!");
        }

        // Notifiche ed eventi
        LinearLayout notificationsList = view.findViewById(R.id.home_notifications_list);
        if (notificationsList != null) {
            List<String> notifications = Arrays.asList("Evento Yoga alle 18:00", "Promemoria: Bere acqua alle 15:00");
            for (String notif : notifications) {
                TextView tv = new TextView(getContext());
                tv.setText(notif);
                tv.setTextSize(14);
                tv.setTextColor(0xFF388e3c); // verde
                tv.setPadding(0, 2, 0, 2);
                notificationsList.addView(tv);
            }
        } else {
            Log.e("HomepageFragment", "home_notifications_list non trovato nel layout!");
        }

        return view;
    }
}
