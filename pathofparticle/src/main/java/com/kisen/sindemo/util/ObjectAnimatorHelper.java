package com.kisen.sindemo.util;

import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;

import com.kisen.sindemo.BaseActivity;

/**
 * @Title :
 * @Description :
 * @Version :
 * Created by huang on 2017/2/28.
 */

public class ObjectAnimatorHelper {

    private BaseActivity activity;

    private ObjectAnimatorHelper(BaseActivity activity){
        this.activity = activity;
    }

    public static ObjectAnimator object(BaseActivity activity, TypeEvaluator evaluator, MotionPath path) {

        ObjectAnimatorHelper helper = new ObjectAnimatorHelper(activity);

        return ObjectAnimator.ofObject(
                helper,
                "point",
                evaluator,
                path.getPoints().toArray());
    }


    /**
     * 反射用到的方法
     * @param point ObjectAnimator.ofObject() 方法会反射本类的"point"
     */
    public void setPoint(MotionPoint point) {
        activity.setPoint(point);
    }
}
