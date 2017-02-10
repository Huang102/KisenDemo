package com.kisen.customanimation.animation;

/**
 * @Description : view移动的位置信息存储类
 * Created by huang on 2017/2/9.
 */
public class PathPoint {

    /**
     * 动作指令定义
     */
    /**
     * 移动到某个点
     */
    private static final int MOVE = 0;
    /**
     * 直线运动到某个点
     */
    static final int LINE = 1;
    /**
     * 贝塞尔曲线运动到某个点
     */
    static final int CUBIC = 2;

    /**
     * 操作指令
     */
    int mOperation;

    /**
     * 存储View的X坐标
     */
    float mPathX;
    /**
     * 存储View的Y坐标
     */
    float mPathY;

    /**
     * 用于贝塞尔曲线坐标存储
     */
    float mPathX1, mPathX2, mPathY1, mPathY2;

    private PathPoint(int operation, float x, float y) {
        mOperation = operation;
        mPathX = x;
        mPathY = y;
    }

    private PathPoint(float pointX0, float pointY0, float pointX1, float pointY1, float pointX2, float pointY2) {
        mOperation = CUBIC;
        mPathX = pointX0;
        mPathY = pointY0;
        mPathX1 = pointX1;
        mPathY1 = pointY1;
        mPathX2 = pointX2;
        mPathY2 = pointY2;
    }

    /**
     * 存储移动到的坐标位置
     */
    static PathPoint moveTo(float x, float y) {
        return new PathPoint(MOVE, x, y);
    }

    /**
     * 存储直线运动到的坐标位置
     */
    static PathPoint lineTo(float x, float y) {
        return new PathPoint(LINE, x, y);
    }

    /**
     * 存储贝塞尔曲线的运动轨迹坐标
     */
    static PathPoint cubicTo(float pointX0, float pointY0, float pointX1, float pointY1, float pointX2, float pointY2) {
        return new PathPoint(pointX0, pointY0, pointX1, pointY1, pointX2, pointY2);
    }

}
