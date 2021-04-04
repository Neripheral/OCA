package com.nerpage.oca.classes.fighting;

import com.nerpage.oca.classes.Entity;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.function.Predicate;

public class FightManager {
    //================================================================================
    // region //            Inner classes

    enum Goal{
        /**
         * True if any of the participants is dead.
         */
        DEATH(
                fightManager ->
                        fightManager.getFighters()
                                .stream()
                                .anyMatch(fighter -> fighter.getEntity().isDead())
        );

        private Predicate<FightManager> condition;

        public boolean check(FightManager fightManager){
            return this.condition.test(fightManager);
        }

        Goal(Predicate<FightManager> condition){
            this.condition = condition;
        }

    }

    // endregion //         Inner classes
    //================================================================================
    //================================================================================
    // region //            Fields

    private List<Fighter> fighters = new ArrayList<>();
    private Goal goal;

    // endregion //         Fields
    //================================================================================
    //================================================================================
    // region //            Accessors

    public List<Fighter> getFighters(){
        return new ArrayList<>(this.fighters);
    }

    public FightManager setFighters(ArrayList<Fighter> fighters){
        this.fighters = new ArrayList<>(fighters);
        return this;
    }

    public Goal getGoal() {
        return goal;
    }

    public FightManager setGoal(Goal goal) {
        this.goal = goal;
        return this;
    }

    // endregion //         Accessors
    //================================================================================
    //================================================================================
    // region //            Methods

    private void calibrateStopwatches(){
        //TODO: initializing stopwatches
        this.getFighters().forEach(fighter -> fighter.setStopwatchTime(0));
    }

    private boolean didFightEnd(){
        return this.getGoal().check(this);
    }

    public FightManager enrollFighter(Fighter fighter){
        ArrayList<Fighter> fighters = (ArrayList<Fighter>)this.getFighters();
        fighters.add(fighter);
        this.setFighters(fighters);
        return this;
    }

    public FightManager enrollFighter(Entity entity, FightingBehavior behavior){
        return this.enrollFighter(new Fighter(entity, behavior));
    }

    private List<Fighter> getFightersWithout(Fighter fighter){
        // \/\/\/\/\/\/
        List<Fighter> toReturn = new ArrayList<>(this.getFighters());
        toReturn.remove(fighter);
        return toReturn;
        // /\/\/\/\/\/\
        //TODO: make sure this works
    }

    private Fighter getActiveFighter(){
        return this.getFighters()
                .stream()
                .min(Comparator.comparing(Fighter::getStopwatchTime))
                .orElseThrow(NoSuchElementException::new);
    }

    private void advanceTurn(){
        Fighter activeFighter = this.getActiveFighter();

        Action pendingAction = activeFighter.getPendingAction();
        if(pendingAction != null){
            //TODO: clashing Actions
            pendingAction.getTarget().applyStatus(pendingAction.getAppliedStatus());
        }

        Action chosenAction = activeFighter.promptAction(this.getFightersWithout(activeFighter));
        activeFighter.setPendingAction(chosenAction);
    }

    public void startFight(){
        this.calibrateStopwatches();
        while(!this.didFightEnd())
            this.advanceTurn();
    }

    // endregion //         Methods
    //================================================================================
    //================================================================================
    // region //            Constructors

    public FightManager(){}

    // endregion //         Constructors
    //================================================================================
}

