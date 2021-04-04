package com.nerpage.oca.classes.fighting;

import com.nerpage.oca.classes.Entity;

import java.util.List;

public class Fighter {
    //================================================================================
    // region //            Fields

    private Entity entity;
    private Action pendingAction;
    private FightingBehavior behavior;
    private int stopwatchTime;

    // endregion //         Fields
    //================================================================================
    //================================================================================
    // region //            Accessors

    public Entity getEntity() {
        return entity;
    }

    public Fighter setEntity(Entity entity) {
        this.entity = entity;
        return this;
    }

    public Action getPendingAction() {
        return pendingAction;
    }

    public Fighter setPendingAction(Action pendingAction) {
        this.pendingAction = pendingAction;
        return this;
    }

    public FightingBehavior getBehavior() {
        return behavior;
    }

    public Fighter setBehavior(FightingBehavior behavior) {
        this.behavior = behavior;
        return this;
    }

    public int getStopwatchTime() {
        return stopwatchTime;
    }

    public Fighter setStopwatchTime(int stopwatchTime) {
        this.stopwatchTime = stopwatchTime;
        return this;
    }

    // endregion //         Accessors
    //================================================================================
    //================================================================================
    // region //            Methods

    public Action promptAction(List<Fighter> otherFighters){
        return this.getBehavior().promptAction(this, otherFighters);
    }

    // endregion //         Methods
    //================================================================================
    //================================================================================
    // region //            Constructors

    public Fighter(Entity entity, FightingBehavior behavior){
        this.entity = entity;
        this.pendingAction = null;
        this.behavior = behavior;
        this.stopwatchTime = 0;
    }

    // endregion //         Constructors
    //================================================================================

}
