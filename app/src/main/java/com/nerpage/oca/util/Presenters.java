package com.nerpage.oca.util;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

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

    public static void playEffect(Presenter.PointOfInterest poi, @NonNull View root, AnimatedDrawable animatedDrawable){
        View view = getView(poi, root);
        view.setScaleX(animatedDrawable.getScaleX());
        view.setScaleY(animatedDrawable.getScaleY());
        AnimationHelper.playAnimatedDrawable((ImageView)view, animatedDrawable);
    }
}
