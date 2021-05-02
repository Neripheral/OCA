package com.nerpage.oca.layouts;

import android.view.View;
import android.widget.ImageView;

import com.nerpage.oca.R;
import com.nerpage.oca.classes.helpers.AnimationHelper;
import com.nerpage.oca.layouts.models.FighterCardModel;

public class FighterCardLayout extends Layout<FighterCardModel>{
    //================================================================================
    // region //            Inner classes

    public enum POI implements Layout.POI {
        CONTAINER(R.id.fighter_root),
        CARD(R.id.fighter_container),
        TITLE(R.id.fighter_title),
        CURRENT_BLOOD(R.id.fighter_currentBlood),
        MAX_BLOOD(R.id.fighter_maxBlood),
        EFFECT(R.id.fighter_effect_attack);

        int id;

        @Override
        public int getId() {
            return id;
        }

        POI(int id){
            this.id = id;
        }
    }

    // endregion //         Inner classes
    //================================================================================
    //================================================================================
    // region //            Fields



    // endregion //         Fields
    //================================================================================
    //================================================================================
    // region //            Accessors



    // endregion //         Accessors
    //================================================================================
    //================================================================================
    // region //            Private methods



    // endregion //         Private methods
    //================================================================================
    //================================================================================
    // region //            Interface

    @Override
    public void updateViewData() {
        updateText(POI.TITLE, getModel().getTitle());
        updateText(POI.CURRENT_BLOOD, getModel().getCurrentBlood());
        updateText(POI.MAX_BLOOD, getModel().getMaxBlood());
    }

    public void playEffect(int resId, int duration, float scale, Runnable after){
        getView(POI.EFFECT).setScaleX(scale);
        getView(POI.EFFECT).setScaleY(scale);
        AnimationHelper.playCustomDurationAnimation((ImageView)getView(POI.EFFECT), resId, duration, after);
    }

    // endregion //         Interface
    //================================================================================
    //================================================================================
    // region //            Constructors

    public FighterCardLayout(View root) {
        super(root);
    }

    // endregion //         Constructors
    //================================================================================
}
