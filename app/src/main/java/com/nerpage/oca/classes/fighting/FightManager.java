package com.nerpage.oca.classes.fighting;

import com.nerpage.oca.classes.Ledger;
import com.nerpage.oca.classes.PlayerCharacter;
import com.nerpage.oca.classes.fighting.behaviors.FightingBehavior;

import java.util.List;

public class FightManager {
    //================================================================================
    // region //            Fields

    private Fight fight = new Fight();
    private Fighter pcFighter = null;
    private Ledger ledger = new Ledger();

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

    private Ledger getLedger() {
        return ledger;
    }

    private FightManager setLedger(Ledger ledger) {
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

