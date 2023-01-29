package com.nerpage.oca.pac;

import android.view.View;

import androidx.annotation.NonNull;

import java.util.Objects;
import java.util.Optional;

public interface Presenter {
    /**
     * Returns the root of an existing hierarchy this presenter describes.
     * @return root {@code View}
     */
    @NonNull
    View getRoot();

    /**
     * POI (Point of Interest) is an autocompletion-friendly layer of abstraction
     * between xml ids and the source code. Subclasses of {@code Presenter} have their own
     * static enum subclass of POI that maps enum constant to the view's id (R.id.*).
     * @see com.nerpage.oca.fragments.presenters.ExamplePresenter.POI
     */
    interface PointOfInterest {
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
    interface Callback{
        void call();
    }
}
