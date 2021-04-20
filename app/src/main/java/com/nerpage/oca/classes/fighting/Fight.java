package com.nerpage.oca.classes.fighting;

import com.nerpage.oca.classes.Entity;
import com.nerpage.oca.classes.fighting.behaviors.FightingBehavior;
import com.nerpage.oca.classes.fighting.ledger.events.FightEvent;
import com.nerpage.oca.classes.fighting.phases.FightPhase;
import com.nerpage.oca.classes.fighting.phases.StartFightPhase;

import java.util.ArrayList;
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
    private FightPhase currentPhase;

    // endregion //         Fields
    //================================================================================
    //================================================================================
    // region //            Accessors

    private FightListener getFightListener() {
        return fightListener;
    }

    private FightPhase getCurrentPhase() {
        return currentPhase;
    }

    private Fight setCurrentPhase(FightPhase currentPhase) {
        this.currentPhase = currentPhase;
        return this;
    }

    // endregion //         Accessors
    //================================================================================
    //================================================================================
    // region //            Private methods

    private void executePhase(){
        getCurrentPhase().execute();
        getFightListener().notifyAbout(getCurrentPhase().getEventAfterPhase());
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
        return newFighter;
    }

    public void proceed(){
        setCurrentPhase(
                getCurrentPhase().getNextPhase()
        );
        executePhase();
    }

    public void start(){
        setCurrentPhase(new StartFightPhase(this));
        executePhase();
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
