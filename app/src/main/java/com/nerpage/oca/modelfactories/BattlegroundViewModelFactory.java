package com.nerpage.oca.modelfactories;

import android.content.Context;

import com.nerpage.oca.classes.Entity;
import com.nerpage.oca.models.BattlegroundViewModel;

public final class BattlegroundViewModelFactory {
    public static BattlegroundViewModel generateFreshModel(Context context, Entity pc, Entity enemy){
        BattlegroundViewModel newModel = new BattlegroundViewModel();
        newModel.setPcCurrentBlood(pc.getBlood());
        newModel.setPcMaxBlood(pc.getMaxBlood());

        newModel.setEnemyTitle(enemy.getName(context));
        newModel.setEnemyCurrentBlood(enemy.getBlood());
        newModel.setEnemyMaxBlood(enemy.getMaxBlood());

        return newModel;
    }

    private BattlegroundViewModelFactory() { }
}
