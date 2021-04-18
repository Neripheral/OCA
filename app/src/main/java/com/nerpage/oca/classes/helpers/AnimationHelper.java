package com.nerpage.oca.classes.helpers;

import android.graphics.drawable.AnimationDrawable;
import android.os.SystemClock;
import android.widget.ImageView;

public class AnimationHelper {
    public static class AnimationDrawableEx extends AnimationDrawable{
        private volatile int duration;
        private int currentFrame;

        public AnimationDrawableEx(final AnimationDrawable animation, final int duration){
            currentFrame = 0;
            this.duration = duration;
            for(int i = 0; i< animation.getNumberOfFrames(); i++){
                addFrame(animation.getFrame(i), this.duration);
            }
            setOneShot(true);
        }

        @Override
        public void run() {
            int framesNo = getNumberOfFrames();
            currentFrame++;
            if(currentFrame >= framesNo){
                currentFrame = 0;
                if(isOneShot()) {
                    onAnimationEnd();
                    return;
                }
            }
            selectDrawable(currentFrame);
            scheduleSelf(this, SystemClock.uptimeMillis() + duration);
        }

        public void setDuration(final int duration){
            this.duration = duration;
            unscheduleSelf(this);
            selectDrawable(currentFrame);
            scheduleSelf(this, SystemClock.uptimeMillis() + duration);
        }

        public void onAnimationEnd(){
            return;
        }
    }

    public static void playCustomDurationAnimation(final ImageView view, final int resId, final int duration, final Runnable onAnimationEnd){
        view.setBackgroundResource(resId);
        AnimationDrawableEx animation = new AnimationDrawableEx((AnimationDrawable)view.getBackground(), duration){
            @Override
            public void onAnimationEnd() {
                onAnimationEnd.run();
            }
        };
        view.setBackground(animation);
        animation.stop();
        animation.start();
    }

    public static void playCustomDurationAnimation(final ImageView view, final int resId, final int duration){
        playCustomDurationAnimation(view, resId, duration, () -> {});
    }

    public static void playAnimation(final ImageView view, final int resId){
        view.setBackgroundResource(resId);
        AnimationDrawable animation = (AnimationDrawable)view.getBackground();
        animation.stop();
        animation.start();
    }
}
