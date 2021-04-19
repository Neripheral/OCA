package com.nerpage.oca.classes.fighting.phases;

import com.nerpage.oca.classes.fighting.Fight;

public class ActiveFighterActionSelection extends FightPhase {
    @Override
    public FightPhase getNextPhase() {
        return new ActiveFighterCalculation(getFight());
        //TODO: branch off to end the fight
    }

    @Override
    public void execute() {
        //TODO: fighter selects action
    }

    public ActiveFighterActionSelection(Fight fight) {
        super(fight);
    }
}
