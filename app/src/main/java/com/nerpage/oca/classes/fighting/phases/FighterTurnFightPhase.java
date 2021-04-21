package com.nerpage.oca.classes.fighting.phases;

import com.nerpage.oca.classes.fighting.Fight;
import com.nerpage.oca.classes.fighting.Fighter;

public abstract class FighterTurnFightPhase extends FightPhase {
    //================================================================================
    // region //            Fields

    private Fighter activeFighter;

    // endregion //         Fields
    //================================================================================
    //================================================================================
    // region //            Accessors

    protected Fighter getActiveFighter() {
        return activeFighter;
    }

    private FighterTurnFightPhase setActiveFighter(Fighter activeFighter) {
        this.activeFighter = activeFighter;
        return this;
    }

    // endregion //         Accessors
    //================================================================================
    //================================================================================
    // region //            Constructors

    public FighterTurnFightPhase(Fight fight, Fighter activeFighter){
        super(fight);
        setActiveFighter(activeFighter);
    }

    // endregion //         Constructors
    //================================================================================
}
