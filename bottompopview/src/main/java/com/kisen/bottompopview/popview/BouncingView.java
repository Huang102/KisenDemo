package com.kisen.bottompopview.popview;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import com.kisen.bottompopview.R;

/**
 * @Title :
 * @Description :
 * @Version :
 * Created by huang on 2017/2/10.
 */

public class BouncingView extends View {

    private Paint mPaint;
    //变化过程当中当前的弧线的高度mArcHeight
    private int mArcHeight;//当前的弧高
    private int mMaxArcHeight;//最大弧高

    private Path mPath = new Path();

    private Status mStatus = Status.NONE;

    private AnimationListener animationListener;


    public enum Status {
        NONE,
        STATUS_SMOOTH_UP,
        STATUS_DOWN
    }

    public BouncingView(Context context) {
        this(context, null);
    }

    public BouncingView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BouncingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.WHITE);
        mMaxArcHeight = getResources().getDimensionPixelSize(R.dimen.arc_max_height);
    }

    public void show() {
        // 不断地控制MArcHeight的高度
        mStatus = Status.STATUS_SMOOTH_UP;
        ValueAnimator valueAnimator = ValueAnimator.ofInt(0, mMaxArcHeight);
        valueAnimator.setDuration(500);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mArcHeight = (int) animation.getAnimatedValue();
                if (mArcHeight == mMaxArcHeight) {
                    //弹一下
                    bounce();
                }
                //刷新，调用onDraw()方法
                invalidate();
            }
        });
        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                animationListener.showContent();
            }
        });
        valueAnimator.start();
    }

    /**
     * 回弹动画
     */
    private void bounce() {
        mStatus = Status.STATUS_DOWN;
        //弹一下
        ValueAnimator valueAnimator = ValueAnimator.ofInt(mMaxArcHeight, 0);
        valueAnimator.setDuration(300);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mArcHeight = (int) animation.getAnimatedValue();
                invalidate();
            }
        });
        valueAnimator.start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int currentPointY = 0;

        switch (mStatus) {
            case NONE:
                currentPointY = 0;
                break;
            case STATUS_SMOOTH_UP:
                //currentPointY的值和mArcHeight的变化率是一样的
                //getHeight()~0     0~mMaxHeight
                //current/getHeight()=1-mArcHeight/mMaxArcHeight
                currentPointY = (int) (getHeight() * (1 - (float) mArcHeight / mMaxArcHeight) + mMaxArcHeight);
                break;
            case STATUS_DOWN:
                currentPointY = mMaxArcHeight;
                break;
        }

        mPath.reset();
        mPath.moveTo(0, currentPointY);
        mPath.quadTo(getWidth() / 2, currentPointY - mArcHeight, getWidth(), currentPointY);
        mPath.lineTo(getWidth(), getHeight());
        mPath.lineTo(0, getHeight());
        mPath.close();
        canvas.drawPath(mPath, mPaint);
    }

    public void setAnimationListener(BouncingView.AnimationListener listener) {
        this.animationListener = listener;
    }

    public interface AnimationListener {
        void showContent();
    }

}
