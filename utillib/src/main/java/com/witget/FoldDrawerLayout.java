package com.witget;

import android.content.Context;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;



public class FoldDrawerLayout extends DrawerLayout {

	public FoldDrawerLayout(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public FoldDrawerLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public FoldDrawerLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onAttachedToWindow() {
		super.onAttachedToWindow();

		final int childCount = getChildCount();
		for (int i = 0; i < childCount; i++) {

			final View child = getChildAt(i);
			if (child instanceof OrigamiView) {
				OrigamiView foldLayout = ((OrigamiView) child);
				foldLayout.setRight(true);
			}

		}
		setDrawerListener(new DrawerListener() {

			@Override
			public void onDrawerStateChanged(int arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onDrawerSlide(View drawerView, float slideOffset) {

				if (drawerView instanceof OrigamiView) {
					OrigamiView foldLayout = ((OrigamiView) drawerView);
					foldLayout.setFactor(slideOffset);
				}

			}

			@Override
			public void onDrawerOpened(View arg0) {

			}

			@Override
			public void onDrawerClosed(View arg0) {

			}
		});

	}

	boolean isDrawerView2(View child) {
		final int gravity = ((LayoutParams) child.getLayoutParams()).gravity;
		final int absGravity = GravityCompat.getAbsoluteGravity(gravity,
				ViewCompat.getLayoutDirection(child));
		return (absGravity & (Gravity.LEFT | Gravity.RIGHT)) != 0;
	}
}
