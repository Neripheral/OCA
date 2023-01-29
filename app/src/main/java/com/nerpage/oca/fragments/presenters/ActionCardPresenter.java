package com.nerpage.oca.fragments.presenters;

import android.view.View;

import com.nerpage.oca.R;
import com.nerpage.oca.pac.AbstractPresenter;

public class ActionCardPresenter extends AbstractPresenter {
    //================================================================================
    // region //            POI

    public enum POI implements PointOfInterest {
        CONTAINER(R.id.action_container),
        THUMBNAIL(R.id.action_thumbnail),
        TITLE(R.id.action_title),
        INFO_BOX(R.id.action_info),
        DESCRIPTION(R.id.action_description);

        int id;

        @Override
        public int getId() {
            return this.id;
        }

        POI(int id) {
            this.id = id;
        }
    }

    // endregion //         POI
    //================================================================================
    //================================================================================
    // region //            Interface

    public void updateThumbnail(int imgResId){
        updateImage(POI.THUMBNAIL, imgResId);
    }

    public void updateTitle(String title){
        updateText(POI.TITLE, title);
    }

    public void updateDescription(String description){
        updateText(POI.DESCRIPTION, description);
    }

    public void setOverallListener(View.OnClickListener listener){
        getView(POI.CONTAINER).setOnClickListener(listener);
    }

    public void showDetails(){
        show(POI.INFO_BOX);
    }

    public void hideDetails(){
        hide(POI.INFO_BOX);
    }

    // endregion //         Interface
    //================================================================================
    //================================================================================
    // region //

    public ActionCardPresenter(View root){
        super(root);
    }

    // endregion //
    //================================================================================
}
