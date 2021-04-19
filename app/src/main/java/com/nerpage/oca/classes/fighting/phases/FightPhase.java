package com.nerpage.oca.classes.fighting.phases;

import com.nerpage.oca.classes.fighting.Fight;

public abstract class FightPhase {
    private final Fight fight;

    public Fight getFight() {
        return fight;
    }

    public abstract FightPhase getNextPhase();
    public abstract void execute();

    public FightPhase(Fight fight) {
        this.fight = fight;
    }
}