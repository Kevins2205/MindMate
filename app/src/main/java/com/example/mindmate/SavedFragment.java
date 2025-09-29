package com.example.mindmate;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import java.util.Arrays;
import java.util.List;

public class SavedFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_saved, container, false);
        LinearLayout savedList = view.findViewById(R.id.saved_list);

        // Esempio di elementi salvati statici
        List<String> savedItems = Arrays.asList("Articolo: Benefici della meditazione", "Video: Esercizi di respirazione", "Evento: Yoga al parco");
        for (String item : savedItems) {
            TextView tv = new TextView(getContext());
            tv.setText(item);
            tv.setTextSize(18);
            tv.setPadding(0, 16, 0, 16);
            savedList.addView(tv);
        }
        return view;
    }
}

