package com.witget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

/**
 * HookFrame com.xh.util 2018 2018-5-29 上午10:48:55 instructions：
 * author:liuhuiliang email:825378291@qq.com
 **/

public class SuctionViewGroup extends FrameLayout {
	private int width = -1;
	private int heigth = -1;
	private Matrix[] mMatrixs;
	private float dw;
	private float dw2;
	private float dh;
	private float halfw;
	private boolean hide = false;
	private int num = 500 / 10;
	private float[] src;

	public SuctionViewGroup(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		init(context);
	}

	public SuctionViewGroup(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		init(context);
	}

	public SuctionViewGroup(Context context, AttributeSet attrs,
			int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		// TODO Auto-generated constructor stub
		init(context);
	}

	@SuppressLint("NewApi")
	public SuctionViewGroup(Context context, AttributeSet attrs,
			int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
		// TODO Auto-generated constructor stub
		init(context);
	}

	private void init(Context context) {
		// TODO Auto-generated method stub
		mMatrixs = new Matrix[num];

	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

	@Override
	protected void onLayout(boolean changed, int left, int top, int right,
			int bottom) {
		// TODO Auto-generated method stub
		super.onLayout(changed, left, top, right, bottom);
		if (width <= 0) {
			width = getMeasuredWidth();
			heigth = getMeasuredHeight();
			dw = width * .5f / (num) / 2;
			dw2 = dw * 2;
			dh = heigth * .5f / (num);
			halfw = width * .5f;
			src = new float[] { 0, 0, width, 0, 0, heigth, width, heigth };
			float[] dst = new float[8];
			for (int i = 0; i < num; i++) {
				Matrix matrix = new Matrix();
				dst[0] = i * dw;
				dst[1] = dh * i;
				dst[2] = width - dst[0];
				dst[3] = dst[1];
				dst[4] = i * dw2;
				if (dst[4] >= halfw)
					dst[4] = halfw - .5f;
				dst[5] = heigth;
				dst[6] = width - dst[4];
				dst[7] = heigth;
				matrix.setPolyToPoly(src, 0, dst, 0, 4);
				mMatrixs[i] = matrix;
			}
		}

	}

	public void setTime(int time) {
		num=time/10;
		mMatrixs=new Matrix[num];
		float[] dst = new float[8];
		for (int i = 0; i < num; i++) {
			Matrix matrix = new Matrix();
			dst[0] = i * dw;
			dst[1] = dh * i;
			dst[2] = width - dst[0];
			dst[3] = dst[1];
			dst[4] = i * dw2;
			if (dst[4] >= halfw)
				dst[4] = halfw - .5f;
			dst[5] = heigth;
			dst[6] = width - dst[4];
			dst[7] = heigth;
			matrix.setPolyToPoly(src, 0, dst, 0, 4);
			mMatrixs[i] = matrix;
		}
	}

	private int index = 0;

	@Override
	protected synchronized void dispatchDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		if (index >= num || index <= -1) {
			if (!hide)
				super.dispatchDraw(canvas);
			return;
		}
		Matrix mMatrix = mMatrixs[index];
		canvas.concat(mMatrix);
		canvas.save();
		super.dispatchDraw(canvas);
		// canvas.restore();
	}

	private boolean animator = false;

	public synchronized void hide() {
		if (animator)
			return;
		hide = true;
		index = 0;
		new Thread() {
			public void run() {
				animator = true;
				try {
					while (index < num) {
						postInvalidate();
						index++;
						sleep(10);
					}
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				animator = false;
				try {
					sleep(1000);
					show();
				} catch (Exception e) {
					// TODO: handle exception
				}
			};
		}.start();
	}

	public synchronized void show() {
		if (animator)
			return;
		hide = false;
		index = num - 1;
		new Thread() {
			public void run() {
				animator = true;
				try {
					while (index > -1) {
						postInvalidate();
						index--;
						sleep(10);
					}
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				animator = false;
			};
		}.start();
	}
}
