package com.example.mindmate;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class DettaglioDiarioFragment extends Fragment {

    private static final String ARG_DATA = "data";
    private static final String ARG_EMOJI = "emoji";
    private static final String ARG_TESTO = "testo";

    private String data;
    private String emoji;
    private String testo;

    public static DettaglioDiarioFragment newInstance(String data, String emoji, String testo) {
        DettaglioDiarioFragment fragment = new DettaglioDiarioFragment();
        Bundle args = new Bundle();
        args.putString(ARG_DATA, data);
        args.putString(ARG_EMOJI, emoji);
        args.putString(ARG_TESTO, testo);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            data = getArguments().getString(ARG_DATA);
            emoji = getArguments().getString(ARG_EMOJI);
            testo = getArguments().getString(ARG_TESTO);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dettaglio_diario, container, false);

        TextView tvData = view.findViewById(R.id.tvData);
        TextView tvEmoji = view.findViewById(R.id.tvEmoji);
        TextView tvTesto = view.findViewById(R.id.tvTesto);
        Button btnBack = view.findViewById(R.id.btnBack);

        tvData.setText(data);
        tvEmoji.setText(emoji);
        tvTesto.setText(testo);

        btnBack.setOnClickListener(v -> requireActivity().onBackPressed());

        return view;
    }
}
