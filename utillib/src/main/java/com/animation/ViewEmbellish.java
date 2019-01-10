package com.animation;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;

/**
 * 2018/5/22 9:41
 * instructions：
 * author:liuhuiliang  email:825378291@qq.com
 **/

public class ViewEmbellish {
    private View view;
    private ViewGroup.LayoutParams params;

    public ViewEmbellish(View view) {
        // TODO Auto-generated constructor stub
        if (view == null)
            throw new RuntimeException("you view is null");
        this.view = view;
        params = view.getLayoutParams();
        if (params == null)
            throw new RuntimeException("this view no params");
    }

    public int getWidth() {
        return view.getWidth();
    }

    public void setWidth(int width) {
        params.width = width;
        view.requestLayout();
    }

    public int getHeight() {
        return view.getHeight();
    }

    public void setHeight(int height) {
        params.height = height;
        view.requestLayout();
    }

    public float getX() {
        return view.getX();
    }

    public void setX(float x) {
        view.setX(x);
    }

    public float getY() {
        return view.getY();
    }

    public void setY(float y) {
        view.setY(y);
    }

    public float getAlpha() {
        return view.getAlpha();
    }

    public void setAlpha(float alpha) {
        view.setAlpha(alpha);
    }

    public float getTranslationY() {
        return view.getTranslationY();
    }

    public void setTranslationY(float translationY) {
        view.setTranslationY(translationY);
    }

    public float getTranslationX() {
        return view.getTranslationX();
    }

    public void setTranslationX(float translationX) {
        view.setTranslationX(translationX);
    }

    public float getScaleX() {
        return view.getScaleX();
    }

    public void setScaleX(float scaleX) {
        view.setScaleX(scaleX);
    }

    public float getScaleY() {
        return view.getScaleY();
    }

    public void setScaleY(float scaleY) {
        view.setScaleY(scaleY);
    }

    public void setBackgroundColor(int color) {
        view.setBackgroundColor(color);
    }

    public int getBackgroundColor() {
        Drawable drawable = view.getBackground();
        if (drawable instanceof ColorDrawable) {
            return ((ColorDrawable) drawable).getColor();
        } else
            return Color.argb(0x00, 0x00, 0x00, 0x00);
    }

    public float getRotationX() {
        return view.getRotationX();
    }

    public void setRotationX(float rotationX) {
        view.setRotationX(rotationX);
    }

    public float getRotationY() {
        return view.getRotationY();
    }

    public void setRotationY(float rotationY) {
        view.setRotationY(rotationY);
    }

    public float getRotation() {
        return view.getRotation();
    }

    public void setRotation(float rotation) {
        view.setRotation(rotation);
    }
}
