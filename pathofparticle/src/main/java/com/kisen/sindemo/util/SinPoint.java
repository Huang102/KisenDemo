package com.kisen.sindemo.util;

/**
 * @Title :
 * @Description :
 * @Version :
 * Created by huang on 2017/2/27.
 */

public class SinPoint {

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
    SinPoint(float x, float y, float r) {
        this.type = CIRCLE;
        this.x = x;
        this.y = y;
        this.r = r;
    }

    SinPoint(int order, float... points) {
        this.type = CURVE;
        this.order = order;
        this.points = points;
    }

    /**
     * 通用类型
     */
    SinPoint(int type, float x, float y) {
        this.type = type;
        this.x = x;
        this.y = -y;
    }

    public static SinPoint fibbonacci(float x, float y, int count) {
        return new SinPoint(FIBBONACCI, x, y).setCount(count);
    }

    public static SinPoint curve(int order, float... points) {
        return new SinPoint(order, points);
    }

    public static SinPoint circle(float x, float y, float r) {
        return new SinPoint(x, y, r);
    }

    public static SinPoint sin(float x, float y) {
        return new SinPoint(SIN, x, y);
    }

    public static SinPoint move(float x, float y) {
        return new SinPoint(MOVE, x, y);
    }

    public SinPoint setCount(int count) {
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
