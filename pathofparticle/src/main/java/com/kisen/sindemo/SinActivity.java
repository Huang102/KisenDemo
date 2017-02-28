package com.kisen.sindemo;

import android.animation.Animator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.TextView;

import com.kisen.sindemo.util.SinEvaluator;
import com.kisen.sindemo.util.SinPath;

public class SinActivity extends BaseActivity implements SeekBar.OnSeekBarChangeListener {

    private boolean changed;
    private TextView show;
    private float[] awp = new float[]{1, 1, 0};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sin);
    }

    @Override
    protected SinPath getPath() {
        SinPath path = new SinPath();
        path.move(0, 0);
        path.sin(800, 0);
        return path;
    }

    @Override
    protected TypeEvaluator getEvaluator() {
        return  new SinEvaluator(awp[0], awp[1], awp[2]);
    }

    protected void setupView() {
        initAnimator();
        show = (TextView) findViewById(R.id.show);

        SeekBar seekBarA = (SeekBar) findViewById(R.id.seek_bar_a);
        SeekBar seekBarW = (SeekBar) findViewById(R.id.seek_bar_w);
        SeekBar seekBarP = (SeekBar) findViewById(R.id.seek_bar_p);

        seekBarA.setMax(300);
        seekBarW.setMax(500);
        seekBarP.setMax(100);
        seekBarA.setOnSeekBarChangeListener(this);
        seekBarW.setOnSeekBarChangeListener(this);
        seekBarP.setOnSeekBarChangeListener(this);

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                if (changed) {
                    changed = false;
                    animator.setEvaluator(new SinEvaluator(awp[0], awp[1], awp[2]));
                }
            }
        });
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
        animator.setDuration(3000);
        animator.setInterpolator(null);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.start();
    }

    public void getAWP() {
        show.setText("A :" + awp[0] + "W :" + awp[1] + "P :" + awp[2]);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        changed = true;
        switch (seekBar.getId()) {
            case R.id.seek_bar_a:
                awp[0] = progress / 3f + 50;
                break;
            case R.id.seek_bar_w:
                awp[1] = progress / 1000f;
                break;
            case R.id.seek_bar_p:
                awp[2] = progress / 100f;
                break;
        }
        getAWP();
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
