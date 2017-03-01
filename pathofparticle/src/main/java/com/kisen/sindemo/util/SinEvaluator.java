package com.kisen.sindemo.util;

import android.animation.TypeEvaluator;
import android.util.Log;

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
            case SinPoint.FIBBONACCI:
                int count = endValue.getCount();
                //当前所在的螺旋线(数列中第几个数对应的弧)
                int currentIndex = (int) (fraction * count) + 1;
                //每段四分之一圆弧的运动时间
                float t_count = 1f / count;
                int currentR = fibbonacci(currentIndex);
                //当前运动圆弧轨迹的原点
                float ox = fibOx(currentIndex, endValue);
                float oy = fibOy(currentIndex, endValue);

                double thetaFib;
                float t = (fraction - (currentIndex - 1) * t_count)/t_count;
                switch (currentIndex % 4) {
                    case 1://第一象限:原点x坐标变化-->负方向移动
                        thetaFib = Math.PI * t / 2;
                        break;
                    case 2://第二象限:原点y坐标变化-->负方向移动
                        thetaFib = Math.PI * t / 2 + Math.PI / 2;
                        break;
                    case 3://第三象限:原点x坐标变化-->正方向移动
                        thetaFib = Math.PI * t / 2 + Math.PI;
                        break;
                    default://第四象限:原点y坐标变化-->正方向移动
                        thetaFib = Math.PI * t / 2 + Math.PI * 3 / 2;
                        break;
                }
                x = (float) (ox + currentR * Math.cos(thetaFib));
                y = (float) (oy + currentR * Math.sin(thetaFib));
//                Log.e("point", "thetaFib：" + thetaFib + "  x：" + x + "  y：" + y + "  ox：" + ox + "  oy：" + oy);

//                //根据公式得到 r = Math.pow(e,2*ln(fy)*theta/Math.PI
//                int count = endValue.getCount();
//                double fy = 137.5f / 180 * Math.PI;
//                double thetaFib = count / 4f * 2 * Math.PI * fraction;
//                double r = Math.pow(Math.E, 2 * Math.log1p(fy - 1) * thetaFib / Math.PI);
//                x = (float) (endValue.getX() + r * Math.cos(thetaFib));
//                y = (float) (endValue.getY() + r * Math.sin(thetaFib));
//                Log.e("point", "thetaFib：" + thetaFib + "  x：" + x + "  y：" + y);
                break;
            default:
                x = endValue.getX();
                y = 0;
                break;
        }
        return SinPoint.move(x, y);
    }

    /**
     * 得到斐波那契数列对应位置的值
     * [（1＋√5）/2]^n /√5 － [（1－√5）/2]^n /√5
     */
    public int fibbonacci(int index) {
        int f;
        double sqrt5 = Math.sqrt(5);
        f = (int) (Math.pow(((1 + sqrt5) / 2), index) / sqrt5 - Math.pow(((1 - sqrt5) / 2), index) / sqrt5);
        if (f == 0)
            return fibbonacci(index - 1);
        return f;
    }

    private float fibOy(int index, SinPoint endValue) {
        if (index % 2 == 1)
            index = index - 1;
        if (index == 2 || index == 0)
            return endValue.getY();
        //index % 4 == 1    判断是否是第二象限
        return fibOy(index - 2, endValue) + (index % 4 == 0 ? 1 : -1) * fibbonacci(index - 2);
    }

    private float fibOx(int index, SinPoint endValue) {
        if (index % 2 == 0)
            index = index - 1;
        if (index == 1)
            return endValue.getX();
        //(index + 1) % 4 == 0   判断是否是第三象限
        return fibOx(index - 2, endValue) + ((index + 1) % 4 == 0 ? 1 : -1) * fibbonacci(index - 2);
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
