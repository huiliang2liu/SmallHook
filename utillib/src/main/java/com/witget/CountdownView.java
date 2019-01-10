package com.witget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

public class CountdownView extends View {
	private final static String TAG = "CountdownView";
	/**
	 * 画笔
	 */
	private Paint paint;
	/**
	 * 背景颜色
	 */
	private int color = Color.rgb(0xed, 0x9c, 0x00);
	/**
	 * 字体颜色
	 */
	private int text_color = Color.rgb(0xff, 0xff, 0xff);
	/**
	 * 字体大小
	 */
	private int text_size;
	/**
	 * 线条宽度
	 */
	private int line_width;
	/**
	 * 字的宽度
	 */
	private int text_width;
	/**
	 * 组件高度
	 */
	private int heigth = 0;
	/**
	 * 组件宽度
	 *
	 */
	private int width = 0;
	/**
	 * 进度条
	 */
	private int progress = 0;
	/**
	 * 展示的字
	 */
	private String text = "10分钟";

	public CountdownView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		init();
	}

	public CountdownView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		init();
	}

	public CountdownView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		// TODO Auto-generated constructor stub
		init();
	}

	public int getProgress() {
		return progress;
	}

	public void setProgress(int progress) {
		this.progress = progress;
		invalidate();
	}

	private void init() {
		// TODO Auto-generated method stub
		paint = new Paint();
		paint.setStyle(Style.FILL);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		if (width == 0) {
			width = getWidth();
			heigth = getHeight();
			text_size = heigth * 2 / 3;
			line_width = heigth / 10;
			text_width = text_size * text.length() + text_size;
		}
		int blank_width = width / 100 * progress;
		draw_line(canvas, blank_width);
		draw_text(canvas, blank_width);
	}

	private void draw_text(Canvas canvas, int width) {
		// TODO Auto-generated method stub
		paint.setColor(color);
		RectF rectF = new RectF(this.width - width - text_width, 0, this.width
				- width, heigth);
		canvas.drawRoundRect(rectF, line_width, line_width, paint);
		paint.setColor(text_color);
		paint.setColor(text_color);
		canvas.drawText(text, this.width - width - text_width * 0.5f+text_size, (heigth + text_size) * 0.4f, paint);
	}

	private void draw_line(Canvas canvas, int width) {
		// TODO Auto-generated method stub
		paint.setColor(color);
		RectF rectF = new RectF(0, 0, this.width - text_width - width
				+ line_width, line_width);
		canvas.drawRect(rectF, paint);
	}
}
