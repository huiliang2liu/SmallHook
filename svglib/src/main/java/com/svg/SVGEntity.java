package com.svg;

import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Path;
import android.graphics.RadialGradient;
import android.graphics.RectF;
import android.graphics.Shader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * com.svg
 * 2019/1/11 12:26
 * instructions：
 * author:liuhuiliang  email:825378291@qq.com
 **/
public class SVGEntity extends ASVG {

    private HashMap<String, Gradient> gradientRefMap = new HashMap<String, Gradient>();
    RectF bounds = null;
    private List<SVGD> objects;
    private boolean whiteMode = false;
    private Integer searchColor = null;
    private Integer replaceColor = null;
    private Matrix matrix;
    private float[] values;

    public SVGEntity() {
        objects = new ArrayList<SVGD>();
        matrix = new Matrix();
        values = new float[9];
        // TODO Auto-generated constructor stub
    }

    public void setColorSwap(Integer searchColor, Integer replaceColor) {
        this.searchColor = searchColor;
        this.replaceColor = replaceColor;
    }

    public void setWhiteMode(boolean whiteMode) {
        this.whiteMode = whiteMode;
    }

    public void setBounds(RectF bounds) {
        this.bounds = bounds;
    }

    @Override
    public Path createPath(float factor) {
        // TODO Auto-generated method stub
        Path path = new Path();
        for (int i = 0; i < objects.size(); i++) {
            // if (i == 1) {
            SVGD svgd = objects.get(i);
            path.addPath(svgd.factorPath(svgd.toPath(), factor));
            // }
        }
        return path;
    }

    @Override
    public int color() {
        // TODO Auto-generated method stub
        if (objects == null || objects.size() <= 0)
            return objects.get(0).color();
        throw new RuntimeException("not find color");
    }

    public HashMap<String, Gradient> getGradientRefMap() {
        return gradientRefMap;
    }

    public Shader getShader(String key, float factor) {
        if (gradientRefMap.containsKey(key)) {
            Gradient gradient = gradientRefMap.get(key);
            if (gradient.xlink != null) {
                Gradient parent = getGradient(gradient.xlink);
                if (parent != null) {
                    gradient = parent.createChild(gradient);
                }
            }
            int[] colors = new int[gradient.colors.size()];
            for (int i = 0; i < colors.length; i++) {
                colors[i] = gradient.colors.get(i);
            }
            float[] positions = new float[gradient.positions.size()];
            for (int i = 0; i < positions.length; i++) {
                positions[i] = gradient.positions.get(i) * factor;
            }
            if (gradient.isRadial)
                if (gradient.xlink != null) {
                    Gradient parent = getGradient(gradient.xlink);
                    if (parent != null) {
                        gradient = parent.createChild(gradient);
                    }
                }
            Shader g;
            if (gradient.isRadial)
                g = new RadialGradient(gradient.x * factor,
                        gradient.y * factor, gradient.radius * factor, colors,
                        positions, Shader.TileMode.CLAMP);
            else
                g = new LinearGradient(gradient.x1 * factor, gradient.y1
                        * factor, gradient.x2 * factor, gradient.y2 * factor,
                        colors, positions, Shader.TileMode.CLAMP);
            if (gradient.matrix != null) {
                matrix.getValues(values);
                values[2] *= factor;
                values[5] *= factor;
                matrix.setValues(values);
                g.setLocalMatrix(matrix);
            }
            return g;
        }
        return null;
    }

    public Gradient getGradient(String key) {
        if (gradientRefMap.containsKey(key))
            return gradientRefMap.get(key);
        return null;
    }

    public void addGradient(String key, Gradient value) {
        gradientRefMap.put(key, value);
    }

    public void add(SVGD svgd) {
        svgd.setColorSwap(searchColor, replaceColor);
        svgd.setWhiteMode(whiteMode);
        objects.add(svgd);
    }

    public void setViewBox(String viewBoxWidth) {
        String strings[] = viewBoxWidth.split(" ");
        if (strings.length == 4) {
            float x = Float.valueOf(strings[0]);
            float y = Float.valueOf(strings[1]);
            float w = Float.valueOf(strings[2]);
            float h = Float.valueOf(strings[3]);
            setViewBoxWidth((int) (w - x));
            setViewBoxHeigth((int) (h - y));
        }
    }

    @Override
    protected void childDraw(Canvas canvas, float factor) {
        // TODO Auto-generated method stub
        for (int i = 0; i < objects.size(); i++) {
            objects.get(i).draw(canvas, factor);
            paint.reset();
        }
    }

}