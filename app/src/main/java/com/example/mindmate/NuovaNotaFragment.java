package com.example.mindmate;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import java.text.SimpleDateFormat;
import java.util.Date;

public class NuovaNotaFragment extends Fragment {
    private int selectedEmojiIndex = -1;
    private TextView[] emojiViews;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_nuova_nota, container, false);
        LinearLayout emojiContainer = view.findViewById(R.id.emojiContainer);
        java.util.List<TextView> emojiList = new java.util.ArrayList<>();
        collectEmojiTextViews(emojiContainer, emojiList);
        emojiViews = emojiList.toArray(new TextView[0]);
        for (int i = 0; i < emojiViews.length; i++) {
            final int emojiIdx = i;
            emojiViews[i].setOnClickListener(v -> selectEmoji(emojiIdx));
        }
        Button salva = view.findViewById(R.id.buttonSalvaNota);
        salva.setOnClickListener(v -> salvaNota(view));
        Button indietro = view.findViewById(R.id.buttonIndietroNota);
        indietro.setOnClickListener(v -> requireActivity().getSupportFragmentManager().popBackStack());
        return view;
    }

    // Ricorsivo: raccoglie tutti i TextView (emoji) anche se annidati
    private void collectEmojiTextViews(View parent, java.util.List<TextView> list) {
        if (parent instanceof TextView) {
            list.add((TextView) parent);
        } else if (parent instanceof ViewGroup) {
            ViewGroup vg = (ViewGroup) parent;
            for (int i = 0; i < vg.getChildCount(); i++) {
                collectEmojiTextViews(vg.getChildAt(i), list);
            }
        }
    }

    private void selectEmoji(int idx) {
        int colorSelected = 0xFF222222; // colore normale (nero)
        int colorDeselected = 0xFFBDBDBD; // grigio chiaro per bianco/nero
        for (int i = 0; i < emojiViews.length; i++) {
            if (i == idx) {
                emojiViews[i].setBackgroundResource(R.drawable.bg_emoji_selected);
                emojiViews[i].setTextColor(colorSelected);
            } else {
                emojiViews[i].setBackgroundResource(0);
                emojiViews[i].setTextColor(colorDeselected);
            }
        }
        selectedEmojiIndex = idx;
    }

    private void salvaNota(View view) {
        EditText editText = view.findViewById(R.id.editTextNota);
        String testo = editText.getText().toString();
        String data = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        if (selectedEmojiIndex < 0) {
            android.widget.Toast.makeText(getContext(), "Seleziona un'emoji!", android.widget.Toast.LENGTH_SHORT).show();
            return;
        }
        if (testo.isEmpty()) {
            android.widget.Toast.makeText(getContext(), "Scrivi una nota!", android.widget.Toast.LENGTH_SHORT).show();
            return;
        }
        DiarioFragment.noteTemporanee.add(new DiarioFragment.NotaTemp(testo, data, selectedEmojiIndex));
        android.widget.Toast.makeText(getContext(), "Nota salvata correttamente nel diario!", android.widget.Toast.LENGTH_SHORT).show();
        requireActivity().getSupportFragmentManager().popBackStack();
    }
}
