package com.nerpage.oca.classes;

import android.view.View;

public abstract class LayoutHelper {
    /**
     * POI represents each View in Layout that is important.
     * As such each final LayoutHelper should have own enum implementing POI.
     */
    interface POI{
        int getId();
    }

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

    public View getView(int id){
        return this.getRoot().findViewById(id);
    }

    public View getView(POI poi){
        return this.getView(poi.getId());
    }

    public View show(int id){
        this.getView(id).setVisibility(View.VISIBLE);
        return this.getView(id);
    }

    public View show(POI poi){
        return this.show(poi.getId());
    }

    public void hide(int id){
        this.getView(id).setVisibility(View.GONE);
    }

    public void hide(POI poi){
        this.hide(poi.getId());
    }

}
