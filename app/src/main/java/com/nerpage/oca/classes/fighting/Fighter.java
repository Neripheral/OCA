package com.nerpage.oca.classes.fighting;

import com.nerpage.oca.classes.Entity;

public class Fighter {
    //================================================================================
    // region //            Fields

    private Entity entity;
    private Action pendingAction;
    private FightingBehavior behavior;

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

    // endregion //         Accessors
    //================================================================================
    //================================================================================
    // region //            Constructors

    public Fighter(Entity entity){
        this.setEntity(entity);
    }

    // endregion //         Constructors
    //================================================================================

}
