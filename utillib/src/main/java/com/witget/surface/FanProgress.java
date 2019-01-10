package com.witget.surface;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;

import java.util.ArrayList;
import java.util.List;

import hook.com.utillib.R;

/**
 * com.witget.surface
 * 2019/1/8 18:12
 * instructions：
 * author:liuhuiliang  email:825378291@qq.com
 **/
public class FanProgress extends RefreshSurfaceView {
    private int borderColor = Color.rgb(0xf3, 0xcf, 0x14);
    private int backgroundColor = Color.WHITE;
    private int w, h;
    private int distance = 10;
    private float r = 0;
    private int dAngle = 10;
    private float angle = 0;
    private int progress = 0;
    private LoadedListen loadedListen;
    private Paint paint;
    private Bitmap bmp;
    private Bitmap leaf;
    private List<Leaf> leafs;
    private int time = -50;
    private float speed = 2.5f;
    private float period = 2;

    {
        paint = new Paint();
        paint.setStrokeWidth(1);
        paint.setAntiAlias(true);
        bmp = BitmapFactory.decodeResource(getResources(),
                R.mipmap.fengshan);
        leaf = BitmapFactory.decodeResource(getResources(),
                R.mipmap.leaf);
        leafs = new ArrayList<>();
    }

    public FanProgress(Context context) {
        super(context);
    }

