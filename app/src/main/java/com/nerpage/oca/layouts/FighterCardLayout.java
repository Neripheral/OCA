package com.nerpage.oca.layouts;

import android.view.View;

import com.nerpage.oca.R;
import com.nerpage.oca.models.FighterCardModel;

public class FighterCardLayout extends Layout<FighterCardModel>{
    //================================================================================
    // region //            Inner classes

    public enum POI implements Layout.POI {
        CONTAINER(R.id.enemy_root),
        CARD(R.id.enemy_container),
        TITLE(R.id.enemy_title),
        CURRENT_BLOOD(R.id.enemy_currentBlood),
        MAX_BLOOD(R.id.enemy_maxBlood),
        EFFECT(R.id.enemy_effect_attack);

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
