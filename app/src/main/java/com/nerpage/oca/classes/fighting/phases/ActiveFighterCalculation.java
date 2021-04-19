package com.nerpage.oca.classes.fighting.phases;

import com.nerpage.oca.classes.fighting.Fight;

public class ActiveFighterCalculation extends FightPhase {
    @Override
    public FightPhase getNextPhase() {
        return new ActiveFighterActionExecution(getFight());
    }

    @Override
    public void execute() {
        //TODO: calculating active fighter
    }

    public ActiveFighterCalculation(Fight fight) {
        super(fight);
    }
}
