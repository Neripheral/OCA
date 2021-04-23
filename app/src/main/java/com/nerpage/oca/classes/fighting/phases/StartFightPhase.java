package com.nerpage.oca.classes.fighting.phases;

import com.nerpage.oca.classes.fighting.Fight;
import com.nerpage.oca.classes.fighting.events.FightEvent;
import com.nerpage.oca.classes.fighting.events.FightStartedEvent;

public class StartFightPhase extends FightPhase {
    @Override
    public FightPhase getNextPhase() {
        return new ActiveFighterCalculationPhase(getFight());
    }

    @Override
    public void execute() {
        //TODO: onFightStart
    }

    @Override
    public FightEvent getEventAfterPhase() {
        return new FightStartedEvent(getFight().getFighters());
    }

    public StartFightPhase(Fight fight) {
        super(fight);
    }
}
