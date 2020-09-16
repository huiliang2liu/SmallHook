package com.witget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class ColoredProgressBar extends View {
	private int s = Color.rgb(0x1a, 0xfc, 0x05), c = Color
			.rgb(0xec, 0xfa, 0x04), e = Color.rgb(0xf2, 0x21, 0x00);
	private int b = Color.WHITE;
	private int w = 0, h = 0;
	private int progress = 0;
	private final static String TAG = "ColoredProgressBar";
	private LoadedListen loadedListen;
	private Paint paint;

	public ColoredProgressBar(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		init();
	}

	public ColoredProgressBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		init();
	}

	public ColoredProgressBar(Context context, AttributeSet attrs,
			int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		// TODO Auto-generated constructor stub
		init();
	}

	private void init() {
		// TODO Auto-generated method stub
		w = getWidth();
		h = getHeight();
		paint = new Paint();
		paint.setAntiAlias(true);
		paint.setColor(b);
		Log.e(TAG, "init");
	}

	@SuppressLint("DrawAllocation")
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		synchronized (this) {
			w = getWidth();
			h = getHeight();
			paint.setStyle(Paint.Style.FILL);
			float a = (float) progress / 100;
			float r = (float) h / 2;
			canvas.drawCircle(r, r, r, paint);
			canvas.drawRect(r, 0, w - r, h, paint);
			canvas.drawCircle(w - r, r, r, paint);
			float x = a * w;
			Shader lShader = new LinearGradient(0, 0, w, h, new int[] { s, c,
					e, }, new float[] { 0, 0.5f, 1 }, Shader.TileMode.MIRROR);
			paint.setShader(lShader);
			if (x < r) {
				float rd = (float) Math.toDegrees(Math.acos(1 - x / r));
				RectF rectf = new RectF(0, 0, h, h);
				canvas.drawArc(rectf, 180 - rd, rd * 2, false, paint);
			} else if (x <= w - r) {
				RectF rectf = new RectF(0, 0, h, h);
				canvas.drawArc(rectf, 90.0f, 180.0f, true, paint);
				canvas.drawRect(r, 0, a * w, h, paint);
			} else {
				canvas.drawCircle(r, r, r, paint);
				canvas.drawRect(r, 0, w - r, h, paint);
				canvas.drawCircle(w - r, r, r, paint);
				x = r - (w - x);
				float rd = (float) Math.toDegrees(Math.acos(x / r));
				RectF rectf = new RectF(w - h, 0, w, h);
				Shader lShader1 = new LinearGradient(0, 0, w, h, new int[] { b,
						b, b, }, new float[] { 0, 0.5f, 1 },
						Shader.TileMode.MIRROR);
				paint.setShader(lShader1);
				canvas.drawArc(rectf, 360 - rd, rd * 2, false, paint);
				if (x == r) {
					if (loadedListen != null) {
						loadedListen.loaded();
						loadedListen = null;
					}
				}
			}
		}
	}

	public int getProgress() {
		return progress;
	}

	public void setLoadedListen(LoadedListen loadedListen) {
		this.loadedListen = loadedListen;
	}

	synchronized public void setProgress(int progress) {
		if (progress < 0)
			this.progress = 0;
		else if (progress <= 100)
			this.progress = progress;
		else
			this.progress = 100;
		invalidate();
	}

	public static interface  LoadedListen {
		public abstract void loaded();
	}

}
