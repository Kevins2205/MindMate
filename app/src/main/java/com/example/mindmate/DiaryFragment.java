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

public class DiaryFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_diary, container, false);
        LinearLayout diaryList = view.findViewById(R.id.diary_list);
        Button btnAddNote = view.findViewById(R.id.btn_add_note);

        // Esempio di note statiche
        List<String> notes = Arrays.asList("Oggi mi sento bene.", "Ho fatto una passeggiata.", "Ho meditato 10 minuti.");
        for (String note : notes) {
            TextView tv = new TextView(getContext());
            tv.setText(note);
            tv.setTextSize(18);
            tv.setPadding(0, 16, 0, 16);
            diaryList.addView(tv);
        }

        btnAddNote.setOnClickListener(v -> {
            // Apri NewNoteFragment
            Fragment newNoteFragment = new NewNoteFragment();
            requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, newNoteFragment)
                .addToBackStack(null)
                .commit();
        });

        return view;
    }
}
