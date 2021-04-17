package com.nerpage.oca.classes.helpers;

import android.graphics.drawable.AnimationDrawable;
import android.widget.ImageView;

public class AnimationHelper {
    public static void playAnimation(ImageView view, int resId){
        view.setBackgroundResource(resId);
        AnimationDrawable animation = (AnimationDrawable)view.getBackground();
        animation.stop();
        animation.start();
    }
}
