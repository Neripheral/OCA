package com.nerpage.oca.fragments.presenters;

import android.view.View;

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
