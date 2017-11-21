package com.kisen.sindemo.util;

import android.animation.TypeEvaluator;

/**
 * @Title :
 * @Description :
 * @Version :
 * Created by huang on 2017/2/27.
 */

public class MotionEvaluator implements TypeEvaluator<MotionPoint> {

    private float a, w, p;

    /**
     * 控制三角函数的三个变量值
     */
    public MotionEvaluator(float a, float w, float p) {
        this.a = a;
        this.w = w;
        this.p = p;
    }

    @Override
    public MotionPoint evaluate(float fraction, MotionPoint startValue, MotionPoint endValue) {
        //fraction == time
        float x, y;
        switch (endValue.getType()) {
            case MotionPoint.SIN:
                x = startValue.getX() + (endValue.getX() - startValue.getX()) * fraction;
                y = (float) (a * Math.sin(w * x + p));
                break;
            case MotionPoint.CIRCLE:
//                x = fraction < 0.5 ? endValue.getR() * fraction * 4 : endValue.getR() * 4 * (1 - fraction);
//                float v = (float) Math.sqrt(endValue.getR() * endValue.getR() - (x - endValue.getX()) * (x - endValue.getX())) + endValue.getY();
//                y = fraction < 0.5 ? -v : v;
                //旋转角度
                double theta = 2 * Math.PI * fraction;
                x = (float) (endValue.getX() - endValue.getR() * Math.cos(theta));
                y = (float) (endValue.getY() - endValue.getR() * Math.sin(theta));
                break;
            case MotionPoint.CURVE:
                int order = endValue.getOrder();
                float[] points = endValue.getPoints();
                int length = points.length;
                if (length != order * 2)
                    throw new IllegalAccessError("可变参数对应的贝塞尔阶数不一致");
                x = bT(true, fraction, startValue, endValue);
                y = bT(false, fraction, startValue, endValue);
                break;
            case MotionPoint.FIBBONACCI:
                int count = endValue.getCount();
                //当前所在的螺旋线(数列中第几个数对应的弧)
                int currentIndex = (int) (fraction * count) + 1;
                //每段四分之一圆弧的运动时间
                float t_count = 1f / count;
                int currentR = fibonacci(currentIndex);
                //当前运动圆弧轨迹的原点
                float ox = fibOx(currentIndex, endValue);
                float oy = fibOy(currentIndex, endValue);

                double thetaFib;
                float t = (fraction - (currentIndex - 1) * t_count) / t_count;
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
        return MotionPoint.move(x, y);
    }

    /**
     * 得到斐波那契数列对应位置的值
     * [（1＋√5）/2]^n /√5 － [（1－√5）/2]^n /√5
     */
    public int fibonacci(int index) {
        int f;
        double sqrt5 = Math.sqrt(5);
        f = (int) (Math.pow(((1 + sqrt5) / 2), index) / sqrt5 - Math.pow(((1 - sqrt5) / 2), index) / sqrt5);
        if (f == 0)
            return fibonacci(index - 1);
        return f;
    }

    private float fibOy(int index, MotionPoint endValue) {
        if (index % 2 == 1)
            index = index - 1;
        if (index == 2 || index == 0)
            return endValue.getY();
        //index % 4 == 1    判断是否是第二象限
        return fibOy(index - 2, endValue) + (index % 4 == 0 ? 1 : -1) * fibonacci(index - 2);
    }

    private float fibOx(int index, MotionPoint endValue) {
        if (index % 2 == 0)
            index = index - 1;
        if (index == 1)
            return endValue.getX();
        //(index + 1) % 4 == 0   判断是否是第三象限
        return fibOx(index - 2, endValue) + ((index + 1) % 4 == 0 ? 1 : -1) * fibonacci(index - 2);
    }

    /**
     * 贝塞尔曲线公式
     */
    private float bT(boolean x, float t, MotionPoint startValue, MotionPoint endValue) {
        float _t = 1 - t;
        //贝塞尔曲线阶数
        int order = endValue.getOrder();
        //贝塞尔曲线所有控制点和终点
        float[] points = endValue.getPoints();
        if (order > 1) {
            float p = 0;
            float pi;
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

        int nC = factorial(n);

        int rC = factorial(r);

        int n_rC = factorial(n - r);

        return nC / (rC * n_rC);
    }

    /**
     * 阶乘递归算法
     */
    private int factorial(int n) {
        if (n <= 1)//0的阶乘为1
            return 1;
        return n * factorial(n - 1);
    }

}
