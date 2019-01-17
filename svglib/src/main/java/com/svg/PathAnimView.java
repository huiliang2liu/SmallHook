package com.svg;

/**
 * com.svg
 * 2019/1/11 11:03
 * instructions：
 * author:liuhuiliang  email:825378291@qq.com
 **/

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.iface.ISVG;
import com.reflect.FieldManager;
import com.svg.vector.VectorPars;

/**
 * 介绍：一个路径动画的View 利用源Path绘制“底” 利用动画Path 绘制 填充动画
 * <p>
 * 通过mPathAnimHelper 对SourcePath做动画： 一个SourcePath 内含多段Path，循环取出每段Path，并做一个动画,
 * <p>
 *
 */

public class PathAnimView extends ImageView implements AnimEndListen {
    protected Path mSourcePath;// 需要做动画的源Path
    protected Path mAnimPath;// 用于绘制动画的Path
    protected Paint mPaint;
    protected int mColorBg = Color.argb(0x00, 0x00, 0x00, 0x00);// 背景色
    protected int mColorFg = Color.WHITE;// 前景色 填充色
    protected PathAnimHelper mPathAnimHelper;// Path动画工具类
    protected int mPaddingLeft, mPaddingTop;
    protected VectorPars vectorParas;
    protected SVGPars svgParser;
    private ISVG entity;

    public PathAnimView(Context context) {
        this(context, null);
        init(context);
    }

    public PathAnimView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        init(context);
    }

    public PathAnimView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public Path getSourcePath() {
        return mSourcePath;
    }

    public void drawPath(int rawId) {
        if (vectorParas != null) {

        }
    }

    public void drawVectorPath(int rawId) {
        if (vectorParas != null) {
            try {
                entity = vectorParas.paras(getResources(), rawId);
                setSourcePath(entity.createPath(this));
                mColorFg = entity.color();
                start();
            } catch (Exception e) {
                // TODO: handle exception
            }
        }
    }

    public void start(int time) {
        mPathAnimHelper.setAnimTime(time);
        startAnim();
    }

    public void start() {
        start(300);
    }

    public void drawSVGPath(int rawId) throws Exception {
        if (svgParser != null) {
            entity = svgParser.paras(getResources(), rawId);
            setSourcePath(entity.createPath(this));
            mColorFg = entity.color();
            start();
        }
    }

    /**
     * 这个方法可能会经常用到，用于设置源Path
     *
     * @param sourcePath
     * @return
     */
    public PathAnimView setSourcePath(Path sourcePath) {
        mSourcePath = sourcePath;
        mPathAnimHelper.setSourcePath(sourcePath);
        return this;
    }

    public Paint getPaint() {
        return mPaint;
    }

    public PathAnimView setPaint(Paint paint) {
        mPaint = paint;
        return this;
    }

    public int getColorBg() {
        return mColorBg;
    }

    public PathAnimView setColorBg(int colorBg) {
        mColorBg = colorBg;
        return this;
    }

    public int getColorFg() {
        return mColorFg;
    }

    public PathAnimView setColorFg(int colorFg) {
        mColorFg = colorFg;
        return this;
    }

    public PathAnimHelper getPathAnimHelper() {
        return mPathAnimHelper;
    }

    public PathAnimView setPathAnimHelper(PathAnimHelper pathAnimHelper) {
        mPathAnimHelper = pathAnimHelper;
        return this;
    }

    public Path getAnimPath() {
        return mAnimPath;
    }

    /**
     * INIT FUNC
     **/
    protected void init(Context context) {
        // Paint
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(3);
        // 动画路径只要初始化即可
        mAnimPath = new Path();
        // 初始化动画帮助类
        mPathAnimHelper = getInitAnimHeper();
        mPathAnimHelper.setInfinite(false);
        mPathAnimHelper.setListen(this);
        Class cls=context.getClass();
        vectorParas = (VectorPars) FieldManager.getField(context,
                FieldManager.field(cls, "vectorParas"));
        this.svgParser = (SVGPars) FieldManager.getField(context,
                FieldManager.field(cls, "svgParser"));
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mPaddingLeft = getPaddingLeft();
        mPaddingTop = getPaddingTop();
    }

    /**
     * 子类可通过重写这个方法，返回自定义的AnimHelper
     *
     * @return
     */
    protected PathAnimHelper getInitAnimHeper() {
        return new PathAnimHelper(this, mSourcePath, mAnimPath);
    }

    /**
     * draw FUNC
     **/
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mSourcePath == null)
            return;
        // 平移
        canvas.translate(mPaddingLeft, mPaddingTop);

        // 先绘制底，
        mPaint.setColor(mColorBg);
        canvas.drawPath(mSourcePath, mPaint);

        // 再绘制前景，mAnimPath不断变化，不断重绘View的话，就会有动画效果。
        mPaint.setColor(mColorFg);
        canvas.drawPath(mAnimPath, mPaint);
    }

    /**
     * 设置动画 循环
     */
    public PathAnimView setAnimInfinite(boolean infinite) {
        mPathAnimHelper.setInfinite(infinite);
        return this;
    }

    /**
     * 设置动画 总时长
     */
    public PathAnimView setAnimTime(long animTime) {
        mPathAnimHelper.setAnimTime(animTime);
        return this;
    }

    /**
     * 执行循环动画
     */
    public void startAnim() {
        mPathAnimHelper.startAnim();
    }

    /**
     *
     * lhl 2018-1-9 下午12:42:36 说明：设置循环播放
     *            void
     */
    public void setInfinite(boolean infinite) {
        mPathAnimHelper.setInfinite(infinite);
    }

    /**
     * 停止动画
     */
    public void stopAnim() {
        mPathAnimHelper.stopAnim();
    }

    public void setListen(AnimEndListen listen) {
        mPathAnimHelper.setListen(listen);
    }

    /**
     * 清除并停止动画
     */
    public void clearAnim() {
        stopAnim();
        mAnimPath.reset();
        mAnimPath.lineTo(0, 0);
        invalidate();
    }

    @Override
    public void end() {
        // TODO Auto-generated method stub
        if (entity != null)
            setImageDrawable(entity.createDrawable(this));
        else if (entity != null)
            setImageDrawable(entity.createDrawable(this));
    }
}
