package com.kisen.sindemo.util;

/**
 * @Title :
 * @Description :
 * @Version :
 * Created by huang on 2017/2/27.
 */

public class MotionPoint {

    public static final int MOVE = 0;
    public static final int SIN = 1;
    public static final int CIRCLE = 2;
    public static final int CURVE = 3;
    /**
     * 斐波那契数列
     */
    public static final int FIBBONACCI = 4;
    private int type;

    private float x, y, r;

    private float[] points;
    private int order;
    private int count;

    /**
     * Circle
     */
    MotionPoint(float x, float y, float r) {
        this.type = CIRCLE;
        this.x = x;
        this.y = y;
        this.r = r;
    }

    MotionPoint(int order, float... points) {
        this.type = CURVE;
        this.order = order;
        this.points = points;
    }

    /**
     * 通用类型
     */
    MotionPoint(int type, float x, float y) {
        this.type = type;
        this.x = x;
        this.y = -y;
    }

    public static MotionPoint fibonacci(float x, float y, int count) {
        return new MotionPoint(FIBBONACCI, x, y).setCount(count);
    }

    public static MotionPoint curve(int order, float... points) {
        return new MotionPoint(order, points);
    }

    public static MotionPoint circle(float x, float y, float r) {
        return new MotionPoint(x, y, r);
    }

    public static MotionPoint sin(float x, float y) {
        return new MotionPoint(SIN, x, y);
    }

    public static MotionPoint move(float x, float y) {
        return new MotionPoint(MOVE, x, y);
    }

    public MotionPoint setCount(int count) {
        this.count = count;
        return this;
    }

    public int getCount() {
        return count;
    }

    public int getType() {
        return type;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getR() {
        return r;
    }

    public int getOrder() {
        return order;
    }

    public float[] getPoints() {
        return points;
    }
}
