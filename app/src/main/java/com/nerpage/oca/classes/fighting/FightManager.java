package com.nerpage.oca.classes.fighting;

import com.nerpage.oca.classes.Entity;

import java.util.ArrayList;
import java.util.List;
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

    private boolean checkForGoal(){
        return this.getGoal().check(this);
    }

    private FightManager enrollFighter(Fighter fighter){
        ArrayList<Fighter> fighters = (ArrayList<Fighter>)this.getFighters();
        fighters.add(fighter);
        this.setFighters(fighters);
        return this;
    }

    public FightManager enrollFighter(Entity entity){
        return this.enrollFighter(new Fighter(entity));
    }

    public FightManager enrollAI(){
        //TODO: how to enroll an ai
        return this;
    }

    // endregion //         Methods
    //================================================================================
    //================================================================================
    // region //            Constructors

    public FightManager(){}

    // endregion //         Constructors
    //================================================================================
}

