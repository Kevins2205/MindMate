package com.example.mindmate;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class DettaglioIspirazioneFragment extends Fragment {
    private MediaPlayer mediaPlayer;
    private boolean isPlaying = false;
    private Handler handler = new Handler();
    private Runnable updateSeekBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_dettaglio_ispirazione, container, false);
        Bundle args = getArguments();
        String tipo = args != null ? args.getString("tipo", "") : "";
        String id = args != null ? args.getString("id", "") : "";
        String titolo = args != null ? args.getString("titolo", "") : "";

        TextView tvTitolo = root.findViewById(R.id.tvTitoloDettaglio);
        tvTitolo.setText(titolo);
        FrameLayout frameMedia = root.findViewById(R.id.frameMedia);
        ImageView imgCover = root.findViewById(R.id.imgCoverDettaglio);
        ImageView btnPlay = root.findViewById(R.id.btnPlayDettaglio);
        LinearLayout layoutSeekBar = root.findViewById(R.id.layoutSeekBar);
        SeekBar seekBar = root.findViewById(R.id.seekBar);
        TextView tvCurrentTime = root.findViewById(R.id.tvCurrentTime);
        TextView tvTotalTime = root.findViewById(R.id.tvTotalTime);
        LinearLayout btnPlayAudioWrapper = root.findViewById(R.id.btnPlayAudioWrapper);

        // Imposta la cover in base all'id
        imgCover.setImageResource(getCoverResId(id));

        if ("video".equals(tipo)) {
            frameMedia.setVisibility(View.VISIBLE);
            imgCover.setVisibility(View.VISIBLE);
            btnPlay.setVisibility(View.VISIBLE);
            layoutSeekBar.setVisibility(View.VISIBLE);
            btnPlayAudioWrapper.setVisibility(View.GONE);
            // Mostra copertina e play finché non parte il video
            imgCover.setVisibility(View.VISIBLE);
            btnPlay.setVisibility(View.VISIBLE);
            VideoView videoView = new VideoView(requireContext());
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.MATCH_PARENT,
                    FrameLayout.LayoutParams.MATCH_PARENT);
            videoView.setLayoutParams(params);
            videoView.setBackgroundResource(R.drawable.bg_video_rounded);
            int videoRes = getVideoResId(id);
            Uri uri = Uri.parse("android.resource://" + requireContext().getPackageName() + "/" + videoRes);
            videoView.setVideoURI(uri);
            frameMedia.addView(videoView, 0); // sotto la copertina
            layoutSeekBar.setVisibility(View.VISIBLE);
            btnPlay.setOnClickListener(v -> {
                imgCover.setVisibility(View.GONE);
                btnPlay.setVisibility(View.GONE);
                videoView.start();
            });
            imgCover.setOnClickListener(v -> {
                imgCover.setVisibility(View.GONE);
                btnPlay.setVisibility(View.GONE);
                videoView.start();
            });
            videoView.setOnPreparedListener(mp -> {
                seekBar.setMax(videoView.getDuration());
                tvTotalTime.setText(formatTime(videoView.getDuration()));
                tvCurrentTime.setText(formatTime(0));
                updateSeekBar = new Runnable() {
                    @Override
                    public void run() {
                        if (videoView.isPlaying()) {
                            seekBar.setProgress(videoView.getCurrentPosition());
                            tvCurrentTime.setText(formatTime(videoView.getCurrentPosition()));
                        }
                        handler.postDelayed(this, 300);
                    }
                };
                handler.post(updateSeekBar);
            });
            videoView.setOnCompletionListener(mp -> {
                seekBar.setProgress(seekBar.getMax());
                tvCurrentTime.setText(formatTime(seekBar.getMax()));
                imgCover.setVisibility(View.VISIBLE);
                btnPlay.setVisibility(View.VISIBLE);
                // Segna completamento ispirazione solo temporaneamente
                com.example.mindmate.IspirazioneFragment.ispirazioneCompletataOggi = true;
            });
            seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    if (fromUser) {
                        videoView.seekTo(progress);
                        tvCurrentTime.setText(formatTime(progress));
                    }
                }
                @Override public void onStartTrackingTouch(SeekBar seekBar) {}
                @Override public void onStopTrackingTouch(SeekBar seekBar) {}
            });
            videoView.setOnClickListener(v -> {
                if (videoView.isPlaying()) videoView.pause();
                else videoView.start();
            });
        } else if ("audio".equals(tipo)) {
            // Mantieni frameMedia visibile ma vuoto per conservare lo spazio
            frameMedia.setVisibility(View.INVISIBLE); // oppure View.VISIBLE se vuoi vedere il riquadro vuoto
            imgCover.setVisibility(View.GONE);
            btnPlay.setVisibility(View.GONE);
            // Se hai aggiunto VideoView dinamicamente, puoi rimuoverlo qui
            for (int i = frameMedia.getChildCount() - 1; i >= 0; i--) {
                View child = frameMedia.getChildAt(i);
                if (child instanceof VideoView) frameMedia.removeView(child);
            }
            layoutSeekBar.setVisibility(View.VISIBLE);
            btnPlayAudioWrapper.setVisibility(View.VISIBLE);
            ImageButton btnPlayAudio = root.findViewById(R.id.btnPlayAudio);
            // Logica play/pausa audio
            int audioRes = getAudioResId(id);
            btnPlayAudio.setOnClickListener(v -> {
                if (mediaPlayer == null) {
                    mediaPlayer = MediaPlayer.create(requireContext(), audioRes);
                    mediaPlayer.setOnCompletionListener(mp -> {
                        btnPlayAudio.setImageResource(R.drawable.ic_play);
                        isPlaying = false;
                    });
                    seekBar.setMax(mediaPlayer.getDuration());
                    tvTotalTime.setText(formatTime(mediaPlayer.getDuration()));
                    tvCurrentTime.setText(formatTime(0));
                }
                if (mediaPlayer != null)
                {
                    mediaPlayer.setOnCompletionListener(mp -> {
                        com.example.mindmate.IspirazioneFragment.ispirazioneCompletataOggi = true;
                        btnPlayAudio.setImageResource(R.drawable.ic_play);
                        isPlaying = false;
                    });
                }
                if (isPlaying && mediaPlayer != null) {
                    mediaPlayer.pause();
                    btnPlayAudio.setImageResource(R.drawable.ic_play);
                } else if (mediaPlayer != null) {
                    mediaPlayer.start();
                    btnPlayAudio.setImageResource(R.drawable.ic_pause);
                    updateSeekBar = new Runnable() {
                        @Override
                        public void run() {
                            if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                                seekBar.setProgress(mediaPlayer.getCurrentPosition());
                                tvCurrentTime.setText(formatTime(mediaPlayer.getCurrentPosition()));
                                handler.postDelayed(this, 300);
                            }
                        }
                    };
                    handler.post(updateSeekBar);
                }
                isPlaying = !isPlaying;
            });

            seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    if (fromUser && mediaPlayer != null) {
                        mediaPlayer.seekTo(progress);
                        tvCurrentTime.setText(formatTime(progress));
                    }
                }
                @Override public void onStartTrackingTouch(SeekBar seekBar) {}
                @Override public void onStopTrackingTouch(SeekBar seekBar) {}
            });
        }

        Button btnPreferiti = root.findViewById(R.id.btnPreferiti);
        com.example.mindmate.Ispirazione ispirazione = new com.example.mindmate.Ispirazione(tipo, id, titolo);
        aggiornaStatoPreferiti(btnPreferiti, ispirazione);
        btnPreferiti.setOnClickListener(v -> {
            if (PreferitiManager.isPreferito(ispirazione)) {
                PreferitiManager.rimuoviPreferito(ispirazione);
                Toast.makeText(requireContext(), "Rimosso dai preferiti!", Toast.LENGTH_SHORT).show();
            } else {
                PreferitiManager.aggiungiPreferito(ispirazione);
                Toast.makeText(requireContext(), "Aggiunto ai preferiti!", Toast.LENGTH_SHORT).show();
            }
            aggiornaStatoPreferiti(btnPreferiti, ispirazione);
        });
        Button btnIndietro = root.findViewById(R.id.btnIndietro);
        btnIndietro.setOnClickListener(v -> requireActivity().getSupportFragmentManager().popBackStack());
        return root;
    }

    private int getVideoResId(String id) {
        // Mappa id a risorse raw (da personalizzare)
        if ("videoPrincipale".equals(id)) return R.raw.video_motivazionale_1;
        if ("videoCorrelato1".equals(id)) return R.raw.video_motivazionale_2;
        if ("videoCorrelato2".equals(id)) return R.raw.video_motivazionale_3;
        return R.raw.video_motivazionale_1;
    }
    private int getAudioResId(String id) {
        // Mappa id a risorse raw (da personalizzare)
        if ("audio1".equals(id)) return R.raw.audio_motivazionale_1;
        if ("audio2".equals(id)) return R.raw.audio_motivazionale_2;
        if ("audio3".equals(id)) return R.raw.audio_motivazionale_3;
        return R.raw.audio_motivazionale_1;
    }
    private String formatTime(int millis) {
        int seconds = millis / 1000;
        int minutes = seconds / 60;
        seconds = seconds % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }
    private void aggiornaStatoPreferiti(Button btn, com.example.mindmate.Ispirazione ispirazione) {
        if (PreferitiManager.isPreferito(ispirazione)) {
            btn.setText("Rimuovi dai preferiti");
            btn.setBackgroundTintList(android.content.res.ColorStateList.valueOf(getResources().getColor(android.R.color.holo_red_dark)));
        } else {
            btn.setText("Aggiungi ai preferiti");
            btn.setBackgroundTintList(android.content.res.ColorStateList.valueOf(android.graphics.Color.parseColor("#2B5C53")));
        }
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
        if (handler != null && updateSeekBar != null) {
            handler.removeCallbacks(updateSeekBar);
        }
    }

    // Metodo per restituire la cover corretta in base all'id
    private int getCoverResId(String id) {
        if ("videoPrincipale".equals(id)) return R.drawable.cover1;
        if ("videoCorrelato1".equals(id)) return R.drawable.cover2;
        if ("videoCorrelato2".equals(id)) return R.drawable.cover3;
        return R.drawable.cover1;
    }
}
