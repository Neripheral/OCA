package com.nerpage.oca.modelfactories;

import android.content.Context;

import com.nerpage.oca.classes.Entity;
import com.nerpage.oca.classes.fighting.Fighter;
import com.nerpage.oca.classes.fighting.actions.Action;
import com.nerpage.oca.models.ActionCardModel;
import com.nerpage.oca.models.BattlegroundViewModel;

import java.util.ArrayList;
import java.util.List;

public final class BattlegroundViewModelFactory{
    private static void bindPcData(BattlegroundViewModel newModel, Context context, Fighter pc){
        newModel.setPcCurrentBlood(pc.getEntity().getBlood());
        newModel.setPcMaxBlood(pc.getEntity().getMaxBlood());
    }

    private static void bindEnemyData(BattlegroundViewModel newModel, Context context, Fighter enemy) {
        newModel.setEnemyCard(
                FighterCardModelFactory.generateFreshModel(context, enemy)
        );
    }

    private static List<ActionCardModel> generateActionCardModels(Context context, List<Action> possibleActions) {
        List<ActionCardModel> cardModels = new ArrayList<>();
        for(Action action : possibleActions){
            cardModels.add(ActionCardModelFactory.generateFreshModel(context, action));
        }
        return cardModels;
    }

    private static void bindPossibleActions(BattlegroundViewModel newModel, Context context, Fighter pc) {
        newModel.setPossibleActions(
                generateActionCardModels(context, pc.getEntity().getPossibleActions())
        );
    }

    public static BattlegroundViewModel generateFreshModel(Context context, Fighter pc, Fighter enemy){
        BattlegroundViewModel newModel = new BattlegroundViewModel();
        bindPcData(newModel, context, pc);
        bindEnemyData(newModel, context, enemy);
        bindPossibleActions(newModel, context, pc);

        return newModel;
    }

    private BattlegroundViewModelFactory() { }
}
