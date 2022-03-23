package com.nerpage.oca.classes.fighting;

import com.nerpage.oca.classes.Ledger;
import com.nerpage.oca.classes.PlayerCharacter;
import com.nerpage.oca.classes.events.EventController;
import com.nerpage.oca.classes.events.FlowController;
import com.nerpage.oca.classes.fighting.behaviors.FightingBehavior;

import java.util.List;

public class FightManager {
    //================================================================================
    // region //            Fields

    private final Fight fight;
    private Fighter pcFighter = null;
    private Ledger ledger = new Ledger();
    private final FlowController flowFreezer;
    private final EventController eventController;

    // endregion //         Fields
    //================================================================================
    //================================================================================
    // region //            Accessors


    private Fight getFight() {
        return fight;
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

    private FlowController getFlowFreezer() {
        return flowFreezer;
    }

    private EventController getEventController() {
        return eventController;
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

    public void start(){
        getFight().start();
    }

    public void addFlowFreezer(EventController.EventEmitter emitter){
        getFlowFreezer().addEventEmitter(emitter);
    }

    public void addEventListener(EventController.EventReceiver receiver){
        getEventController().addEventReceiver(receiver);
    }

    // endregion //         Interface
    //================================================================================
    //================================================================================
    // region //            Constructors

    public FightManager(){
        fight = new Fight();

        flowFreezer = new FlowController();
        getFlowFreezer().addEventReceiver(getFight());

        eventController = new EventController();
        getEventController().addEventEmitter(getFight());
    }

    // endregion //         Constructors
    //================================================================================
}

