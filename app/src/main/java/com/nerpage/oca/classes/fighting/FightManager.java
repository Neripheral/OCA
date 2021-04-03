package com.nerpage.oca.classes.fighting;

import com.nerpage.oca.classes.Entity;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class FightManager {
    //================================================================================
    // Inner class
    //================================================================================

    public interface OnYourTurnNotifier{
        Action getNextActionAgainst(Entity enemy);
    }

    public static class Fighter {
        private Entity entity;
        private OnYourTurnNotifier notifier;

        public Entity getEntity() {
            return entity;
        }

        public Fighter setEntity(Entity entity) {
            this.entity = entity;
            return this;
        }

        public OnYourTurnNotifier getNotifier() {
            return notifier;
        }

        public Fighter setNotifier(OnYourTurnNotifier notifier) {
            this.notifier = notifier;
            return this;
        }

        public Fighter(Entity entity, OnYourTurnNotifier notifier){
            this.setEntity(entity);
            this.setNotifier(notifier);
        }
    }

    enum TurnStatus{
        ALPHA,
        BRAVO
    }

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

    //================================================================================
    // Fields
    //================================================================================

    private List<Fighter> fighters = new ArrayList<>();
    private TurnStatus turnStatus;
    private Goal goal;

    //================================================================================
    // Accessors
    //================================================================================

    public List<Fighter> getFighters(){
        return new ArrayList<>(this.fighters);
    }

    public FightManager setFighters(ArrayList<Fighter> fighters){
        this.fighters = new ArrayList<>(fighters);
        return this;
    }

    public TurnStatus getTurnStatus() {
        return turnStatus;
    }

    public FightManager setTurnStatus(TurnStatus turnStatus) {
        this.turnStatus = turnStatus;
        return this;
    }

    public Goal getGoal() {
        return goal;
    }

    public FightManager setGoal(Goal goal) {
        this.goal = goal;
        return this;
    }

    //================================================================================
    // Methods
    //================================================================================

    private boolean checkForGoal(){
        return this.getGoal().check(this);
    }

    public void advanceTurn(){
        this.setTurnStatus(
                TurnStatus.values()[
                        (this.getTurnStatus().ordinal()+1) % TurnStatus.values().length
                        ]
        );
    }

    private FightManager enrollFighter(Fighter fighter){
        ArrayList<Fighter> fighters = (ArrayList<Fighter>)this.getFighters();
        fighters.add(fighter);
        this.setFighters(fighters);
        return this;
    }

    public FightManager enrollFighter(Entity entity, OnYourTurnNotifier notifier){
        return this.enrollFighter(new Fighter(entity, notifier));
    }

    public FightManager enrollAI(DuelistAI ai){
        return this.enrollFighter((Entity)ai, ai::getNextAction);
    }

    //================================================================================
    // Constructors
    //================================================================================

    public FightManager(){}
}

