package com.nerpage.oca.modelfactories;

import android.content.Context;

import com.nerpage.oca.classes.fighting.Fighter;
import com.nerpage.oca.models.FighterCardModel;

public final class FighterCardModelFactory {
    public static FighterCardModel generateFreshModel(Context context, Fighter fighter){
        FighterCardModel model = new FighterCardModel();
        model.setTitle(fighter.getEntity().getName(context));
        model.setCurrentBlood(String.valueOf(fighter.getEntity().getBlood()));
        model.setMaxBlood(String.valueOf(fighter.getEntity().getMaxBlood()));
        return model;
    }

    private FighterCardModelFactory(){}
}
