package com.nerpage.oca.fragments.presenters;

import com.nerpage.oca.R;
import com.nerpage.oca.fragments.Presenter;

public class ActionCardPresenter extends Presenter {
    //================================================================================
    // region //            POI

    public enum POI implements Presenter.POI {
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

    // endregion //         Interface
    //================================================================================
}
