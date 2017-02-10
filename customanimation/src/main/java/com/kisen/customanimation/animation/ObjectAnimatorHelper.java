package com.kisen.customanimation.animation;

import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.view.View;

/**
 * @Description : helper工具类
 * Created by huang on 2017/2/9.
 */

public class ObjectAnimatorHelper {

    private static final String PROPERTY_NAME = "pathLocation";
    private View target;

    /**
     * @param target    移动的View
     * @param converter 估值器
     * @param values    View移动移动的路径
     * @return ObjectAnimator
     */
    public static ObjectAnimator ofObject(View target, TypeEvaluator converter, Object... values) {
        ObjectAnimatorHelper helper = new ObjectAnimatorHelper(target);
        return ObjectAnimator.ofObject(helper, PROPERTY_NAME, converter, values);
    }

    private ObjectAnimatorHelper(View target) {
        this.target = target;
    }

    /**
     * 被反射的方法
     *
     * @param p 移动到的点坐标
     */
    public void setPathLocation(PathPoint p) {
        if (target != null) {
            target.setTranslationX(p.mPathX);
            target.setTranslationY(p.mPathY);
        }
    }

}
