package com.nerpage.oca.models;

import androidx.lifecycle.ViewModel;

public class BattlegroundViewModel extends ViewModel {
    //================================================================================
    // Fields
    //================================================================================

    private String enemyTitle = "missing_enemy_title";
    private int enemyCurrentBlood = 0;
    private int enemyMaxBlood = 0;
    private int pcCurrentBlood = 0;
    private int pcMaxBlood = 0;

    //================================================================================
    // Accessors
    //================================================================================

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
}
