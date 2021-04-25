package com.nerpage.oca.classes;

import android.view.View;

import androidx.lifecycle.ViewModel;

import com.nerpage.oca.interfaces.HasLayout;

public abstract class Layout<M extends ViewModel> implements HasLayout {
    //================================================================================
    // region //            Inner classes

    public interface POI extends HasLayout.POI{}

    // endregion //         Inner classes
    //================================================================================
    //================================================================================
    // region //            Fields

    private View root;
    private M model;

    // endregion //         Fields
    //================================================================================
    //================================================================================
    // region //            Accessors

    public View getRoot() {
        return this.root;
    }

    public Layout<M> setRoot(View newRoot){
        this.root = newRoot;
        return this;
    }

    public M getModel() {
        return model;
    }

    public Layout<M> setModel(M model) {
        this.model = model;
        return this;
    }

    // endregion //         Accessors
    //================================================================================
    //================================================================================
    // region //            Private methods



    // endregion //         Private methods
    //================================================================================
    //================================================================================
    // region //            Interface

    public abstract void updateViewData();

    // endregion //         Interface
    //================================================================================
    //================================================================================
    // region //            Constructors

    public Layout(View root){
        this.setRoot(root);
    }

    // endregion //         Constructors
    //================================================================================
}
