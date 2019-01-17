package com.svg;

import android.graphics.Canvas;
import android.graphics.Path;

import java.util.ArrayList;

/**
 * com.svg
 * 2019/1/11 12:31
 * instructions：
 * author:liuhuiliang  email:825378291@qq.com
 **/
public class SVGPolygon extends SVGPath {
    public SVGPolygon(SVGEntity entity) {
        super(entity);
        // TODO Auto-generated constructor stub
    }

    private boolean isClose = false;
    private Path path;

    public void setNumbers(SVGPars.NumberParse numbers) {
        ArrayList<Float> points = numbers.numbers;
        if (points.size() > 1) {
            path = new Path();
            path.moveTo(points.get(0), points.get(1));
            for (int i = 2; i < points.size(); i += 2) {
                float x = points.get(i);
                float y = points.get(i + 1);
                path.lineTo(x, y);
            }
            if (isClose) {
                path.close();
            }
        }
    }

    public boolean isClose() {
        return isClose;
    }

    public void setClose(boolean isClose) {
        this.isClose = isClose;
    }

    @Override
    protected void childDraw(Canvas canvas, float factor) {
        // TODO Auto-generated method stub
        if (this.path != null) {
            super.childDraw(canvas, factor);
//			Path path = new Path();
//			matrix.postScale(factor, factor);
//			path.addPath(this.path, matrix);
//			if (doFill(factor)) {
//				doLimits(path);
//				canvas.drawPath(path, paint);
//			}
//			if (doStroke()) {
//				canvas.drawPath(path, paint);
//			}
//			matrix.reset();
        }
    }
}
