package com.nerpage.oca.classes.fighting.phases;

import com.nerpage.oca.classes.fighting.Fight;
import com.nerpage.oca.classes.fighting.Fighter;

public abstract class FighterTurnFightPhase extends FightPhase{
    //================================================================================
    // region //            Fields

    private Fighter fighter;

    // endregion //         Fields
    //================================================================================
    //================================================================================
    // region //            Accessors

    protected Fighter getFighter() {
        return fighter;
    }

    private FighterTurnFightPhase setFighter(Fighter fighter) {
        this.fighter = fighter;
        return this;
    }

    // endregion //         Accessors
    //================================================================================
    //================================================================================
    // region //            Constructors

    public FighterTurnFightPhase(Fight fight, Fighter fighter){
        super(fight);
        setFighter(fighter);
    }

    // endregion //         Constructors
    //================================================================================
}
