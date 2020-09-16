package com.svg;

import android.graphics.Canvas;
import android.graphics.Path;
import android.util.Log;

/**
 * com.svg
 * 2019/1/11 12:32
 * instructions：
 * author:liuhuiliang  email:825378291@qq.com
 **/
public class SVGRect extends SVGPath {
    public SVGRect(SVGEntity entity) {
        super(entity);
        // TODO Auto-generated constructor stub
    }

    private float x = 0;
    private float y = 0;
    private float width = 0;
    private float height = 0;

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(String width) {
        if(width.endsWith("%")){
            this.width=entity.getViewBoxWidth()*Float.valueOf(width.substring(0,width.length()-1))/100;
            return;
        }
        if(width.endsWith("px")){
            this.width=Float.valueOf(width.substring(0,width.length()-2));
            return;
        }
        this.width = Float.valueOf(width);
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(String height) {
        if(height.endsWith("%")){
            this.height=entity.getViewBoxHeigth()*Float.valueOf(height.substring(0,height.length()-1))/100;
            return;
        }
        if(height.endsWith("px")){
            this.height=Float.valueOf(height.substring(0,height.length()-2));
            return;
        }
        this.height = Float.valueOf(height);
    }

    @Override
    protected void childDraw(Canvas canvas, float factor) {
        // TODO Auto-generated method stub
        if (path == null) {
            path = new Path();
            path.addRect(x, y, x + width, y + height, Path.Direction.CW);
            path.close();
        }
        super.childDraw(canvas, factor);
    }

}
