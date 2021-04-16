package com.nerpage.oca.classes.fighting;

import com.nerpage.oca.classes.Entity;
import com.nerpage.oca.classes.PlayerCharacter;
import com.nerpage.oca.classes.fighting.behaviors.FightingBehavior;
import com.nerpage.oca.classes.fighting.ledger.FightLedger;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;

public class FightManager {
    //================================================================================
    // region //            Fields

    private Fight fight = new Fight();
    private Fighter pcFighter = null;
    private FightLedger ledger = new FightLedger();

    // endregion //         Fields
    //================================================================================
    //================================================================================
    // region //            Accessors

    private Fight getFight() {
        return fight;
    }

    private FightManager setFight(Fight fight) {
        this.fight = fight;
        return this;
    }

    public Fighter getPcFighter() {
        return pcFighter;
    }

    private FightManager setPcFighter(Fighter pcFighter) {
        this.pcFighter = pcFighter;
        return this;
    }

    private FightLedger getLedger() {
        return ledger;
    }

    private FightManager setLedger(FightLedger ledger) {
        this.ledger = ledger;
        return this;
    }

    // endregion //         Accessors
    //================================================================================
    //================================================================================
    // region //            Private Methods



    // endregion //         Private Methods
    //================================================================================
    //================================================================================
    // region //            Interface

    public FightManager enrollPlayer(PlayerCharacter pc, FightingBehavior behavior){
        setPcFighter(
                getFight().enrollFighter(pc, behavior, 0)
        );

        return this;
    }

    public FightManager enrollFighter(Fighter opponent, int handicapTime){
        getFight().enrollFighter(opponent.getEntity(), opponent.getBehavior(), handicapTime);
        return this;
    }

    public List<Fighter> getFighters(){
        return getFight().getFighters();
    }

    public List<Fighter> getParticipantsExceptForPc(){
        return getFight().getFightersWithout(getPcFighter());
    }

    public void addObserver(Fight.FightObserver observer) {
        getFight().addObserver(observer);
    }

    public void start(){
        getFight().start();
    }

    // endregion //         Interface
    //================================================================================


    // endregion //         Methods
    //================================================================================
    //================================================================================
    // region //            Constructors

    public FightManager(){}

    // endregion //         Constructors
    //================================================================================
}

