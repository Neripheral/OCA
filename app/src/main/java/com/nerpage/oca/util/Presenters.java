package com.nerpage.oca.util;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.nerpage.oca.R;
import com.nerpage.oca.classes.helpers.AnimationHelper;
import com.nerpage.oca.pac.Presenter;


import java.util.Objects;

public enum Presenters {
    ;

    public static View getView(@NonNull Presenter.PointOfInterest poi, @NonNull View root){
        Objects.requireNonNull(poi, "poi can't be null.");
        Objects.requireNonNull(root, "root can't be null.");

        return poi.of(root).orElseThrow(
                ()->new IllegalArgumentException("There is no view for this POI and root.")
        );
    }



    private static void setText(@NonNull View view, @NonNull String text){
        if(!(view instanceof TextView))
            throw new IllegalArgumentException("View isn't a TextView.");
        ((TextView)view).setText(text);
    }

    public static void setText(@NonNull Presenter.PointOfInterest poi, @NonNull View root, @NonNull String text){
        Objects.requireNonNull(poi, "poi can't be null.");
        Objects.requireNonNull(root, "root can't be null.");
        Objects.requireNonNull(text, "text can't be null.");

        View v = getView(poi, root);
        setText(v, text);
    }



    private static String getText(@NonNull View view){
        if(view instanceof TextView)
            return ((TextView)view).getText().toString();
        throw new IllegalArgumentException("Can't find text for the specified view.");
    }

    public static String getText(@NonNull Presenter.PointOfInterest poi, @NonNull View root){
        Objects.requireNonNull(poi, "poi can't be null.");
        Objects.requireNonNull(root, "root can't be null.");

        View v = getView(poi, root);
        return getText(v);
    }



    private static void setImage(@NonNull View view, int imageResourceId){
        Objects.requireNonNull(view, "View can't be null.");
        if(!(view instanceof ImageView))
            throw new IllegalArgumentException("View isn't an ImageView.");
        ((ImageView)view).setImageResource(imageResourceId);
        view.setTag(R.id.testing_image_resId, imageResourceId);
    }

    public static void setImage(Presenter.PointOfInterest poi, View root, int imageResourceId){
        Objects.requireNonNull(poi, "poi can't be null.");
        Objects.requireNonNull(root, "root can't be null.");

        View v = getView(poi, root);
        setImage(v, imageResourceId);
    }



    private static void show(@NonNull View view){
        Objects.requireNonNull(view, "View can't be null.");
        view.setVisibility(View.VISIBLE);
    }

    public static void show(@NonNull Presenter.PointOfInterest poi, @NonNull View root){
        Objects.requireNonNull(poi, "POI can't be null.");
        Objects.requireNonNull(root, "Root can't be null.");

        show(getView(poi, root));
    }



    private static void hide(@NonNull View view){
        Objects.requireNonNull(view, "View can't be null.");
        view.setVisibility(View.GONE);
    }

    public static void hide(@NonNull Presenter.PointOfInterest poi, @NonNull View root){
        Objects.requireNonNull(poi, "POI can't be null.");
        Objects.requireNonNull(root, "Root can't be null.");

        hide(getView(poi, root));
    }

    public static void playEffect(Presenter.PointOfInterest poi, @NonNull View root, AnimatedDrawable animatedDrawable){
        View view = getView(poi, root);
        view.setScaleX(animatedDrawable.getScaleX());
        view.setScaleY(animatedDrawable.getScaleY());
        AnimationHelper.playAnimatedDrawable((ImageView)view, animatedDrawable);
    }
}
