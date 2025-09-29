package com.example.mindmate;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class BreathingFragment extends Fragment {
    private CountDownTimer timer;
    private TextView breathingTimer;
    private Button btnStart;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_breathing, container, false);
        breathingTimer = view.findViewById(R.id.breathing_timer);
        btnStart = view.findViewById(R.id.btn_start_breathing);

        btnStart.setOnClickListener(v -> startBreathingExercise());
        return view;
    }

    private void startBreathingExercise() {
        btnStart.setEnabled(false);
        breathingTimer.setVisibility(View.VISIBLE);
        timer = new CountDownTimer(60000, 1000) { // 1 minuto
            public void onTick(long millisUntilFinished) {
                int seconds = (int) (millisUntilFinished / 1000);
                int min = seconds / 60;
                int sec = seconds % 60;
                breathingTimer.setText(String.format("%02d:%02d", min, sec));
            }
            public void onFinish() {
                breathingTimer.setText("00:00");
                btnStart.setEnabled(true);
            }
        }.start();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (timer != null) timer.cancel();
    }
}

