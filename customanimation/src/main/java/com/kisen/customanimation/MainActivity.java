package com.kisen.customanimation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AccelerateInterpolator;

import com.kisen.customanimation.animation.AnimatorPath;
import com.kisen.customanimation.animation.ObjectAnimatorHelper;
import com.kisen.customanimation.animation.PathEvaluator;
import com.kisen.customanimation.animation.PathPoint;

public class MainActivity extends AppCompatActivity {

    private static final long ANIMATION_DURATION = 500;
    private static final float MINIMUM_X_DISTANCE = 500;
    private static final float SCROLL_FACTOR = 13f;
    private View mMove;
    private View mGroup;
    private float startX;
    private boolean mRevealFlag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMove = findViewById(R.id.move);
        mGroup = findViewById(R.id.group);

        mMove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onMovePressed(v);
            }
        });
    }

    private void onMovePressed(View v) {
        if (startX == 0)
            startX = mMove.getX();
        //开启动画
        AnimatorPath mPath = new AnimatorPath();
        mPath.moveTo(0, 0);
        mPath.cubicTo(-200, 200, -400, 100, -600, 50);
        /**
         * 属性动画
         * 可以控制一个对象的任何属性值---setXXX()
         * 通过API反射控制对应属性
         */
        ObjectAnimator animator = ObjectAnimatorHelper.ofObject(v, new PathEvaluator(), mPath.getPoints().toArray());
        animator.setDuration(ANIMATION_DURATION);
        animator.setInterpolator(new AccelerateInterpolator());
        animator.start();

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                if (Math.abs(startX - mMove.getX()) > MINIMUM_X_DISTANCE) {
                    if (!mRevealFlag)
                        mMove.animate()
                                .scaleXBy(SCROLL_FACTOR)
                                .scaleYBy(SCROLL_FACTOR)
                                .setListener(listener)
                                .setDuration(ANIMATION_DURATION);
                    mRevealFlag = true;
                }
            }
        });
    }

    private AnimatorListenerAdapter listener = new AnimatorListenerAdapter() {
        @Override
        public void onAnimationEnd(Animator animation) {
            mMove.setVisibility(View.GONE);
            mGroup.setBackgroundDrawable(mMove.getBackground());
        }
    };

}
