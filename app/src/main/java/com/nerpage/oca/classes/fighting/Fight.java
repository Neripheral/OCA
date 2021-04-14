package com.nerpage.oca.classes.fighting;

import com.nerpage.oca.classes.Entity;
import com.nerpage.oca.classes.Event;
import com.nerpage.oca.classes.fighting.behaviors.FightingBehavior;
import com.nerpage.oca.classes.fighting.ledger.events.EntityPerformedActionEvent;
import com.nerpage.oca.classes.fighting.ledger.events.EntitySelectedActionEvent;
import com.nerpage.oca.classes.fighting.ledger.events.FightStartedEvent;
import com.nerpage.oca.classes.fighting.ledger.events.FighterEnrolledEvent;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;

public class Fight {
    //================================================================================
    // region //            Inner classes

    public interface FightObserver{
        void notifyAboutProgress(Event event);
    }

    // endregion //         Inner classes
    //================================================================================
    //================================================================================
    // region //            Fields

    private final List<Fighter> fighters = new ArrayList<>();
    private final List<FightObserver> observers = new ArrayList<>();

    // endregion //         Fields
    //================================================================================
    //================================================================================
    // region //            Accessors

    private List<Fighter> getFighters() {
        return fighters;
    }

    private List<FightObserver> getObservers() {
        return observers;
    }

    // endregion //         Accessors
    //================================================================================
    //================================================================================
    // region //            Private methods

    private Fighter getNextFighter(){
        return this.getFighters()
                .stream()
                .min(Comparator.comparing(Fighter::getStopwatchTime))
                .orElseThrow(NoSuchElementException::new);
    }

    private List<Fighter> getFightersWithout(Fighter fighter){
        List<Fighter> toReturn = new ArrayList<>(this.getFighters());
        toReturn.remove(fighter);
        return toReturn;
    }

    private void executePendingActionOf(Fighter activeFighter) {
        Action pendingAction = activeFighter.getPendingAction();
        if(pendingAction != null){
            //TODO: clashing Actions
            notifyObservers(new EntityPerformedActionEvent(pendingAction));
            pendingAction.getTarget().applyStatus(pendingAction.getAppliedStatus());
        }
        activeFighter.setPendingAction(null);
    }

    private void proceedWithNextFighter(){
        Fighter activeFighter = getNextFighter();

        executePendingActionOf(activeFighter);
        activeFighter.promptAction(getFightersWithout(activeFighter), this::onActionSelectedNotified);
    }

    private void onActionSelectedNotified(Fighter fighter, Action action){
        notifyObservers(new EntitySelectedActionEvent(action));
        fighter.setPendingAction(action);
        if(action != null)
            fighter.addToStopwatch(action.getTimeSpan());
        proceedWithNextFighter();
    }

    private void notifyObservers(Event event){
        for(FightObserver observer : getObservers()){
            observer.notifyAboutProgress(event);
        }
    }

    // endregion //         Private methods
    //================================================================================
    //================================================================================
    // region //            Interface

    public void addObserver(FightObserver newObserver){
        getObservers().add(newObserver);
    }

    public void enrollFighter(Entity entity, FightingBehavior behavior, int handicapTime){
        Fighter newFighter = new Fighter(entity, behavior);
        newFighter.setStopwatchTime(handicapTime);
        this.getFighters().add(newFighter);
        notifyObservers(new FighterEnrolledEvent(newFighter));
    }

    public void start(){
        notifyObservers(new FightStartedEvent(getFighters()));
        proceedWithNextFighter();
    }

    // endregion //         Interface
    //================================================================================
}
