package com.kisen.sindemo;

import android.animation.Animator;
import android.animation.TypeEvaluator;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.kisen.sindemo.util.SinEvaluator;
import com.kisen.sindemo.util.SinPath;
import com.kisen.sindemo.util.SinPoint;

import java.util.ArrayList;

public class CurveActivity extends BaseActivity {

    private boolean isReset;
    private int order;
    private float[] points;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_curve);
    }

    @Override
    protected SinPath getPath() {
        SinPath path = new SinPath();
        path.move(0, 0);
        path.curve(order, points);
        return path;
    }

    @Override
    protected TypeEvaluator getEvaluator() {
        return new SinEvaluator(0, 0, 0);
    }

    @Override
    protected void setupView() {
        final Button curve = (Button) findViewById(R.id.curve);

        curve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isReset = !isReset;
                if (isReset) {
                    bgView.start();
                    curve.setText("重置");
                    ArrayList<SinPoint> points = bgView.getPoints();
                    float[] floats = new float[points.size() * 2];
                    for (int i = 0; i < points.size(); i++) {
                        SinPoint point = points.get(i);
                        floats[2 * i] = point.getX();
                        floats[2 * i + 1] = point.getY();
                    }

                    start(points.size(), floats);
                } else {//重置
                    curve.setText("开始");
                    bgView.clear();
                    setPoint(SinPoint.move(0, 0));
                }
            }
        });


    }

    private void start(int order, float[] points) {
        this.order = order;
        this.points = points;

        initAnimator();

        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                bgView.clear();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                bgView.clear();
            }
        });
        animator.setDuration(3000);
        animator.setInterpolator(null);
        animator.start();
    }
}
