package com.nerpage.oca.classes;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public abstract class LayoutHelper {
    /**
     * POI represents each View in Layout that is important.
     * As such each final LayoutHelper should have own enum implementing POI.
     */
    public interface POI{
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

    private View getView(int id){
        return this.getRoot().findViewById(id);
    }

    public View getView(POI poi){
        return this.getView(poi.getId());
    }

    public TextView updateText(POI poi, String text){
        ((TextView)this.getView(poi)).setText(text);
        return ((TextView)this.getView(poi));
    }

    public ImageView updateImage(POI poi, int imgId){
        ((ImageView)this.getView(poi)).setImageResource(imgId);
        return ((ImageView)this.getView(poi));
    }

    public View show(POI poi){
        this.getView(poi).setVisibility(View.VISIBLE);
        return this.getView(poi);
    }

    public View hide(POI poi){
        this.getView(poi).setVisibility(View.GONE);
        return this.getView(poi);
    }

    //================================================================================
    // Constructors
    //================================================================================

    public LayoutHelper(View root){
        this.setRoot(root);
    }
}
