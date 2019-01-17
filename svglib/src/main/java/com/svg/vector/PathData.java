package com.svg.vector;

import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;

import com.utils.StringUtil;

/**
 * com.svg.vector
 * 2019/1/11 12:34
 * instructions：
 * author:liuhuiliang  email:825378291@qq.com
 **/
public class PathData {
    private Integer fillColor;
    private Integer fillAlpha;
    private Path mPath;
    private String name;
    private Float strokeWidth;
    private Integer strokeColor;
    private Integer strokeAlpha;
    private Paint.Cap strokeLineCap;
    private Paint.Join join;
    private Float strokeDashoffset = 0f;
    private float[] strokeDasharray;
    private String id;
    private Paint paint;
    private Path paintPath;
    private Matrix matrix;

    public PathData() {
        // TODO Auto-generated constructor stub
        paintPath = new Path();
        matrix = new Matrix();
        matrix.setValues(new float[] { 1.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f,
                0.0f, 0.0f, 1.0f });
    }

    public void setPaint(Paint paint) {
        this.paint = paint;
    }

    @Override
    public boolean equals(Object o) {
        // TODO Auto-generated method stub
        if (o == null
                || !o.getClass().getName().equals(PathData.class.getName())
                || id == null)
            return false;
        PathData pathData = (PathData) o;
        return id.equals(pathData.getId());
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setStrokeDashoffset(String strokeDashoffset) {
        this.strokeDashoffset = Float.valueOf(strokeDashoffset);
    }

    public void setStrokeDasharray(String strokeDasharray) {
        String[] strings = strokeDasharray.split(" ");
        this.strokeDasharray = new float[strings.length];
        for (int i = 0; i < strings.length; i++) {
            this.strokeDasharray[i] = Float.valueOf(strings[i]);
        }
    }

    public DashPathEffect getDashPathEffect() {
        if (strokeDasharray == null || strokeDasharray.length < 2)
            return null;
        return new DashPathEffect(strokeDasharray, strokeDashoffset);
    }

    public Integer getFillAlpha() {
        return fillAlpha;
    }

    public Paint.Join getJoin() {
        return join;
    }

    public void setJoin(String join) {
        if (join.equals("bevel"))
            this.join = Paint.Join.BEVEL;
        if (join.equals("miter"))
            this.join = Paint.Join.MITER;
        if (join.equals("round"))
            this.join = Paint.Join.ROUND;
    }

    public void setFillAlpha(String fillAlpha) {
        this.fillAlpha = (int) (Float.valueOf(fillAlpha) * StringUtil.FF);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getStrokeWidth() {
        return strokeWidth;
    }

    public void setStrokeWidth(float strokeWidth) {
        this.strokeWidth = strokeWidth;
    }

    public Integer getStrokeColor() {
        return strokeColor;
    }

    public void setStrokeColor(String strokeColor) {
        this.strokeColor = StringUtil.color(strokeColor);
    }

    public Integer getStrokeAlpha() {
        return (int) (strokeAlpha * StringUtil.FF);
    }

    public void setStrokeAlpha(float strokeAlpha) {
        this.strokeAlpha = (int) (strokeAlpha * StringUtil.FF);
    }

    public Paint.Cap getStrokeLineCap() {
        return strokeLineCap;
    }

    public void setStrokeLineCap(String strokeLineCap) {
        if (strokeLineCap.equals("butt")) {
            this.strokeLineCap = Paint.Cap.BUTT;
        } else if (strokeLineCap.equals("round")) {
            this.strokeLineCap = Paint.Cap.ROUND;
        } else if (strokeLineCap.equals("square")) {
            this.strokeLineCap = Paint.Cap.SQUARE;
        }
    }

    public void setFillColor(String fillColor) {
        this.fillColor = StringUtil.color(fillColor);
    }

    public void setPath(String pathData) {
        mPath = VectorPathParser.createPathFromPathData(pathData);
    }

    public Path getPath() {
        return mPath;
    }

    public Integer getFillColor() {
        return fillColor;
    }

    protected void draw(Canvas canvas, float factor) {
        int defAlpha = paint.getAlpha();
        Paint.Style defStyle = paint.getStyle();
        float defStrokeWidth = paint.getStrokeWidth();
        matrix.postScale(factor, factor);
        paintPath.addPath(mPath, matrix);
        Paint.Cap cap = getStrokeLineCap();
        PathEffect pathEffect = getDashPathEffect();
        if (pathEffect != null)
            paint.setPathEffect(pathEffect);
        if (cap != null)
            paint.setStrokeCap(cap);
        Paint.Join join = getJoin();
        if (join != null)
            paint.setStrokeJoin(join);
        Integer fillColor = getFillColor();
        if (fillColor != null) {
            paint.setColor(fillColor);
            Integer fillAlpha =getFillAlpha();
            if (fillAlpha != null)
                paint.setAlpha(fillAlpha);
            canvas.drawPath(paintPath, paint);
            paint.setAlpha(defAlpha);
        }
        Integer strokeColor = getStrokeColor();
        if (strokeColor != null) {
            Float strokeWidth = getStrokeWidth();
            if (strokeWidth != null) {
                paint.setStrokeWidth(getStrokeWidth());
            }
            Integer strokeAlpha = getStrokeAlpha();
            if (strokeAlpha != null)
                paint.setAlpha(strokeAlpha);
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(paintPath, paint);

            paint.setStrokeWidth(defStrokeWidth);
            paint.setStyle(defStyle);
            paint.setAlpha(defAlpha);
        }
        paintPath.reset();
        paint.reset();
        matrix.reset();
    }
}
