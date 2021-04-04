package com.nerpage.oca.classes.fighting;

import com.nerpage.oca.classes.Entity;

public abstract class Action {
    //================================================================================
    // region //            Fields

    private Entity source;
    private Entity target;

    // endregion //         Fields
    //================================================================================
    //================================================================================
    // region //            Accessors

    public Entity getSource() {
        return source;
    }

    public Action setSource(Entity source) {
        this.source = source;
        return this;
    }

    public Entity getTarget() {
        return target;
    }

    public Action setTarget(Entity target) {
        this.target = target;
        return this;
    }


    // endregion //         Accessors
    //================================================================================

    public abstract Status getAppliedStatus();
    public abstract int getTimeSpan();
}
