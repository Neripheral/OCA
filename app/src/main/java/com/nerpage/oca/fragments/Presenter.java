package com.nerpage.oca.fragments;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.nerpage.oca.classes.helpers.AnimationHelper;

import java.util.Objects;
import java.util.Optional;

public abstract class Presenter {

    /**
     * Initiates the presenter to manage an already existing layout rooted at
     * the specific {@code View}.
     *
     * @param root View being a root of this presenter's described layout.
     *             Throws {@code NullPointerException} if null.
     */
    public Presenter(@NonNull View root){
        Objects.requireNonNull(root, "Argument root must not be null");
        this.root = root;
    }


    /**
     * POI (Point of Interest) is an autocompletion-friendly layer of abstraction
     * between xml ids and the source code. Subclasses of {@code Presenter} have their own
     * static enum subclass of POI that maps enum constant to the view's id (R.id.*).
     * @see com.nerpage.oca.fragments.presenters.ExamplePresenter.POI
     */
    public interface POI{
        /**
         * Getter of the {@code View} identifier represented by {@code POI} constant
         * @return resource id (R.id.*)
         */
        int getId();

        /**
         * Utility method that fetches {@code View} that this POI represents based on {@code View}
         * hierarchy with given root.
         * @param root root of the hierarchy client needs to find {@code View} for.
         *             Throws {@code NullPointerException} if null.
         * @return {@code Optional} result. Present if there is a {@code View} for given root,
         *          empty if there isn't.
         */
        default Optional<View> of(@NonNull View root){
            Objects.requireNonNull(root, "Argument root must not be null");
            return Optional.ofNullable(root.findViewById(getId()));
        }
    }

    /**
     * A functional interface in form of (void) -> (void) with a sole purpose of being
     * injected into {@code Presenters} if it needs to inform the client back about something.
     */
    public interface Callback{
        void call();
    }


    /**
     * A view being the root of an already existing hierarchy that
     * this {@code Presenter} helps to manage.
     */
    @NonNull
    private final View root;

    /**
     * Returns the root of an existing hierarchy this presenter describes.
     * @return root {@code View}
     */
    @NonNull
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



}
