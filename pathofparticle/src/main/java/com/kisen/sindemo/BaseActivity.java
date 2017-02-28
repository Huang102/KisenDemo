package com.kisen.sindemo;

import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.kisen.sindemo.util.BgView;
import com.kisen.sindemo.util.ObjectAnimatorHelper;
import com.kisen.sindemo.util.SinPath;
import com.kisen.sindemo.util.SinPoint;

/**
 * @Title :
 * @Description :
 * @Version :
 * Created by huang on 2017/2/28.
 */

public abstract class BaseActivity extends AppCompatActivity {

    protected BgView bgView;
    protected View pointView;
    protected ObjectAnimator animator;

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        pointView = findViewById(R.id.point);
        bgView = (BgView) findViewById(R.id.bg_view);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        setupView();
    }

    protected void initAnimator(){
        animator = ObjectAnimatorHelper.object(this, getEvaluator(), getPath());
    }

    protected abstract SinPath getPath();

    protected abstract TypeEvaluator getEvaluator();

    protected abstract void setupView();

    public void setPoint(SinPoint point) {
        pointView.setTranslationX(point.getX());
        pointView.setTranslationY(point.getY());
        bgView.lineTo(point.getX(), point.getY());
    }
}
