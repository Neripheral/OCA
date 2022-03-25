package com.nerpage.oca.fragments;

import android.view.View;
import android.widget.ImageView;

import com.nerpage.oca.classes.helpers.AnimationHelper;
import com.nerpage.oca.layouts.HasLayout;

public abstract class Presenter implements HasLayout {
    //================================================================================
    // region //            POI

    public interface POI extends HasLayout.POI{}

    // endregion //         POI
    //================================================================================
    //================================================================================
    // region //            Fields

    private View root;

    // endregion //         Fields
    //================================================================================
    //================================================================================
    // region //            Accessors

    @Override
    public View getRoot() {
        return root;
    }

    public void setRoot(View newRoot){
        root = newRoot;
    }

    // endregion //         Accessors
    //================================================================================
    //================================================================================
    // region //            Private methods



    // endregion //         Private methods
    //================================================================================
    //================================================================================
    // region //            Interface

    public boolean isRootNull(){
        return (getRoot() == null);
    }

    public void playEffect(POI poi, int resId, int duration, float scale, Runnable after){
        getView(poi).setScaleX(scale);
        getView(poi).setScaleY(scale);
        AnimationHelper.playCustomDurationAnimation((ImageView)getView(poi), resId, duration, after);
    }

    // endregion //         Interface
    //================================================================================
    //================================================================================
    // region //            Constructors

    public Presenter(View newRoot){
        root = newRoot;
    }

    public Presenter(){}

    // endregion //         Constructors
    //================================================================================
}
