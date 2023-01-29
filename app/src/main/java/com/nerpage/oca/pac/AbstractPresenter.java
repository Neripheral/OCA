package com.nerpage.oca.pac;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.nerpage.oca.classes.helpers.AnimationHelper;

import java.util.Objects;

public abstract class AbstractPresenter implements Presenter {

    /**
     * Initiates the presenter to manage an already existing layout rooted at
     * the specific {@code View}.
     *
     * @param root View being a root of this presenter's described layout.
     *             Throws {@code NullPointerException} if null.
     */
    public AbstractPresenter(@NonNull View root){
        Objects.requireNonNull(root, "Argument root must not be null");
        this.root = root;
    }

    /**
     * A view being the root of an already existing hierarchy that
     * this {@code Presenter} helps to manage.
     */
    @NonNull
    private final View root;

    @NonNull
    public View getRoot() {
        return root;
    }



    /**
     *  For compatibility reasons only. Heavily deprecated. DO NOT USE.
     */
    public void playEffect(PointOfInterest poi, int resId, int duration, float scale, Runnable after){
        getView(poi).setScaleX(scale);
        getView(poi).setScaleY(scale);
        AnimationHelper.playCustomDurationAnimation((ImageView)getView(poi), resId, duration, after);
    }

    public View getView(PointOfInterest poi) {
        return getRoot().findViewById(poi.getId());
    }

    public TextView updateText(PointOfInterest poi, String text){
        ((TextView)this.getView(poi)).setText(text);
        return ((TextView)this.getView(poi));
    }

    public ImageView updateImage(PointOfInterest poi, int imgId){
        ((ImageView)this.getView(poi)).setImageResource(imgId);
        return ((ImageView)this.getView(poi));
    }

    public View show(PointOfInterest poi){
        this.getView(poi).setVisibility(View.VISIBLE);
        return this.getView(poi);
    }

    public View hide(PointOfInterest poi){
        this.getView(poi).setVisibility(View.GONE);
        return this.getView(poi);
    }



}
