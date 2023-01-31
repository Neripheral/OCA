package com.nerpage.oca.util;

public interface AnimatedDrawable {
    int getResourceId();
    float getSpeedMultiplier();
    float getScaleX();
    float getScaleY();
    Callback onAnimationEnd();

    static Builder builder(int drawableResourceId){
        return new DefaultAnimatedDrawable.Builder(drawableResourceId);
    }

    interface Builder{
         Builder resourceId(int resourceId);

         Builder speedMultiplier(float speed);

         Builder scaleX(float scaleX);

         Builder scaleY(float scaleY);

         Builder scale(float scale);

         Builder onAnimationEndCallback(Callback onAnimationEndCallback);

         AnimatedDrawable build();
    }
}
