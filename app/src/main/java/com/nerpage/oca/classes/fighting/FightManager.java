package com.nerpage.oca.classes.fighting;

import com.nerpage.oca.classes.Ledger;
import com.nerpage.oca.classes.PlayerCharacter;
import com.nerpage.oca.classes.fighting.behaviors.FightingBehavior;
import com.nerpage.oca.classes.fighting.ledger.events.FightEvent;

import java.util.List;

public class FightManager {
    //================================================================================
    // region //            Fields

    private final Fight fight;
    private final ObserversManager observersManager;
    private Fighter pcFighter = null;
    private Ledger ledger = new Ledger();

    // endregion //         Fields
    //================================================================================
    //================================================================================
    // region //            Accessors


    private Fight getFight() {
        return fight;
    }

    public ObserversManager getObserversManager() {
        return observersManager;
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

    private void onObserversReady(){
        getFight().proceed();
    }

    private void onEventRegistered(FightEvent event){
        getObserversManager().notifyObserversAbout(event);
    }

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

    public Runnable addObserver(ObserversManager.FightListener listener) {
        return getObserversManager().addObserver(listener);
    }

    public void start(){
        getFight().start();
    }

    // endregion //         Interface
    //================================================================================
    //================================================================================
    // region //            Constructors

    public FightManager(){
        this.fight = new Fight(this::onEventRegistered);
        this.observersManager = new ObserversManager(this::onObserversReady);
    }

    // endregion //         Constructors
    //================================================================================
}

