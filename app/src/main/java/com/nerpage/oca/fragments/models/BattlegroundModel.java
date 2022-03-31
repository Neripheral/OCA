package com.nerpage.oca.fragments.models;

import com.nerpage.oca.fragments.Model;

import java.util.List;

public class BattlegroundModel extends Model {
    public int pcCurrentBlood;
    public int pcMaxBlood;

    //TODO: remove when separate fragment created
    public List<ActionCardModel> possibleActions;
}
