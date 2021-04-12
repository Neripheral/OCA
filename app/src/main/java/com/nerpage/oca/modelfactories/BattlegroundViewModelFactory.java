package com.nerpage.oca.modelfactories;

import android.content.Context;

import com.nerpage.oca.classes.Entity;
import com.nerpage.oca.classes.fighting.Action;
import com.nerpage.oca.models.ActionCardModel;
import com.nerpage.oca.models.BattlegroundViewModel;

import java.util.ArrayList;
import java.util.List;

public final class BattlegroundViewModelFactory{
    private static void bindPcData(BattlegroundViewModel newModel, Context context, Entity pc){
        newModel.setPcCurrentBlood(pc.getBlood());
        newModel.setPcMaxBlood(pc.getMaxBlood());
    }

    private static void bindEnemyData(BattlegroundViewModel newModel, Context context, Entity enemy) {
        newModel.setEnemyTitle(enemy.getName(context));
        newModel.setEnemyCurrentBlood(enemy.getBlood());
        newModel.setEnemyMaxBlood(enemy.getMaxBlood());
    }

    private static List<ActionCardModel> generateActionCardModels(Context context, List<Action> possibleActions) {
        List<ActionCardModel> cardModels = new ArrayList<>();
        for(Action action : possibleActions){
            cardModels.add(ActionCardModelFactory.generateFreshModel(context, action));
        }
        return cardModels;
    }

    private static void bindPossibleActions(BattlegroundViewModel newModel, Context context, Entity pc) {
        newModel.setPossibleActions(
                generateActionCardModels(context, pc.getPossibleActions())
        );
    }

    public static BattlegroundViewModel generateFreshModel(Context context, Entity pc, Entity enemy){
        BattlegroundViewModel newModel = new BattlegroundViewModel();
        bindPcData(newModel, context, pc);
        bindEnemyData(newModel, context, enemy);
        bindPossibleActions(newModel, context, pc);

        return newModel;
    }

    private BattlegroundViewModelFactory() { }
}
