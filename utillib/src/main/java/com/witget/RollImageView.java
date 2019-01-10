package com.witget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

/**
 * View com.xh.witget 2018 2018-6-1 下午3:25:32 instructions： author:liuhuiliang
 * email:825378291@qq.com
 **/

public class RollImageView extends ImageView {

	private int mDx;
	private int mMinDx;

	public RollImageView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public RollImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public RollImageView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		// TODO Auto-generated constructor stub
	}

	public void setDx(int dx) {
		if (getDrawable() == null) {
			return;
		}
		mDx = dx - mMinDx;
		if (mDx <= 0) {
			mDx = 0;
		}
		if (mDx > getDrawable().getBounds().height() - mMinDx) {
			mDx = getDrawable().getBounds().height() - mMinDx;
		}
		invalidate();
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		mMinDx = h;
	}

	public int getDx() {
		return mDx;
	}

	@Override
	protected void onDraw(Canvas canvas) {

		Drawable drawable = getDrawable();
		int w = getWidth();
		int h = (int) (getWidth() * 1.0f / drawable.getIntrinsicWidth() * drawable
				.getIntrinsicHeight());
		drawable.setBounds(0, 0, w, h);
		canvas.save();
		canvas.translate(0, -getDx());
		super.onDraw(canvas);
		canvas.restore();
	}
}
