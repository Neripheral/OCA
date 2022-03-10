package com.nerpage.oca.layouts;

import android.view.View;
import com.nerpage.oca.fragments.presenters.FighterCardPresenter;
import com.nerpage.oca.layouts.models.FighterCardModel;

public class FighterCardLayout extends Layout<FighterCardModel>{
    //================================================================================
    // region //            Fields

    FighterCardPresenter p;

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
        p.updateTitle(getModel().getTitle());
        p.updateCurrentBlood(getModel().getCurrentBlood());
        p.updateMaxBlood(getModel().getMaxBlood());
    }

    public void playEffect(int resId, int duration, float scale, Runnable after){
        p.playEffectOnAvatar(resId, duration, scale, after);
    }

    // endregion //         Interface
    //================================================================================
    //================================================================================
    // region //            Constructors

    public FighterCardLayout(View root) {
        super(root);
        p = new FighterCardPresenter();
        p.setRoot(root);
    }

    // endregion //         Constructors
    //================================================================================
}
