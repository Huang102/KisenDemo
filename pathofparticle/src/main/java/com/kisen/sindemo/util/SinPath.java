package com.kisen.sindemo.util;

import java.util.ArrayList;

/**
 * @Title :
 * @Description :
 * @Version :
 * Created by huang on 2017/2/27.
 */

public class SinPath {

    private ArrayList<SinPoint> points = new ArrayList<>();

    public void fibbonacci(float x, float y, int count) {
        points.add(SinPoint.fibbonacci(x, y, count));
    }

    public void circle(float x, float y, float r) {
        points.add(SinPoint.circle(x, y, r));
    }

    public void curve(int order, float... cp) {
        points.add(SinPoint.curve(order, cp));
    }

    public void sin(float x, float y) {
        points.add(SinPoint.sin(x, y));
    }

    public void move(float x, float y) {
        points.add(SinPoint.move(x, y));
    }

    public ArrayList<SinPoint> getPoints() {
        return points;
    }
}
