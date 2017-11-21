package com.kisen.sindemo.util;

import java.util.ArrayList;

/**
 * @Title :
 * @Description :
 * @Version :
 * Created by huang on 2017/2/27.
 */

public class MotionPath {

    private ArrayList<MotionPoint> points = new ArrayList<>();

    public void fibonacci(float x, float y, int count) {
        points.add(MotionPoint.fibonacci(x, y, count));
    }

    public void circle(float x, float y, float r) {
        points.add(MotionPoint.circle(x, y, r));
    }

    public void curve(int order, float... cp) {
        points.add(MotionPoint.curve(order, cp));
    }

    public void sin(float x, float y) {
        points.add(MotionPoint.sin(x, y));
    }

    public void move(float x, float y) {
        points.add(MotionPoint.move(x, y));
    }

    public ArrayList<MotionPoint> getPoints() {
        return points;
    }
}
