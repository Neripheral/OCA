package com.nerpage.oca.classes.fighting.phases;

import com.nerpage.oca.classes.fighting.Fight;

public class ActiveFighterActionExecution extends FightPhase {
    @Override
    public FightPhase getNextPhase() {
        return new ActiveFighterActionSelection(getFight());
    }

    @Override
    public void execute() {
        //TODO: executing fighter action
    }

    public ActiveFighterActionExecution(Fight fight) {
        super(fight);
    }
}
