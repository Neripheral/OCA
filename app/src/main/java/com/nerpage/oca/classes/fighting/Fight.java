package com.nerpage.oca.classes.fighting;

import com.nerpage.oca.classes.Entity;
import com.nerpage.oca.classes.Event;
import com.nerpage.oca.classes.fighting.actions.Action;
import com.nerpage.oca.classes.fighting.behaviors.FightingBehavior;
import com.nerpage.oca.classes.fighting.ledger.events.EntityPerformedActionEvent;
import com.nerpage.oca.classes.fighting.ledger.events.EntitySelectedActionEvent;
import com.nerpage.oca.classes.fighting.ledger.events.FightEvent;
import com.nerpage.oca.classes.fighting.ledger.events.FightStartedEvent;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Fight {
    //================================================================================
    // region //            Inner classes

    public interface FightListener{
        void notifyAbout(FightEvent event);
    }

    // endregion //         Inner classes
    //================================================================================
    //================================================================================
    // region //            Fields

    private final List<Fighter> fighters = new ArrayList<>();
    private final FightListener fightListener;

    // endregion //         Fields
    //================================================================================
    //================================================================================
    // region //            Accessors

    public FightListener getFightListener() {
        return fightListener;
    }

    // endregion //         Accessors
    //================================================================================
    //================================================================================
    // region //            Private methods

    private Fighter getNextFighter(){
        return this.getFighters()
                .stream()
                .filter(Fighter::canFight)
                .min(Comparator.comparing(Fighter::getStopwatchTime))
                .orElse(null);
    }

    private void executePendingActionOf(Fighter activeFighter) {
        Action pendingAction = activeFighter.getPendingAction();
        if(pendingAction != null){
            //TODO: clashing Actions
            notifyAbout(new EntityPerformedActionEvent(pendingAction, activeFighter.getEntity()));
            pendingAction.getTarget().applyStatus(pendingAction.getAppliedStatus());
        }
        activeFighter.setPendingAction(null);
    }

    private void proceedWithNextFighter(){
        Fighter activeFighter = getNextFighter();

        if(activeFighter != null) { // null -> end of fight
            executePendingActionOf(activeFighter);
            activeFighter.promptAction(getFightersWithout(activeFighter), this::onActionSelectedNotified);
        }
    }

    private void onActionSelectedNotified(Fighter fighter, Action action){
        notifyAbout(new EntitySelectedActionEvent(action, fighter.getEntity()));
        fighter.setPendingAction(action);
        if(action != null)
            fighter.addToStopwatch(action.getTimeSpan());
        proceedWithNextFighter();
    }

    private void notifyAbout(FightEvent event){
        getFightListener().notifyAbout(event);
    }

    // endregion //         Private methods
    //================================================================================
    //================================================================================
    // region //            Interface

    public List<Fighter> getFighters() {
        return fighters;
    }

    public List<Fighter> getFightersWithout(Fighter fighter){
        List<Fighter> toReturn = new ArrayList<>(this.getFighters());
        toReturn.remove(fighter);
        return toReturn;
    }

    public Fighter enrollFighter(Entity entity, FightingBehavior behavior, int handicapTime){
        Fighter newFighter = new Fighter(entity, behavior);
        newFighter.setStopwatchTime(handicapTime);
        this.getFighters().add(newFighter);
        //notifyObservers(new FighterEnrolledEvent(newFighter));
        return newFighter;
    }

    public void start(){
        notifyAbout(new FightStartedEvent(getFighters()));
        proceedWithNextFighter();
    }

    // endregion //         Interface
    //================================================================================
    //================================================================================
    // region //            Constructors

    public Fight(FightListener listener){
        this.fightListener = listener;
    }

    // endregion //         Constructors
    //================================================================================

}
