package com.svg;

import android.graphics.Canvas;
import android.graphics.Path;

/**
 * com.svg
 * 2019/1/11 12:31
 * instructions：
 * author:liuhuiliang  email:825378291@qq.com
 **/
public class SVGPath extends SVGD {
    public SVGPath(SVGEntity entity) {
        super(entity);
        // TODO Auto-generated constructor stub
    }

    protected Path path;

    public Path getPath() {
        return path;
    }

    public void setPath(Path path) {
        this.path = path;
    }

    @Override
    protected void childDraw(Canvas canvas, float factor) {
        Path path1 = factorPath(path, factor);
        if (doFill(factor)) {
            doLimits(path1);
            canvas.drawPath(path1, paint);
        }
        if (doStroke()) {
            canvas.drawPath(path1, paint);
        }
    }

}
