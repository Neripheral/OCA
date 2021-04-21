package com.nerpage.oca.classes.fighting.phases;

import com.nerpage.oca.classes.fighting.Fight;
import com.nerpage.oca.classes.fighting.Fighter;

import java.util.Comparator;

public class ActiveFighterCalculationPhase extends FightPhase {
    //================================================================================
    // region //            Fields

    private Fighter activeFighter;

    // endregion //         Fields
    //================================================================================
    //================================================================================
    // region //            Accessors

    private Fighter getActiveFighter() {
        return activeFighter;
    }

    private ActiveFighterCalculationPhase setActiveFighter(Fighter activeFighter) {
        this.activeFighter = activeFighter;
        return this;
    }

    // endregion //         Accessors
    //================================================================================
    //================================================================================
    // region //            Methods

    private Fighter getNextFighter(){
        return getFight().getFighters()
                .stream()
                .filter(Fighter::canFight)
                .min(Comparator.comparing(Fighter::getStopwatchTime))
                .orElse(null);
    }

    @Override
    public FightPhase getNextPhase() {
        return new ActiveFighterTurnStartPhase(getFight(), getActiveFighter());
    }

    @Override
    public void execute() {
        setActiveFighter(getNextFighter());
    }

    // endregion //         Methods
    //================================================================================
    //================================================================================
    // region //            Constructors

    public ActiveFighterCalculationPhase(Fight fight) {
        super(fight);
    }

    // endregion //         Constructors
    //================================================================================
}
