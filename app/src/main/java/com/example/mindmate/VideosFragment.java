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

public class VideosFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_videos, container, false);
        LinearLayout videosList = view.findViewById(R.id.videos_list);

        // Esempio di video statici
        for (int i = 1; i <= 3; i++) {
            LinearLayout row = new LinearLayout(getContext());
            row.setOrientation(LinearLayout.HORIZONTAL);
            row.setPadding(0, 16, 0, 16);
            row.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

            ImageView preview = new ImageView(getContext());
            preview.setLayoutParams(new LinearLayout.LayoutParams(160, 90));
            preview.setImageResource(R.drawable.ic_profile); // Placeholder
            preview.setScaleType(ImageView.ScaleType.CENTER_CROP);

            LinearLayout textCol = new LinearLayout(getContext());
            textCol.setOrientation(LinearLayout.VERTICAL);
            textCol.setPadding(24, 0, 0, 0);
            textCol.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

            TextView title = new TextView(getContext());
            title.setText("Video " + i);
            title.setTextSize(18);
            title.setTextColor(0xFF222222);
            TextView desc = new TextView(getContext());
            desc.setText("Descrizione breve del video " + i);
            desc.setTextSize(14);
            desc.setTextColor(0xFF444444);

            textCol.addView(title);
            textCol.addView(desc);
            row.addView(preview);
            row.addView(textCol);
            videosList.addView(row);
        }
        return view;
    }
}

