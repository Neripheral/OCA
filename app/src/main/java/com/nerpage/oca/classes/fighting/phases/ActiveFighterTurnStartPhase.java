package com.nerpage.oca.classes.fighting.phases;

import com.nerpage.oca.classes.fighting.Fight;
import com.nerpage.oca.classes.fighting.Fighter;
import com.nerpage.oca.classes.fighting.ledger.events.FightEvent;
import com.nerpage.oca.classes.fighting.ledger.events.FighterTurnStartedEvent;

public class ActiveFighterTurnStartPhase extends FighterTurnFightPhase {
    @Override
    public FightPhase getNextPhase() {
        return new ActiveFighterActionExecutionPhase(getFight(), getActiveFighter());
    }

    @Override
    public void execute() {

    }

    @Override
    public FightEvent getEventAfterPhase() {
        return new FighterTurnStartedEvent(getActiveFighter());
    }

    public ActiveFighterTurnStartPhase(Fight fight, Fighter activeFighter) {
        super(fight, activeFighter);
    }
}
