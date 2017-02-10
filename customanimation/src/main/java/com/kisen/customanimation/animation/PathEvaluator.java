package com.kisen.customanimation.animation;

import android.animation.TypeEvaluator;

/**
 * @Description : 估值器，计算运动路径
 * Created by huang on 2017/2/9.
 */
public class PathEvaluator implements TypeEvaluator<PathPoint> {

    @Override
    public PathPoint evaluate(float t, PathPoint startValue, PathPoint endValue) {
        //计算、估值
        //fraction 动画执行的百分比 等同于时间 t
        float x, y;
        switch (endValue.mOperation) {
            case PathPoint.CUBIC://贝塞尔曲线运动计算方式
                float oneMinusT = 1 - t;
                x = startValue.mPathX * oneMinusT * oneMinusT * oneMinusT
                        + 3 * endValue.mPathX * t * oneMinusT * oneMinusT
                        + 3 * endValue.mPathX1 * t * t * oneMinusT
                        + endValue.mPathX2 * t * t * t;
                y = startValue.mPathY * oneMinusT * oneMinusT * oneMinusT
                        + 3 * endValue.mPathY * t * oneMinusT * oneMinusT
                        + 3 * endValue.mPathY1 * t * t * oneMinusT
                        + endValue.mPathY2 * t * t * t;
                break;
            case PathPoint.LINE://直线运动计算方式
                //x,y = 起始点 + t*起始点和终点的距离
                x = startValue.mPathX + t * (endValue.mPathX - startValue.mPathX);
                y = startValue.mPathY + t * (endValue.mPathY - startValue.mPathY);
                break;
            default:
                x = endValue.mPathX;
                y = endValue.mPathY;
                break;
        }
        return PathPoint.moveTo(x, y);
    }
}
