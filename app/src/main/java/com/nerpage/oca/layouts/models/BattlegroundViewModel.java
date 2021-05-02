package com.nerpage.oca.layouts.models;

import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class BattlegroundViewModel extends ViewModel {
    //================================================================================
    // region //            Fields

    private int pcCurrentBlood = 0;
    private int pcMaxBlood = 0;
    private FighterCardModel enemyCard = new FighterCardModel();
    private List<ActionCardModel> possibleActions = new ArrayList<>();

    // endregion //         Fields
    //================================================================================
    //================================================================================
    // region //            Accessors

    public FighterCardModel getEnemyCard() {
        return enemyCard;
    }

    public BattlegroundViewModel setEnemyCard(FighterCardModel enemyCard) {
        this.enemyCard = enemyCard;
        return this;
    }

    public int getPcCurrentBlood() {
        return pcCurrentBlood;
    }

    public BattlegroundViewModel setPcCurrentBlood(int pcCurrentBlood) {
        this.pcCurrentBlood = pcCurrentBlood;
        return this;
    }

    public int getPcMaxBlood() {
        return pcMaxBlood;
    }

    public BattlegroundViewModel setPcMaxBlood(int pcMaxBlood) {
        this.pcMaxBlood = pcMaxBlood;
        return this;
    }

    public List<ActionCardModel> getPossibleActions() {
        return possibleActions;
    }

    public BattlegroundViewModel setPossibleActions(List<ActionCardModel> possibleActions) {
        this.possibleActions = possibleActions;
        return this;
    }

    // endregion //         Accessors
    //================================================================================
}
