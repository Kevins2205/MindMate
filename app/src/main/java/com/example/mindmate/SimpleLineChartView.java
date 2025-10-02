package com.example.mindmate;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import java.util.List;
import java.util.ArrayList;

public class SimpleLineChartView extends View {
    private List<Float> values = new ArrayList<>();
    private Paint axisPaint, linePaint, pointPaint, textPaint;

    public SimpleLineChartView(Context context) {
        super(context);
        init();
    }
    public SimpleLineChartView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }
    private void init() {
        axisPaint = new Paint();
        axisPaint.setColor(Color.parseColor("#222222"));
        axisPaint.setStrokeWidth(4f);
        axisPaint.setStyle(Paint.Style.STROKE);

        linePaint = new Paint();
        linePaint.setColor(Color.parseColor("#2B5C53"));
        linePaint.setStrokeWidth(5f);
        linePaint.setStyle(Paint.Style.STROKE);

        pointPaint = new Paint();
        pointPaint.setColor(Color.parseColor("#2B5C53"));
        pointPaint.setStyle(Paint.Style.FILL);

        textPaint = new Paint();
        textPaint.setColor(Color.parseColor("#222222"));
        textPaint.setTextSize(36f);
        textPaint.setTextAlign(Paint.Align.CENTER);
    }
    public void setValues(List<Float> values) {
        this.values = values;
        invalidate();
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int w = getWidth();
        int h = getHeight();
        int padding = 60;
        // Draw axes
        canvas.drawLine(padding, h-padding, w-padding, h-padding, axisPaint); // X
        canvas.drawLine(padding, h-padding, padding, padding, axisPaint); // Y
        // Draw labels
        canvas.drawText("T E M P O", w/2, h-15, textPaint);
        canvas.save();
        canvas.rotate(-90, 30, h/2);
        canvas.drawText("U M O R E", 30, h/2, textPaint);
        canvas.restore();
        // Draw line and points
        if (values.size() < 2) return;
        float min = 1f, max = 5f;
        float dx = (w-2*padding)*1f/(values.size()-1);
        float prevX = padding, prevY = h-padding-((values.get(0)-min)/(max-min))*(h-2*padding);
        for (int i=1; i<values.size(); i++) {
            float x = padding + i*dx;
            float y = h-padding-((values.get(i)-min)/(max-min))*(h-2*padding);
            canvas.drawLine(prevX, prevY, x, y, linePaint);
            canvas.drawCircle(x, y, 10, pointPaint);
            prevX = x; prevY = y;
        }
        // Draw first point
        float y0 = h-padding-((values.get(0)-min)/(max-min))*(h-2*padding);
        canvas.drawCircle(padding, y0, 10, pointPaint);
    }
}

