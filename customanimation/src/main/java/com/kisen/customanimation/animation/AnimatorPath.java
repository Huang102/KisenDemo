package com.kisen.customanimation.animation;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @Title : 自定义动画路径
 * @Description : 用于存储View移动的坐标
 * Created by huang on 2017/2/9.
 */
public class AnimatorPath {

    /**
     * 保存的路径列表
     */
    private ArrayList<PathPoint> points = new ArrayList<>();

    /**
     * 移动到某个点
     *
     * @param x 移动到位置的x坐标
     * @param y 移动到位置的y坐标
     * @see android.graphics.Path moveTo()
     */
    public void moveTo(float x, float y) {
        points.add(PathPoint.moveTo(x, y));
    }

    /**
     * 从初始点到某一个点画一条直线
     *
     * @param x 终点的x坐标
     * @param y 终点的y坐标
     */
    public void lineTo(float x, float y) {
        points.add(PathPoint.lineTo(x, y));
    }

    /**
     * 从初始点到某一个点沿着贝塞尔曲线运动到终点3
     *
     * @param pointX0 第一个拐点的x坐标
     * @param pointY0 第一个拐点的y坐标
     * @param pointX1 第二个拐点的x坐标
     * @param pointY1 第二个拐点的y坐标
     * @param pointX2 第三个拐点的x坐标
     * @param pointY2 第三个拐点的y坐标
     */
    public void cubicTo(float pointX0, float pointY0, float pointX1, float pointY1, float pointX2, float pointY2) {
        points.add(PathPoint.cubicTo(pointX0, pointY0, pointX1, pointY1, pointX2, pointY2));
    }

    public Collection<PathPoint> getPoints() {
        return points;
    }

}