    public FanProgress(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public FanProgress(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    public FanProgress(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        if (attrs == null)
            return;
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.FanProgress, -1, -1);
        backgroundColor = a.getColor(R.styleable.FanProgress_backgroundColor, backgroundColor);
        speed = a.getFloat(R.styleable.FanProgress_speed, speed);
        period = a.getFloat(R.styleable.FanProgress_period, period);
        a.recycle();
    }

    @Override
    void refresh() {
        angle += dAngle;
        angle = angle > 360 ? angle - 360 : angle;
        addLeaf();
        if (leafs != null)
            for (int i = 0; i < leafs.size(); i++) {
                Leaf leaf = leafs.get(i);
                if (leaf.getX() < 0) {
                    leafs.remove(leaf);
                    continue;
                }
                leaf.setLeafAnge(5);
                leaf.setX(speed);
            }
        Canvas canvas = drawBoard();
        drow(canvas);
        post(canvas);
    }

    synchronized private void drow(Canvas canvas) {
        // TODO Auto-generated method stub
        setWHR();
        drowBorder(canvas);
    }

    private void drowBorder(Canvas canvas) {
        // TODO Auto-generated method stub
        drowBackground(canvas);
        drowLeaf(canvas);
        drowLeft(canvas);
        drowRight(canvas);
        drowFrame(canvas);
        drowBlades(canvas);
        drowProgress(canvas);
    }

    private RectF rectf, rectf1, rectf2;

    private void drowProgress(Canvas canvas) {
        // TODO Auto-generated method stub
        paint.setStyle(Paint.Style.FILL);
        if (progress == 0)
            return;
        if (rectf == null) {
            rectf = new RectF(0, 0, h, h);
            rectf1 = new RectF(w - distance - 2 * h, 0, w - distance - h, h);
            rectf2 = new RectF(w - distance - 2 * h, 0, w - distance - h, h);
        }
        float x = (float) (w - h - distance) * progress / 100;
        if (x < r) {
            float rd = (float) Math.toDegrees(Math.acos(1 - x / r));
            canvas.drawArc(rectf, 180 - rd, rd * 2, false, paint);
        } else if (x < w - 3 * r - distance) {
            canvas.drawArc(rectf, 90, 180, false, paint);
            canvas.drawRect(r, 0, x, h, paint);// 画矩形
        } else {
            canvas.drawArc(rectf, 90, 180, false, paint);
            canvas.drawRect(r, 0, w - distance - 3 * r, h, paint);// 画矩形
            canvas.drawArc(rectf1, 270, 180, false, paint);
            paint.setColor(backgroundColor);
            float rd = (float) Math.toDegrees(Math
                    .acos((x - w + distance + 3 * r) / r));
            canvas.drawArc(rectf2, 360 - rd, 2 * rd, false, paint);
            if (progress == 100 && loadedListen != null) {
                loadedListen.loaded();
                loadedListen = null;
            }
        }
    }

    private RectF lr, rr;
    private Path tl, bl;

    private void drowFrame(Canvas canvas) {
        // TODO Auto-generated method stub
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(borderColor);
        if (lr == null) {
            lr = new RectF(0, 0, h, h);
            tl = new Path();
            tl.moveTo(r, 0);
            tl.lineTo(w - 3 * r - distance, 0);
            bl = new Path();
            bl.moveTo(r, h);
            bl.lineTo(w - 3 * r - distance, h);
            rr = new RectF(w - 2 * h - distance, 0, w - h - distance, h);
        }
        canvas.drawArc(lr, 90, 180, false, paint);
        canvas.drawPath(tl, paint);
        canvas.drawPath(bl, paint);
        canvas.drawArc(rr, 270, 180, false, paint);
    }

    private Path pathRight;

    private void drowRight(Canvas canvas) {
        // TODO Auto-generated method stub
        if (pathRight == null) {
            pathRight = new Path();
            pathRight.moveTo(w - 3 * r - distance, 0);
            RectF oval = new RectF(w - 2 * h - distance, 0, w - h - distance, h);
            pathRight.arcTo(oval, 270, 180);
            pathRight.lineTo(w, h);
            pathRight.lineTo(w, 0);
            pathRight.close();
        }
        canvas.drawPath(pathRight, paint);
    }

    private void drowBackground(Canvas canvas) {
        // TODO Auto-generated method stub
        paint.setColor(backgroundColor);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawRect(0, 0, w, h, paint);
    }

    private Path pathLeft;

    private void drowLeft(Canvas canvas) {
        // TODO Auto-generated method stub
        if (pathLeft == null) {
            pathLeft = new Path();
            pathLeft.moveTo(0, 0);
            pathLeft.lineTo(0, h);
            pathLeft.lineTo(r, h);
            RectF oval = new RectF(0, 0, h, h);
            pathLeft.arcTo(oval, 90, 180);
            pathLeft.close();
        }
        canvas.drawPath(pathLeft, paint);
    }

    private float s = -123, dx, px, py;
    private Matrix matrix;

    private void drowBlades(Canvas canvas) {
        // TODO Auto-generated method stub
        paint.setColor(borderColor);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(w - r, r, r, paint);
        if (s == -123) {
            s = (float) (h - 4) / bmp.getWidth();
            dx = w - h + 2;
            px = w - h + 2 + bmp.getWidth() * s / 2;
            py = 2 + bmp.getWidth() * s / 2;
            matrix = new Matrix();
        }
        matrix.setScale(s, s);// 参数1 x轴缩放比例 ， 参数2 y轴缩放比例
        matrix.postTranslate(dx, 2);// 参数1 x轴坐标 ，参数2 y轴坐标
        matrix.postRotate(angle, px, py);// 参数1 旋转角度
        canvas.drawBitmap(bmp, matrix, paint);
    }

    private void drowLeaf(Canvas canvas) {
        // TODO Auto-generated method stub
        for (Leaf leaf : leafs) {
            canvas.drawBitmap(this.leaf, leaf.getMatrix(), paint);
        }
    }

    private void addLeaf() {
        // TODO Auto-generated method stub
        time += 50;
        // Log.e(TAG, "time=" + time);
        if (time % 3000 != 0)
            return;
        int a = (int) (Math.random() * 5);
        for (int i = 0; i < a; i++) {
            Leaf leaf = new Leaf();
            leaf.setX(-(int) (i * Math.random() * 20));
            leaf.setLeafAnge((int) (Math.random() * 360));
            leafs.add(leaf);
        }
        // Log.e(TAG, "leafs.size()=" + leafs.size() + " a=" + a);
    }

    private void setWHR() {
        // TODO Auto-generated method stub
        w = getWidth();
        h = getHeight();
        r = h / 2;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public void setLoadedListen(LoadedListen loadedListen) {
        this.loadedListen = loadedListen;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public float getPeriod() {
        return period;
    }

    public void setPeriod(float period) {
        this.period = period;
    }

    public int getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public static abstract class LoadedListen {
        public abstract void loaded();
    }

    private class Leaf {
        private float leafAnge = 0;
        private float statX = w - distance - h;
        private float x = w - distance - h;
        private float y = 0;
        private Matrix matrix = new Matrix();

        public float getX() {
            return x;
        }

        public Matrix getMatrix() {
            return matrix;
        }

        public void setX(float x) {
            this.x -= x;
            y = r
                    + (float) ((r - leaf.getWidth() / 2) * Math
                    .sin((statX - this.x) / (w / period) * Math.PI * 2));
            matrix.setRotate(getLeafAnge(),
                    FanProgress.this.leaf.getWidth() / 2,
                    FanProgress.this.leaf.getHeight() / 2);// 参数1
            // 旋转角度
            matrix.postTranslate(getX(),
                    getY() - FanProgress.this.leaf.getWidth() / 4);// 参数1 x轴坐标
            // ，参数2
            // y轴坐标
        }

        public float getLeafAnge() {
            return leafAnge;
        }

        public float getY() {
            return y;
        }

        public void setLeafAnge(float leafAnge) {
            this.leafAnge += leafAnge;
        }

    }
}
