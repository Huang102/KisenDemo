package com.kisen.sindemo;

import android.animation.Animator;
import android.animation.TypeEvaluator;
import android.os.Bundle;

import com.kisen.sindemo.util.MotionEvaluator;
import com.kisen.sindemo.util.MotionPath;

public class FibbonacciActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fibbonacci);
    }

    @Override
    protected MotionPath getPath() {
        MotionPath path = new MotionPath();
        path.move(301, 0);
        path.fibonacci(300, 0, 14);
        return path;
    }

    @Override
    protected TypeEvaluator getEvaluator() {
        return new MotionEvaluator(0, 0, 0);
    }

    @Override
    protected void setupView() {
        initAnimator();
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                bgView.clear();
            }
        });
        animator.setDuration(5000);
        animator.setInterpolator(null);
//        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.start();
    }
}
