package com.kisen.animationlayout;

import android.animation.ArgbEvaluator;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * @Title :
 * @Description :
 * @Version :
 * Created by huang on 2017/3/1.
 */
public class AnimatorLayout extends NestedScrollView {

    /**
     * ScrollView 子容器
     */
    private LinearLayout content;
    /**
     * 保存所有带有自定义效果的view
     */
    private List<OnAnimatorScrollListener> listeners = new ArrayList<>();
    private ArgbEvaluator evaluator = new ArgbEvaluator();

    public AnimatorLayout(Context context) {
        this(context, null);
    }

    public AnimatorLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AnimatorLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        content = new LinearLayout(context, attrs);
        content.setOrientation(LinearLayout.VERTICAL);
        addView(content);
    }


    /**
     * 该方法会在读取XML时addView之前执行
     * @param attrs 布局中的属性
     */
    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        //返回自定义LayoutParams，主要用于读取attrs中的属性
        return new AnimatorLayoutParams(getContext(), attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        for (OnAnimatorScrollListener listener : listeners) {
            AnimatorChildLayout view = (AnimatorChildLayout) listener;
            if (view.isMatchParent()) {
                //方式一：结果正确-完美
                if (MeasureSpec.getMode(heightMeasureSpec) == MeasureSpec.EXACTLY) {
                    int size = MeasureSpec.getSize(heightMeasureSpec);
                    view.getLayoutParams().height = size - getPaddingBottom() - getPaddingTop();
                }
                if (MeasureSpec.getMode(widthMeasureSpec) == MeasureSpec.EXACTLY) {
                    int size = MeasureSpec.getSize(widthMeasureSpec);
                    view.getLayoutParams().width = size - getPaddingBottom() - getPaddingTop();
                }
                //方式二：子控件显示正确，但是全部子控件的长度计算不正确最后的子控件无法显示
//                view.measure(widthMeasureSpec, heightMeasureSpec);
            }
        }
    }

    @Override
    public void addView(View child, ViewGroup.LayoutParams params) {
        //将子控件使用ChildParentLayout包裹起来，用来处理系统控件上的自定义属性
        if (((AnimatorLayoutParams) params).hasAnimatorAttr()) {
            AnimatorChildLayout parentLayout = new AnimatorChildLayout(child.getContext());
            parentLayout.setLayoutParams(params);
            parentLayout.addView(child, params);
            listeners.add(parentLayout);
            //将所有的子控件添加到 content 中，保证ScrollView只有一个child
            content.addView(parentLayout, params);
        } else {
            //将所有的子控件添加到 content 中，保证ScrollView只有一个child
            content.addView(child, params);
        }
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        //获取父容器高度
        int scrollViewHeight = getHeight();
        for (OnAnimatorScrollListener listener : listeners) {
            View child = (View) listener;
            //距离父容器顶端的距离而不是距离屏幕顶端的距离，包括已经滑出屏幕的view高度
            int childTop = child.getTop();

            int childHeight = child.getHeight();
            //计算屏幕内 child距离屏幕顶端的距离
            //t 为父容器滑出屏幕的距离
            int absoluteTop = childTop - t;
            //子控件从底部进入屏幕或者滑出屏幕时
            if (absoluteTop <= scrollViewHeight) {
                float ratio = (scrollViewHeight - absoluteTop) * 1f / childHeight;
                listener.onAnimatorScroll(clamp(ratio, 1f, 0f));
            } else {
                //如果子控件不在屏幕内
                listener.onResetScroll();
            }
        }
    }

    private float clamp(float value, float max, float min) {
        return Math.max(Math.min(value, max), min);
    }

    /**
     * 自定义LayoutParams类，获取子控件的自定义属性
     */
    class AnimatorLayoutParams extends LayoutParams {

        boolean isAnimMatchParent;
        boolean isAnimAlpha;
        boolean isAnimScaleX;
        boolean isAnimScaleY;
        int mAnimFromBgColor;
        int mAnimToBgColor;
        int mAnimTransform;

        AnimatorLayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);

            TypedArray array = c.obtainStyledAttributes(attrs, R.styleable.AnimatorLayout_layoutParams);
            isAnimMatchParent = array.getBoolean(R.styleable.AnimatorLayout_layoutParams_anim_match_parent, false);
            isAnimAlpha = array.getBoolean(R.styleable.AnimatorLayout_layoutParams_anim_alpha, false);
            isAnimScaleX = array.getBoolean(R.styleable.AnimatorLayout_layoutParams_anim_scaleX, false);
            isAnimScaleY = array.getBoolean(R.styleable.AnimatorLayout_layoutParams_anim_scaleY, false);
            mAnimFromBgColor = array.getColor(R.styleable.AnimatorLayout_layoutParams_anim_fromBgColor, -1);
            mAnimToBgColor = array.getColor(R.styleable.AnimatorLayout_layoutParams_anim_toBgColor, -1);
            mAnimTransform = array.getInt(R.styleable.AnimatorLayout_layoutParams_anim_transform, -1);
            array.recycle();
        }

        boolean hasAnimatorAttr() {
            return isAnimMatchParent
                    || isAnimAlpha
                    || isAnimScaleX
                    || isAnimScaleY
                    || (mAnimFromBgColor != -1 && mAnimToBgColor != -1)
                    || mAnimTransform != -1;
        }

        boolean isMatchParent() {
            return isAnimMatchParent;
        }
    }

    /**
     * 自定义属性需要被该容器包裹
     */
    class AnimatorChildLayout extends FrameLayout implements OnAnimatorScrollListener {

        public static final int FROM_TOP = 0X01;
        public static final int FROM_BOTTOM = FROM_TOP << 1;
        public static final int FROM_LEFT = FROM_TOP << 2;
        public static final int FROM_RIGHT = FROM_TOP << 3;
        private AnimatorLayoutParams animParams;

        public AnimatorChildLayout(Context context) {
            super(context);
        }

        @Override
        public void addView(View child, ViewGroup.LayoutParams params) {
            super.addView(child, params);
            animParams = (AnimatorLayoutParams) params;
        }

        @Override
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            if (animParams.isAnimMatchParent) {
                getChildAt(0).measure(widthMeasureSpec, heightMeasureSpec);
            }
        }

        @Override
        public void onAnimatorScroll(float ratio) {
            if (animParams.isAnimAlpha) {
                setAlpha(ratio);
            }
            if (animParams.isAnimScaleX) {
                setScaleX(ratio);
            }
            if (animParams.isAnimScaleY) {
                setScaleY(ratio);
            }
            if (isTranslationFrom(FROM_BOTTOM)) {
                setTranslationY(getHeight() * (1 - ratio));//height->0
            }
            if (isTranslationFrom(FROM_TOP)) {
                setTranslationY(getHeight() * (ratio - 1));//0->height
            }
            if (isTranslationFrom(FROM_LEFT)) {
                setTranslationX(getWidth() * (ratio - 1));//width->0
            }
            if (isTranslationFrom(FROM_RIGHT)) {
                setTranslationX(getWidth() * (1 - ratio));//0->width
            }
            if (animParams.mAnimFromBgColor != -1 && animParams.mAnimToBgColor != -1) {
                int bgColor = (int) evaluator.evaluate(ratio, animParams.mAnimFromBgColor, animParams.mAnimToBgColor);
                getChildAt(0).setBackgroundColor(bgColor);
            }
        }

        boolean isMatchParent() {
            return animParams.isMatchParent();
        }

        private boolean isTranslationFrom(int translationMask) {
            return animParams.mAnimTransform != -1 && (animParams.mAnimTransform & translationMask) == translationMask;
        }

        @Override
        public void onResetScroll() {
            if (animParams.isAnimAlpha) {
                setAlpha(0);
            }
            if (animParams.isAnimScaleX) {
                setScaleX(0);
            }
            if (animParams.isAnimScaleY) {
                setScaleY(0);
            }
            if (isTranslationFrom(FROM_BOTTOM)) {
                setTranslationY(getHeight());//height->0
            }
            if (isTranslationFrom(FROM_TOP)) {
                setTranslationY(-getHeight());//0->height
            }
            if (isTranslationFrom(FROM_LEFT)) {
                setTranslationX(-getWidth());//0->width
            }
            if (isTranslationFrom(FROM_RIGHT)) {
                setTranslationX(getWidth());//width->0
            }
            if (animParams.mAnimFromBgColor != -1 && animParams.mAnimToBgColor != -1) {
                setBackgroundColor(-1);
            }
        }
    }

    interface OnAnimatorScrollListener {
        /**
         * @param ratio 0-1 动画执行百分比
         */
        void onAnimatorScroll(float ratio);

        /**
         * 重置动画
         */
        void onResetScroll();
    }
}
