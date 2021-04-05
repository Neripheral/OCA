package com.nerpage.oca.classes.fighting;

import androidx.annotation.NonNull;

import com.nerpage.oca.classes.Entity;
import com.nerpage.oca.classes.Identifiable;

public abstract class Action implements Cloneable, Identifiable {
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
    //================================================================================
    // region //            Methods


    @Override
    public String getPrefix() {
        return "action";
    }

    @NonNull
    @Override
    public Action clone() {
        try {
            Action toReturn = (Action)super.clone();
            toReturn.setTarget(this.getTarget());
            toReturn.setSource(this.getSource());
            return toReturn;
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    public abstract Status getAppliedStatus();
    public abstract int getTimeSpan();

    // endregion //         Methods
    //================================================================================
}
