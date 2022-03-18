package com.nerpage.oca.fragments.controllers;

import android.content.Context;

import com.nerpage.oca.classes.fighting.Fighter;
import com.nerpage.oca.fragments.Controller;
import com.nerpage.oca.fragments.models.FighterCardModel;
import com.nerpage.oca.fragments.presenters.FighterCardPresenter;

public class FighterCardController extends Controller<FighterCardModel, FighterCardPresenter> {
    //================================================================================
    // region //            Interface

    public void updateModel(Fighter fighter){
        m.maxBlood = String.valueOf(fighter.getEntity().getMaxBlood());
        m.currentBlood = String.valueOf(fighter.getEntity().getBlood());
        m.title = fighter.getEntity().getName(getContext());
    }

    public void updatePresentation() {
        p.updateTitle(m.title);
        p.updateCurrentBlood(m.currentBlood);
        p.updateMaxBlood(m.maxBlood);
    }

    public void playEffect(int resId, int duration, float scale, Runnable after){
        p.playEffectOnAvatar(resId, duration, scale, after);
    }

    // endregion //         Interface
    //================================================================================
    //================================================================================
    // region //            Constructors

    public FighterCardController(Context context){
        super(new FighterCardModel(), new FighterCardPresenter(), context);
    }

    // endregion //         Constructors
    //================================================================================
}
