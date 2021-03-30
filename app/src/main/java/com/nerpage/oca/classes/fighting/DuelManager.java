package com.nerpage.oca.classes.fighting;

import com.nerpage.oca.classes.Entity;

import java.util.ArrayList;
import java.util.List;

public class DuelManager {
    //================================================================================
    // Inner class
    //================================================================================

    public interface OnYourTurnNotifier{
        Action getNextActionAgainst(Entity enemy);
    }

    public static class Duelist {
        private Entity entity;
        private OnYourTurnNotifier notifier;

        public Entity getEntity() {
            return entity;
        }

        public Duelist setEntity(Entity entity) {
            this.entity = entity;
            return this;
        }

        public OnYourTurnNotifier getNotifier() {
            return notifier;
        }

        public Duelist setNotifier(OnYourTurnNotifier notifier) {
            this.notifier = notifier;
            return this;
        }

        public Duelist(Entity entity, OnYourTurnNotifier notifier){
            this.setEntity(entity);
            this.setNotifier(notifier);
        }
    }

    public interface Referee {
        boolean check(DuelManager duelManager);
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
                duelManager ->
                    duelManager.getDuelists()
                            .stream()
                            .anyMatch(duelist -> duelist.getEntity().isDead())
        );

        Referee referee;

        Goal(Referee referee){
            this.referee = referee;
        }

    }

    //================================================================================
    // Fields
    //================================================================================

    private List<Duelist> duelists;
    private TurnStatus turnStatus;
    private Goal goal;

    //================================================================================
    // Accessors
    //================================================================================

    public List<Duelist> getDuelists(){
        return new ArrayList<>(this.duelists);
    }

    public DuelManager setDuelists(ArrayList<Duelist> duelists){
        this.duelists = new ArrayList<>(duelists);
        return this;
    }

    public TurnStatus getTurnStatus() {
        return turnStatus;
    }

    public DuelManager setTurnStatus(TurnStatus turnStatus) {
        this.turnStatus = turnStatus;
        return this;
    }

    public Goal getGoal() {
        return goal;
    }

    public DuelManager setGoal(Goal goal) {
        this.goal = goal;
        return this;
    }

    //================================================================================
    // Methods
    //================================================================================

    private boolean checkForGoal(){
        return this.getGoal().referee.check(this);
    }

    public void advanceTurn(){
        this.setTurnStatus(
                TurnStatus.values()[
                        (this.getTurnStatus().ordinal()+1) % TurnStatus.values().length
                        ]
        );
    }

    private DuelManager enrollDuelist(Duelist duelist){
        ArrayList<Duelist> duelists = (ArrayList<Duelist>)this.getDuelists();
        duelists.add(duelist);
        this.setDuelists(duelists);
        return this;
    }

    public DuelManager enrollDuelist(Entity entity, OnYourTurnNotifier notifier){
        return this.enrollDuelist(new Duelist(entity, notifier));
    }

    public DuelManager enrollAI(DuelistAI ai){
        return this.enrollDuelist((Entity)ai, ai::getNextAction);
    }

    //================================================================================
    // Constructors
    //================================================================================

    public DuelManager(){}
}

