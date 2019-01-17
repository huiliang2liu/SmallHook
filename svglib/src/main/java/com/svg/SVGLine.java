package com.svg;

import android.graphics.Canvas;
import android.graphics.Path;

/**
 * com.svg
 * 2019/1/11 12:29
 * instructions：
 * author:liuhuiliang  email:825378291@qq.com
 **/
public class SVGLine extends SVGPath {
    public SVGLine(SVGEntity entity) {
        super(entity);
        // TODO Auto-generated constructor stub
    }

    private float x1 = 0;
    private float x2 = 0;
    private float y1 = 0;
    private float y2 = 0;

    public float getX1() {
        return x1;
    }

    public void setX1(float x1) {
        this.x1 = x1;
    }

    public float getX2() {
        return x2;
    }

    public void setX2(float x2) {
        this.x2 = x2;
    }

    public float getY1() {
        return y1;
    }

    public void setY1(float y1) {
        this.y1 = y1;
    }

    public float getY2() {
        return y2;
    }

    public void setY2(float y2) {
        this.y2 = y2;
    }

    @Override
    protected void childDraw(Canvas canvas, float factor) {
        // TODO Auto-generated method stub
        if (path == null) {
            path = new Path();
            path.moveTo(x1, y1);
            path.lineTo(x2, y2);
        }
        super.childDraw(canvas, factor);
        // if (doStroke()) {
        // float x1 = this.x1 * factor;
        // float x2 = this.x2 * factor;
        // float y1 = this.y1 * factor;
        // float y2 = this.y2 * factor;
        // doLimits(x1, y1);
        // doLimits(x2, y2);
        // canvas.drawLine(x1, y1, x2, y2, paint);
        // }
    }

}
