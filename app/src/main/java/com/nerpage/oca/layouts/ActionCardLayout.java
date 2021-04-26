package com.nerpage.oca.layouts;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nerpage.oca.R;
import com.nerpage.oca.models.ActionCardModel;

public class ActionCardLayout extends Layout<ActionCardModel> {
    //================================================================================
    // region //           Inner classes

    public enum POI implements Layout.POI {
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

    // endregion //        Inner classes
    //================================================================================
    //================================================================================
    // region //           Fields

    // endregion //        Fields
    //================================================================================
    //================================================================================
    // region //           Accessors

    // endregion //        Accessors
    //================================================================================
    //================================================================================
    // region //           Methods

    @Override
    public void updateViewData() {
        ((ImageView)this.getView(POI.THUMBNAIL)).setImageResource(getModel().getThumbnailResId());
        ((TextView)this.getView(POI.TITLE)).setText(getModel().getTitle());
        ((TextView)this.getView(POI.DESCRIPTION)).setText(getModel().getDescription());
    }

    // endregion //        Methods
    //================================================================================
    //================================================================================
    // region //           Constructors

    public ActionCardLayout(View itemView) {
        super(itemView);
    }

    // endregion //        Constructors
    //================================================================================
}
