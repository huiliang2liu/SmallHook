package com.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * com.view
 * 2018/9/28 15:02
 * instructions：
 * author:liuhuiliang  email:825378291@qq.com
 **/
public class Decoration extends RecyclerView.ItemDecoration{
    private Context mContext;
    private Drawable mDivider;
    private int mWidth;
    private Paint paint;
    private Orientation mOrientation;

    public static enum Orientation {
        HORIZONTAL_LIST, VERTICAL_LIST,GRID;
    }

    //我们通过获取系统属性中的listDivider来添加，在系统中的AppTheme中设置
    public static final int[] ATRRS = new int[]{
            android.R.attr.listDivider
    };

    public Decoration(Context context, Orientation orientation) {
        this.mContext = context;
        TypedArray ta = context.obtainStyledAttributes(ATRRS);
        this.mDivider = ta.getDrawable(0);
        ta.recycle();
        mOrientation = orientation;
    }

    public Decoration(Context context,Orientation orientation,Drawable drawable){
        mDivider=drawable;
        mContext=context;
        mOrientation=orientation;
    }

    public Decoration(Context context, Orientation orientation, int color, int width) {
        this(context, orientation);
        if (width <= 0) {
            if (orientation == Orientation.HORIZONTAL_LIST)
                width = mDivider.getIntrinsicWidth();
            else
                width = mDivider.getIntrinsicHeight();
        }
        mWidth = width;
        paint = new Paint();
        paint.setStrokeWidth(width);
        paint.setColor(color);
        paint.setAntiAlias(true);
    }

    public Decoration(Context context, Orientation orientation, int color) {
        this(context, orientation,color,-1);
    }


    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        if (mOrientation == Orientation.HORIZONTAL_LIST) {
            drawVerticalLine(c, parent, state);
        } else if(mOrientation==Orientation.VERTICAL_LIST){
            drawHorizontalLine(c, parent, state);
        }else{
            drawGrid(c, parent, state);
        }
    }
    private void drawGrid(Canvas c, RecyclerView parent, RecyclerView.State state){
        int childCount=parent.getChildCount();
        int parent_left = parent.getPaddingLeft();
        int parent_right = parent.getWidth() - parent.getPaddingRight();
        int parent_top = parent.getPaddingTop();
        int parent_bottom = parent.getHeight() - parent.getPaddingBottom();
        for (int i=0;i<childCount;i++){
            View child = parent.getChildAt(i);
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            int left=child.getRight() + params.rightMargin;
            int right = left + (paint == null ? mDivider.getIntrinsicWidth() : mWidth);
            int top = child.getBottom() + params.bottomMargin;
            int bottom = top + (paint == null ? mDivider.getIntrinsicHeight() : mWidth);
            drawLine(c, parent_left, top, parent_right, bottom);
            drawLine(c, left, parent_top, right, parent_bottom);
        }
    }
    //画横线, 这里的parent其实是显示在屏幕显示的这部分
    public void drawHorizontalLine(Canvas c, RecyclerView parent, RecyclerView.State state) {
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);

            //获得child的布局信息
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            int top = child.getBottom() + params.bottomMargin;
            int bottom = top + (paint == null ? mDivider.getIntrinsicHeight() : mWidth);
            drawLine(c, left, top, right, bottom);
        }
    }

    //画竖线
    public void drawVerticalLine(Canvas c, RecyclerView parent, RecyclerView.State state) {
        int top = parent.getPaddingTop();
        int bottom = parent.getHeight() - parent.getPaddingBottom();
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);

            //获得child的布局信息
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            int left = child.getRight() + params.rightMargin;
            int right = left + (paint == null ? mDivider.getIntrinsicWidth() : mWidth);
            drawLine(c, left, top, right, bottom);
        }
    }

    private void drawLine(Canvas canvas, int left, int top, int right, int bottom) {
        if (paint == null) {
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(canvas);
        } else {
            canvas.drawLine(left, top, right, bottom, paint);
        }
    }

    //由于Divider也有长宽高，每一个Item需要向下或者向右偏移
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if (mOrientation == Orientation.HORIZONTAL_LIST) {
            //画横线，就是往下偏移一个分割线的高度
            outRect.set(0, 0, 0, paint==null?mDivider.getIntrinsicHeight():mWidth);
        } else if(mOrientation == Orientation.VERTICAL_LIST){
            //画竖线，就是往右偏移一个分割线的宽度
            outRect.set(0, 0, paint==null?mDivider.getIntrinsicWidth():mWidth, 0);
        }else{
            outRect.set(0, 0, paint==null?mDivider.getIntrinsicWidth():mWidth, paint==null?mDivider.getIntrinsicHeight():mWidth);
        }
    }
}
