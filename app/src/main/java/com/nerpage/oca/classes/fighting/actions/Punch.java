package com.nerpage.oca.classes.fighting.actions;

import com.nerpage.oca.classes.fighting.Action;
import com.nerpage.oca.classes.fighting.Status;
import com.nerpage.oca.classes.fighting.statuses.Bloodsuck;

public class Punch extends Action {
    //================================================================================
    // Fields
    //================================================================================

    private Bloodsuck appliedStatus;

    //================================================================================
    // Overrides
    //================================================================================

    @Override
    public Status getAppliedStatus() {
        return this.appliedStatus;
    }

    public Punch setAppliedStatus(Bloodsuck appliedStatus) {
        this.appliedStatus = appliedStatus;
        return this;
    }

    //================================================================================
    // Constructors
    //================================================================================

    public Punch(Bloodsuck appliedStatus) {
        this.setAppliedStatus(appliedStatus);
    }

    public Punch(int power){
        this(new Bloodsuck(power));
    }
}
