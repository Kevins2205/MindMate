package com.example.mindmate;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import java.util.ArrayList;
import java.util.List;

public class ProfiloFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profilo, container, false);

        LineChart lineChart = view.findViewById(R.id.lineChartUmore);
        // Dati di esempio per il grafico dell'umore
        List<Entry> entries = new ArrayList<>();
        entries.add(new Entry(0, 3));
        entries.add(new Entry(1, 4));
        entries.add(new Entry(2, 2));
        entries.add(new Entry(3, 5));
        entries.add(new Entry(4, 4));
        entries.add(new Entry(5, 3));
        entries.add(new Entry(6, 5));

        LineDataSet dataSet = new LineDataSet(entries, "Umore settimanale");
        dataSet.setColor(Color.parseColor("#4F8EF7"));
        dataSet.setCircleColor(Color.parseColor("#4F8EF7"));
        dataSet.setLineWidth(3f);
        dataSet.setCircleRadius(5f);
        dataSet.setDrawValues(false);
        dataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        dataSet.setDrawFilled(true);
        dataSet.setFillColor(Color.parseColor("#B3D1FF"));
        dataSet.setFillAlpha(180);
        dataSet.setDrawHighlightIndicators(false);

        LineData lineData = new LineData(dataSet);
        lineChart.setData(lineData);
        lineChart.getDescription().setEnabled(false);
        lineChart.getLegend().setEnabled(false);
        lineChart.setTouchEnabled(false);
        lineChart.setViewPortOffsets(8, 8, 8, 32);

        // Asse X
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(false);
        xAxis.setTextColor(Color.parseColor("#222222"));
        xAxis.setTextSize(13f);
        xAxis.setGranularity(1f);
        xAxis.setLabelCount(7, true);
        xAxis.setValueFormatter(new ValueFormatter() {
            private final String[] giorni = {"Lun", "Mar", "Mer", "Gio", "Ven", "Sab", "Dom"};
            @Override
            public String getAxisLabel(float value, com.github.mikephil.charting.components.AxisBase axis) {
                if (value >= 0 && value < giorni.length) return giorni[(int) value];
                return "";
            }
        });

        // Asse Y
        YAxis yAxisLeft = lineChart.getAxisLeft();
        yAxisLeft.setDrawGridLines(true);
        yAxisLeft.setGridColor(Color.parseColor("#EEEEEE"));
        yAxisLeft.setTextColor(Color.parseColor("#222222"));
        yAxisLeft.setTextSize(13f);
        yAxisLeft.setAxisMinimum(0f);
        yAxisLeft.setAxisMaximum(5f);
        yAxisLeft.setLabelCount(6, true);
        lineChart.getAxisRight().setEnabled(false);

        lineChart.invalidate();

        LinearLayout btnDettagliUtente = view.findViewById(R.id.btnDettagliUtente);
        btnDettagliUtente.setOnClickListener(v -> {
            FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, new DettaglioUtenteFragment());
            transaction.addToBackStack(null);
            transaction.commit();
        });
        LinearLayout btnProssimiEventi = view.findViewById(R.id.btnProssimiEventi);
        btnProssimiEventi.setOnClickListener(v -> {
            FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, new ProssimiEventiFragment());
            transaction.addToBackStack(null);
            transaction.commit();
        });
        LinearLayout btnPreferiti = view.findViewById(R.id.btnPreferiti);
        if (btnPreferiti != null) {
            btnPreferiti.setOnClickListener(v -> {
                FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, new PreferitiFragment());
                transaction.addToBackStack(null);
                transaction.commit();
            });
        }

        return view;
    }
}
