package com.witget;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

/**
 * View com.xh.witget 2018 2018-5-30 下午5:12:08 instructions： author:liuhuiliang
 * email:825378291@qq.com
 **/

public class RecycleRotationView extends RecycleViewGroup {
	List<View> views;
	private int index = 0;
	private int count = -1;
	private View mView;
	{
		views = new ArrayList<View>();
	}

	public RecycleRotationView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public RecycleRotationView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public RecycleRotationView(Context context, AttributeSet attrs,
			int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		// TODO Auto-generated constructor stub
	}

	public RecycleRotationView(Context context, AttributeSet attrs,
			int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		if (count == -1) {
			count = getChildCount();
			if (count > 0) {
				for (int i = 0; i < count; i++) {
					views.add(getChildAt(i));
				}
				mView = getChildAt(0);
				if (count > 1)
					for (int i = 1; i < count; i++) {
						removeViewAt(i);
					}
			}
		}
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

	@Override
	protected void hideEnd() {
		// TODO Auto-generated method stub
		super.hideEnd();
		removeView(mView);
		index++;
		if (index >= count)
			index = 0;
		mView = views.get(index);
		addView(mView);
	}
}
