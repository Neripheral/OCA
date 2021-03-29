package com.nerpage.oca.classes.fighting;

import com.nerpage.oca.classes.Entity;

public class DuelManager {
    //================================================================================
    // Inner class
    //================================================================================

    public interface GoalChecking{
        boolean check(DuelManager duelManager);
    }

    enum TurnStatus{
        ALPHA_TURN,
        BRAVO_TURN
    }

    enum Goal{
        DEATH(
                duelManager ->
                        duelManager.getEntityAlpha().isDead() || duelManager.getEntityBravo().isDead()
        );

        GoalChecking referee;

        Goal(GoalChecking referee){
            this.referee = referee;
        }

    }

    //================================================================================
    // Fields
    //================================================================================

    private Entity entityAlpha;
    private Entity entityBravo;
    private TurnStatus turnStatus;
    private Goal goal;

    //================================================================================
    // Accessors
    //================================================================================

    public Entity getEntityAlpha() {
        return entityAlpha;
    }

    public DuelManager setEntityAlpha(Entity entityAlpha) {
        this.entityAlpha = entityAlpha;
        return this;
    }

    public Entity getEntityBravo() {
        return entityBravo;
    }

    public DuelManager setEntityBravo(Entity entityBravo) {
        this.entityBravo = entityBravo;
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

    public boolean checkForGoal(){
        return this.getGoal().referee.check(this);
    }

    public void advanceTurn(){
        if(this.getTurnStatus() == TurnStatus.ALPHA_TURN)
            this.setTurnStatus(TurnStatus.BRAVO_TURN);
        else
            this.setTurnStatus(TurnStatus.ALPHA_TURN);
    }

    //================================================================================
    // Constructors
    //================================================================================

    public DuelManager(Entity alpha, Entity bravo, Goal goal, TurnStatus startingTurn){
        this.setEntityAlpha(alpha);
        this.setEntityBravo(bravo);
        this.setGoal(goal);
        this.setTurnStatus(startingTurn);
    }

    public DuelManager(Entity alpha, Entity bravo, Goal goal){
        this(alpha, bravo, goal, TurnStatus.ALPHA_TURN);
    }

    public DuelManager(Entity alpha, Entity bravo){
        this(alpha, bravo, Goal.DEATH);
    }
}

