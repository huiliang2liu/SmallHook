package com.utils;

import android.animation.ObjectAnimator;
import android.view.View;
import android.view.animation.Animation;

import com.animation.ViewEmbellish;

/**
 * com.utils
 * 2018/10/19 18:30
 * instructions：
 * author:liuhuiliang  email:825378291@qq.com
 **/
public class AnimatorUtil {
    public static ObjectAnimator alpha(ViewEmbellish embellish, float startA,
                                       float endA, long time) {
        if (startA < 0 || startA > 1)
            startA = 0;
        if (endA < 0 || endA > 1)
            endA = 1;
        ObjectAnimator alpha = ObjectAnimator.ofFloat(embellish, "alpha",
                startA, endA);
        setAnimationTime(alpha, time);
        return alpha;
    }

    public static ObjectAnimator Y(ViewEmbellish embellish, float startY,
                                   float endY, long time) {
        ObjectAnimator translationY = ObjectAnimator.ofFloat(embellish, "Y",
                startY, endY);
        setAnimationTime(translationY, time);
        return translationY;
    }

    public static ObjectAnimator X(ViewEmbellish embellish, float startY,
                                   float endX, long time) {
        ObjectAnimator translationX = ObjectAnimator.ofFloat(embellish, "X",
                startY, endX);
        setAnimationTime(translationX, time);
        return translationX;
    }

    public static ObjectAnimator translationY(ViewEmbellish embellish,
                                              float startY, float endY, long time) {
        ObjectAnimator translationY = ObjectAnimator.ofFloat(embellish,
                "translationY", startY, endY);
        setAnimationTime(translationY, time);
        return translationY;
    }

    public static ObjectAnimator translationX(ViewEmbellish embellish,
                                              float startY, float endX, long time) {
        ObjectAnimator translationX = ObjectAnimator.ofFloat(embellish,
                "translationX", startY, endX);
        setAnimationTime(translationX, time);
        return translationX;
    }

    public static ObjectAnimator scaleY(ViewEmbellish embellish, float startY,
                                        float endY, long time) {
        if (startY > 1 || startY < 0)
            startY = 0;
        if (endY > 1 || endY < 0)
            endY = 1;
        ObjectAnimator translationY = ObjectAnimator.ofFloat(embellish,
                "scaleY", startY, endY);
        setAnimationTime(translationY, time);
        return translationY;
    }

    public static ObjectAnimator scaleX(ViewEmbellish embellish, float startX,
                                        float endX, long time) {
        if (startX > 1 || startX < 0)
            startX = 0;
        if (endX > 1 || endX < 0)
            endX = 1;
        ObjectAnimator translationX = ObjectAnimator.ofFloat(embellish,
                "scaleX", startX, endX);
        setAnimationTime(translationX, time);
        return translationX;
    }

    public static ObjectAnimator width(ViewEmbellish embellish, int startY,
                                       int endY, long time) {
        if (startY < 0)
            startY = 0;
        if (endY < 0)
            endY = embellish.getWidth();
        ObjectAnimator translationY = ObjectAnimator.ofInt(embellish, "width",
                startY, endY);
        setAnimationTime(translationY, time);
        return translationY;
    }

    public static ObjectAnimator height(ViewEmbellish embellish, int startX,
                                        int endX, long time) {
        if (startX < 0)
            startX = 0;
        if (endX < 0)
            endX = embellish.getHeight();
        ObjectAnimator translationX = ObjectAnimator.ofInt(embellish, "height",
                startX, endX);
        setAnimationTime(translationX, time);
        return translationX;
    }

    public static ObjectAnimator backgroundColor(ViewEmbellish embellish,
                                                 int startX, int endX, long time) {
        ObjectAnimator translationX = ObjectAnimator.ofInt(embellish,
                "backgroundColor", startX, endX);
        setAnimationTime(translationX, time);
        return translationX;
    }

    public static void setAnimationTime(ObjectAnimator objectAnimator, long time) {
        if (time <= 0)
            time = 300;
        objectAnimator.setDuration(time);
    }

    public static ObjectAnimator rotation(ViewEmbellish embellish,
                                          float startY, float endX, long time) {
        ObjectAnimator translationX = ObjectAnimator.ofFloat(embellish,
                "rotation", startY, endX);
        setAnimationTime(translationX, time);
        return translationX;
    }

    public static ObjectAnimator rotatioY(ViewEmbellish embellish,
                                          float startY, float endY, long time) {
        ObjectAnimator translationY = ObjectAnimator.ofFloat(embellish,
                "rotationY", startY, endY);
        setAnimationTime(translationY, time);
        return translationY;
    }

    public static ObjectAnimator rotatioX(ViewEmbellish embellish,
                                          float startX, float endX, long time) {
        ObjectAnimator translationX = ObjectAnimator.ofFloat(embellish,
                "rotationX", startX, endX);
        setAnimationTime(translationX, time);
        return translationX;
    }

    public static void startAnimation(View view, Animation animator) {
        view.startAnimation(animator);
    }
}
