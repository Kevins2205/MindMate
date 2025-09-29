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

public class PhysicalActivity2Fragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_physical_activity2, container, false);
        LinearLayout activityList = view.findViewById(R.id.physical_activity_list);

        // Esempio di attività statiche
        String[] activities = {"Camminata", "Yoga", "Corsa", "Bici"};
        int[] icons = {R.drawable.ic_walk, R.drawable.ic_yoga, R.drawable.ic_running, R.drawable.ic_bike};
        for (int i = 0; i < activities.length; i++) {
            LinearLayout row = new LinearLayout(getContext());
            row.setOrientation(LinearLayout.HORIZONTAL);
            row.setPadding(0, 16, 0, 16);
            row.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

            ImageView icon = new ImageView(getContext());
            icon.setLayoutParams(new LinearLayout.LayoutParams(64, 64));
            icon.setImageResource(icons[i]);
            icon.setScaleType(ImageView.ScaleType.CENTER_CROP);

            TextView tv = new TextView(getContext());
            tv.setText(activities[i]);
            tv.setTextSize(18);
            tv.setPadding(24, 0, 0, 0);
            tv.setTextColor(0xFF222222);

            row.addView(icon);
            row.addView(tv);
            activityList.addView(row);
        }
        return view;
    }
}

