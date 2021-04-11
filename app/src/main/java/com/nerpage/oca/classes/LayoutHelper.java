package com.nerpage.oca.classes;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nerpage.oca.interfaces.HasLayout;

public abstract class LayoutHelper implements HasLayout {
    public interface POI extends HasLayout.POI{}

    //================================================================================
    // Fields
    //================================================================================

    private View root;

    //================================================================================
    // Accessors
    //================================================================================

    public View getRoot() {
        return this.root;
    }

    public LayoutHelper setRoot(View newRoot){
        this.root = newRoot;
        return this;
    }

    //================================================================================
    // Methods
    //================================================================================



    //================================================================================
    // Constructors
    //================================================================================

    public LayoutHelper(View root){
        this.setRoot(root);
    }
}
