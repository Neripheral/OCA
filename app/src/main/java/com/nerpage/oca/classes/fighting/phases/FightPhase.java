package com.nerpage.oca.classes.fighting.phases;

import com.nerpage.oca.classes.fighting.Fight;
import com.nerpage.oca.classes.fighting.ledger.events.FightEvent;

public abstract class FightPhase {
    private final Fight fight;

    public Fight getFight() {
        return fight;
    }

    public abstract FightPhase getNextPhase();
    public abstract void execute();
    public FightEvent getEventBeforePhase(){
        return null;
    }
    public FightEvent getEventAfterPhase(){
        return null;
    }

    public FightPhase(Fight fight) {
        this.fight = fight;
    }
}