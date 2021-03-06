package com.svg;

import android.graphics.Matrix;

import java.util.ArrayList;

/**
 * com.svg
 * 2019/1/11 11:01
 * instructions：
 * author:liuhuiliang  email:825378291@qq.com
 **/
public class Gradient {
    String id;
    String xlink;
    boolean isLinear;
    float x1, y1, x2, y2;
    float x, y, radius;
    ArrayList<Float> positions = new ArrayList<Float>();
    ArrayList<Integer> colors = new ArrayList<Integer>();
    Matrix matrix = null;


    boolean isRadial = false;

    public Gradient createChild(Gradient g) {
        Gradient child = new Gradient();
        child.id = g.id;
        child.xlink = id;
        child.isLinear = g.isLinear;
        child.x1 = g.x1;
        child.x2 = g.x2;
        child.y1 = g.y1;
        child.y2 = g.y2;
        child.x = g.x;
        child.y = g.y;
        child.radius = g.radius;
        child.positions = positions;
        child.colors = colors;
        child.matrix = matrix;
        if (g.matrix != null) {
            if (matrix == null) {
                child.matrix = g.matrix;
            } else {
                Matrix m = new Matrix(matrix);
                m.preConcat(g.matrix);
                child.matrix = m;
            }
        }
        return child;
    }
}
