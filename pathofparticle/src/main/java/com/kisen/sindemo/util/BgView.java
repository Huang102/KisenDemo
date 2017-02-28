package com.kisen.sindemo.util;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

/**
 * @Title :
 * @Description :
 * @Version :
 * Created by huang on 2017/2/27.
 */

public class BgView extends View {
    private final Paint paint, paintP;
    private Path path;
    private float lastX, lastY;
    private float baseY;
    private boolean clear, start;
    private ArrayList<SinPoint> points;

    public BgView(Context context) {
        this(context, null);
    }

    public BgView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BgView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        paintP = new Paint();
        paintP.setColor(Color.BLACK);
        paintP.setStrokeWidth(5);
        paintP.setAntiAlias(true);
        paintP.setStyle(Paint.Style.STROKE);
        paint = new Paint();
        paint.setColor(Color.GREEN);
        paint.setStrokeWidth(5);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        path = new Path();
        points = new ArrayList<>();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                float x = event.getX();
                float y = event.getY() - lastY;
                points.add(SinPoint.move(x, y));
                break;
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        lastY = baseY = getMeasuredHeight() / 2;
        path.moveTo(lastX, baseY);
    }

    public void clear() {
        clear = true;
        points.clear();
    }

    public void start() {
        start = true;
    }

    public void lineTo(float x, float y) {
        path.moveTo(lastX, lastY);
        lastX = x;
        lastY = baseY + y;
        if (!clear)
            path.lineTo(lastX, lastY);
        invalidate();
    }

    public ArrayList<SinPoint> getPoints() {
        return points;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (clear) {
            clear = false;
            path = new Path();
        }
        canvas.drawPath(path, paint);
        if (start) {
            start = false;
            for (int i = 0; i < points.size(); i++) {
                SinPoint point = points.get(i);
                canvas.drawPoint(point.getX(), point.getY() + lastY, paintP);
            }
        }
    }
}
