package com.witget;

import android.content.Context;
import android.support.v4.widget.SlidingPaneLayout;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * View com.xh.witget 2018 2018-6-1 上午9:56:45 instructions： author:liuhuiliang
 * email:825378291@qq.com
 **/

public class FoldSlidingPanelLayout extends SlidingPaneLayout {

	public FoldSlidingPanelLayout(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public FoldSlidingPanelLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public FoldSlidingPanelLayout(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onAttachedToWindow() {
		super.onAttachedToWindow();

		View child = getChildAt(0);
		if (child != null) {

			removeView(child);
			final OrigamiView foldLayout = new OrigamiView(getContext());
			// foldLayout.setAnchor(0);
			foldLayout.addView(child);
			ViewGroup.LayoutParams layPar = child.getLayoutParams();
			addView(foldLayout, 0, layPar);

			setPanelSlideListener(new PanelSlideListener() {

				@Override
				public void onPanelSlide(View arg0, float arg1) {
					foldLayout.setFactor(arg1);
				}

				@Override
				public void onPanelOpened(View arg0) {
					// TODO Auto-generated method stub

				}

				@Override
				public void onPanelClosed(View arg0) {

				}
			});

		}
	}
}
