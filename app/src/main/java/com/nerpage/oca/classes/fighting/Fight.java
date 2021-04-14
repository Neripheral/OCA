package com.nerpage.oca.classes.fighting;

import com.nerpage.oca.classes.Entity;
import com.nerpage.oca.classes.fighting.behaviors.FightingBehavior;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;

public class Fight {
    //================================================================================
    // region //            Fields

    private List<Fighter> fighters = new ArrayList<>();

    // endregion //         Fields
    //================================================================================
    //================================================================================
    // region //            Accessors

    private List<Fighter> getFighters() {
        return fighters;
    }

    private Fight setFighters(List<Fighter> fighters) {
        this.fighters = fighters;
        return this;
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

    public List<Fighter> getFightersWithout(Fighter fighter){
        List<Fighter> toReturn = new ArrayList<>(this.getFighters());
        toReturn.remove(fighter);
        return toReturn;
    }

    private void executePendingActionOf(Fighter activeFighter) {
        Action pendingAction = activeFighter.getPendingAction();
        if(pendingAction != null){
            //TODO: clashing Actions
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
        fighter.setPendingAction(action);
        if(action != null)
            fighter.addToStopwatch(action.getTimeSpan());
        proceedWithNextFighter();
    }

    // endregion //         Private methods
    //================================================================================
    //================================================================================
    // region //            Interface

    public void enrollFighter(Entity entity, FightingBehavior behavior, int handicapTime){
        Fighter newFighter = new Fighter(entity, behavior);
        newFighter.setStopwatchTime(handicapTime);
        this.getFighters().add(newFighter);
    }

    public void start(){
        proceedWithNextFighter();
    }

    // endregion //         Interface
    //================================================================================
}
