package com.nerpage.oca.models;

import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class BattlegroundViewModel extends ViewModel {
    //================================================================================
    // region //            Fields

    private String enemyTitle = "missing_enemy_title";
    private int enemyCurrentBlood = 0;
    private int enemyMaxBlood = 0;
    private int pcCurrentBlood = 0;
    private int pcMaxBlood = 0;
    private List<ActionCardModel> possibleActions = new ArrayList<>();

    // endregion //         Fields
    //================================================================================
    //================================================================================
    // region //            Accessors

    public String getEnemyTitle() {
        return enemyTitle;
    }

    public BattlegroundViewModel setEnemyTitle(String enemyTitle) {
        this.enemyTitle = enemyTitle;
        return this;
    }

    public int getEnemyCurrentBlood() {
        return enemyCurrentBlood;
    }

    public BattlegroundViewModel setEnemyCurrentBlood(int enemyCurrentBlood) {
        this.enemyCurrentBlood = enemyCurrentBlood;
        return this;
    }

    public int getEnemyMaxBlood() {
        return enemyMaxBlood;
    }

    public BattlegroundViewModel setEnemyMaxBlood(int enemyMaxBlood) {
        this.enemyMaxBlood = enemyMaxBlood;
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
