package com.animation;

import android.animation.ObjectAnimator;
import android.view.View;
import android.view.animation.Animation;

/**
 * 2018/5/22 9:42
 * instructions：
 * author:liuhuiliang  email:825378291@qq.com
 **/

public class AnimatorFactory {
    // public static <T> AnimatorFactory alpha(Object object,
    // Interpolator interpolator, TypeEvaluator<T> evaluator) {
    // // 第一个参数为 view对象，第二个参数为 动画改变的类型，第三，第四个参数依次是开始透明度和结束透明度。
    // AnimatorFactory alpha = AnimatorFactory.ofFloat(object, "alpha", 0f, 1f);
    // alpha.setDuration(2000);// 设置动画时间
    // if (interpolator != null)
    // alpha.setInterpolator(interpolator);// 设置动画插入器，减速
    // alpha.setRepeatCount(-1);// 设置动画重复次数，这里-1代表无限
    // alpha.setRepeatMode(Animation.REVERSE);// 设置动画循环模式。
    // /*
    // * ArgbEvaluator：这种评估者可以用来执行类型之间的插值整数值代表ARGB颜色。
    // * FloatEvaluator：这种评估者可以用来执行浮点值之间的插值。
    // * IntEvaluator：这种评估者可以用来执行类型int值之间的插值。
    // * RectEvaluator：这种评估者可以用来执行类型之间的插值矩形值。
    // *
    // * 由于本例是改变View的backgroundColor属性的背景颜色所以此处使用ArgbEvaluator
    // */
    // if (evaluator != null)
    // alpha.setEvaluator(evaluator);
    // return alpha;
    // // alpha.start();// 启动动画。
    // }
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
