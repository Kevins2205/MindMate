package com.example.mindmate;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class NewNoteFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_note, container, false);
        EditText editNote = view.findViewById(R.id.edit_note);
        Button btnSave = view.findViewById(R.id.btn_save_note);

        btnSave.setOnClickListener(v -> {
            String note = editNote.getText().toString().trim();
            if (TextUtils.isEmpty(note)) {
                Toast.makeText(getContext(), "Scrivi una nota prima di salvare", Toast.LENGTH_SHORT).show();
            } else {
                // Logica per salvare la nota (da implementare con database o lista condivisa)
                Toast.makeText(getContext(), "Nota salvata!", Toast.LENGTH_SHORT).show();
                requireActivity().getSupportFragmentManager().popBackStack();
            }
        });
        return view;
    }
}

