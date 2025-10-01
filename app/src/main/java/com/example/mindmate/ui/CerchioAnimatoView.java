package com.example.mindmate.ui;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class CerchioAnimatoView extends View {
    public enum StatoRespiro { INSPIRA, TRATTIENI, ESPIRA, FERMO }

    private StatoRespiro stato = StatoRespiro.FERMO;
    private int colore = Color.BLUE;
    private String testoTimer = "";
    private float progress = 1f; // 1f = cerchio pieno, 0.5f = metà
    private ValueAnimator animator;
    private Paint paint;
    private Paint paintText;

    public CerchioAnimatoView(Context context) {
        super(context);
        init();
    }
    public CerchioAnimatoView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }
    public CerchioAnimatoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
    private void init() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(18f);
        paint.setColor(colore);
        paintText = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintText.setColor(Color.WHITE);
        paintText.setTextSize(48f);
        paintText.setTextAlign(Paint.Align.CENTER);
    }

    public void impostaStato(StatoRespiro stato, long durata, String testo) {
        this.stato = stato;
        this.testoTimer = testo;
        switch (stato) {
            case INSPIRA:
                colore = Color.parseColor("#1976D2");
                animaCerchio(0.5f, 1f, durata);
                break;
            case TRATTIENI:
                colore = Color.parseColor("#FFD600");
                fermaAnimazione(1f);
                break;
            case ESPIRA:
                colore = Color.parseColor("#43A047");
                animaCerchio(1f, 0.5f, durata);
                break;
            case FERMO:
                colore = Color.parseColor("#1976D2");
                fermaAnimazione(1f);
                break;
        }
        paint.setColor(colore);
        invalidate();
    }

    public void aggiornaTimer(String testo) {
        this.testoTimer = testo;
        invalidate();
    }

    private void animaCerchio(float da, float a, long durata) {
        if (animator != null) animator.cancel();
        animator = ValueAnimator.ofFloat(da, a);
        animator.setDuration(durata);
        animator.addUpdateListener(animation -> {
            progress = (float) animation.getAnimatedValue();
            invalidate();
        });
        animator.start();
    }

    private void fermaAnimazione(float valore) {
        if (animator != null) animator.cancel();
        progress = valore;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float raggio = (Math.min(getWidth(), getHeight()) / 2f * progress * 0.9f);
        float cx = getWidth() / 2f;
        float cy = getHeight() / 2f;
        canvas.drawCircle(cx, cy, raggio, paint);
        if (testoTimer != null && !testoTimer.isEmpty()) {
            canvas.drawText(testoTimer, cx, cy + paintText.getTextSize() / 3, paintText);
        }
    }
}

