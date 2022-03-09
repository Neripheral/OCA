package com.nerpage.oca.layouts;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public interface HasLayout {
    class RootUnsetException extends IllegalStateException{}
    /**
     * POI represents each View in Layout that is important.
     * As such each final LayoutHelper should have own enum implementing POI.
     */
    interface POI{
        int getId();
    }

    View getRoot();

    default View getView(POI poi) throws RootUnsetException{
        if(getRoot() == null)
            throw new RootUnsetException();
        return this.getRoot().findViewById(poi.getId());
    }

    default TextView updateText(POI poi, String text){
        ((TextView)this.getView(poi)).setText(text);
        return ((TextView)this.getView(poi));
    }

    default ImageView updateImage(POI poi, int imgId){
        ((ImageView)this.getView(poi)).setImageResource(imgId);
        return ((ImageView)this.getView(poi));
    }

    default View show(POI poi){
        this.getView(poi).setVisibility(View.VISIBLE);
        return this.getView(poi);
    }

    default View hide(POI poi){
        this.getView(poi).setVisibility(View.GONE);
        return this.getView(poi);
    }
}
