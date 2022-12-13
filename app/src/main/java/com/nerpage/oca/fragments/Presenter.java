package com.nerpage.oca.fragments;

import android.view.View;
import android.widget.ImageView;

import com.nerpage.oca.classes.helpers.AnimationHelper;
import com.nerpage.oca.layouts.HasLayout;

public abstract class Presenter implements HasLayout {

    public interface POI extends HasLayout.POI{}



    private final View root;

    @Override
    public View getRoot() {
        return root;
    }



    public void playEffect(POI poi, int resId, int duration, float scale, Runnable after){
        getView(poi).setScaleX(scale);
        getView(poi).setScaleY(scale);
        AnimationHelper.playCustomDurationAnimation((ImageView)getView(poi), resId, duration, after);
    }



    public Presenter(View newRoot){
        root = newRoot;
    }

}
