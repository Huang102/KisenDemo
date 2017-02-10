package com.kisen.bottompopview.popview;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.support.v4.view.NestedScrollingChild;
import android.support.v4.widget.NestedScrollView;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;
import android.widget.ScrollView;

import com.kisen.bottompopview.R;

/**
 * @Title :
 * @Description :
 * @Version :
 * Created by huang on 2017/2/10.
 */

public class BouncingMenu {

    private final BouncingView mBouncingView;
    private final ViewGroup mParentVG;
    private View mRootView;
    private final ViewGroup mCustomContainer;

    public BouncingMenu(Activity activity, View content) {
        mParentVG = (ViewGroup) activity.findViewById(android.R.id.content);
        /*
        //不断地往上追溯到帧布局"@android:id/content" 锚点view
        mParentVG = findRootParent(view);
         */
        mRootView = activity.getLayoutInflater().inflate(R.layout.view_bouncing, mParentVG, false);
        mBouncingView = (BouncingView) mRootView.findViewById(R.id.bouncing_view);
        mBouncingView.setAnimationListener(new BouncingView.AnimationListener(){
            @Override
            public void showContent() {
                mCustomContainer.setVisibility(View.VISIBLE);
                mCustomContainer.scheduleLayoutAnimation();
            }
        });
        mCustomContainer = (ViewGroup) mRootView.findViewById(R.id.custom_container);
        if (content.getParent() != null) {
            ViewGroup parentView = (ViewGroup) content.getParent();
            parentView.removeView(content);
        }
        if (content instanceof NestedScrollingChild || content instanceof ScrollView){
            mCustomContainer.addView(content);
        } else {
            NestedScrollView scrollView = new NestedScrollView(content.getContext());
            mCustomContainer.addView(scrollView);
            scrollView.addView(content);
        }
        mCustomContainer.setVisibility(View.GONE);
    }

    public static BouncingMenu makeMenu(Activity activity, View content) {
        return new BouncingMenu(activity, content);
    }

    /**
     * @param view 锚点view
     * @return
     */
    private ViewGroup findRootParent(View view) {
        do {
            if (view instanceof FrameLayout) {
                if (view.getId() == android.R.id.content) {
                    return (ViewGroup) view;
                }
            }
            ViewParent parent = view.getParent();
            view = parent instanceof View ? (View) parent : null;
        } while (view != null);
        return null;
    }

    public BouncingMenu show() {
        //显示--往FrameLayout里面addView(XXXX)
        if (mRootView.getParent() != null)
            mParentVG.removeView(mRootView);
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mParentVG.addView(mRootView, lp);
        //开启自定义控件动画
        mBouncingView.show();
        return this;
    }

    public void dismiss() {
        //移除rootView
        ObjectAnimator animator = ObjectAnimator.ofFloat(mRootView, "translationY", 0, mRootView.getHeight());
        animator.setDuration(600);
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mParentVG.removeView(mRootView);
                mRootView = null;
            }
        });
        animator.start();
    }

}
