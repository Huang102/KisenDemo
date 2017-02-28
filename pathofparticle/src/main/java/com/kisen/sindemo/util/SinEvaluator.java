package com.kisen.sindemo.util;

import android.animation.TypeEvaluator;

/**
 * @Title :
 * @Description :
 * @Version :
 * Created by huang on 2017/2/27.
 */

public class SinEvaluator implements TypeEvaluator<SinPoint> {

    private float a, w, p;

    /**
     * 控制三角函数的三个变量值
     */
    public SinEvaluator(float a, float w, float p) {
        this.a = a;
        this.w = w;
        this.p = p;
    }

    @Override
    public SinPoint evaluate(float fraction, SinPoint startValue, SinPoint endValue) {
        //fraction == time
        float x, y;
        switch (endValue.getType()) {
            case SinPoint.SIN:
                x = startValue.getX() + (endValue.getX() - startValue.getX()) * fraction;
                y = (float) (a * Math.sin(w * x + p));
                break;
            case SinPoint.CIRCLE:
//                x = fraction < 0.5 ? endValue.getR() * fraction * 4 : endValue.getR() * 4 * (1 - fraction);
//                float v = (float) Math.sqrt(endValue.getR() * endValue.getR() - (x - endValue.getX()) * (x - endValue.getX())) + endValue.getY();
//                y = fraction < 0.5 ? -v : v;
                //旋转角度
                double theta = 2 * Math.PI * fraction;
                x = (float) (endValue.getX() - endValue.getR() * Math.cos(theta));
                y = (float) (endValue.getY() - endValue.getR() * Math.sin(theta));
                break;
            case SinPoint.CURVE:
                int order = endValue.getOrder();
                float[] points = endValue.getPoints();
                int length = points.length;
                if (length != order * 2)
                    throw new IllegalAccessError("可变参数对应的贝塞尔阶数不一致");
//                float t = fraction;
//                float _t = 1 - fraction;
//                float p0x = startValue.getX();
//                float p0y = startValue.getY();
//                float p1x = points[0];
//                float p1y = points[1];
//                float p2x = points[2];
//                float p2y = points[3];
//                switch (order) {
//                    case 2:
//                        x = _t * _t * p0x + 2 * t * _t * p1x + t * t * p2x;
//                        y = _t * _t * p0y + 2 * t * _t * p1y + t * t * p2y;
//                        break;
//                    case 3:
//                        float p3x = points[4];
//                        float p3y = points[5];
//                        x = _t * _t * _t * p0x + 3 * p1x * t * _t * _t + 3 * p2x * t * t * _t + p3x * t * t * t;
//                        y = _t * _t * _t * p0y + 3 * p1y * t * _t * _t + 3 * p2y * t * t * _t + p3y * t * t * t;
//                        break;
//                    default:
                x = bT(true, fraction, startValue, endValue);
                y = bT(false, fraction, startValue, endValue);
//                        break;
//                }
                break;
            default:
                x = endValue.getX();
                y = 0;
                break;
        }
        return SinPoint.move(x, y);
    }

    /**
     * 贝塞尔曲线公式
     */
    private float bT(boolean x, float t, SinPoint startValue, SinPoint endValue) {
        float _t = 1 - t;
        //贝塞尔曲线阶数
        int order = endValue.getOrder();
        //贝塞尔曲线所有控制点和终点
        float[] points = endValue.getPoints();
        if (order > 1) {
            float p = 0;
            float pi = 0;
            for (int i = 0; i <= order; i++) {
                if (i == 0) {//默认起始点
                    pi = x ? startValue.getX() : startValue.getY();
                } else {
                    pi = x ? points[2 * (i - 1)] : points[2 * (i - 1) + 1];
                }
                //贝塞尔曲线公式
                p += pi * c(i, order) * Math.pow(_t, order - i) * Math.pow(t, i);
            }
            return p;
        }
        return 0;
    }

    /**
     * 求二项式系数 n!/(r!*(n-r)!)
     *
     * @return 返回结果
     */
    private int c(int r, int n) {
        int c;

        int nC = 1;
        for (int i = 1; i <= n; i++) {
            nC *= i;
        }
        int rC = 1;
        for (int i = 1; i <= r; i++) {
            rC *= i;
        }

        int n_rC = 1;
        for (int i = 1; i <= n - r; i++) {
            n_rC *= i;
        }

        c = nC / (rC * n_rC);
        return c;
    }

}
