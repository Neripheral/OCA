package com.nerpage.oca.fragments;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nerpage.oca.classes.helpers.AnimationHelper;

public abstract class Presenter {

    public interface POI{
        int getId();
    }



    private final View root;

    public View getRoot() {
        return root;
    }



    public void playEffect(POI poi, int resId, int duration, float scale, Runnable after){
        getView(poi).setScaleX(scale);
        getView(poi).setScaleY(scale);
        AnimationHelper.playCustomDurationAnimation((ImageView)getView(poi), resId, duration, after);
    }

    public View getView(POI poi) {
        return getRoot().findViewById(poi.getId());
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

    public Presenter(View newRoot){
        root = newRoot;
    }

}
