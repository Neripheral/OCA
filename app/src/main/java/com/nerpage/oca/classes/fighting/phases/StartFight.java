package com.nerpage.oca.classes.fighting.phases;

import com.nerpage.oca.classes.fighting.Fight;

class StartFight extends FightPhase{
    @Override
    public FightPhase getNextPhase() {
        return new ActiveFighterCalculation(getFight());
    }

    @Override
    public void execute() {
        //TODO: onFightStart
    }

    public StartFight(Fight fight) {
        super(fight);
    }
}
