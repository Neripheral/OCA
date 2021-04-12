package com.nerpage.oca.classes.fighting.actions;

import androidx.annotation.NonNull;

import com.nerpage.oca.R;
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
    public Bloodsuck getAppliedStatus() {
        return this.appliedStatus;
    }

    public Punch setAppliedStatus(Bloodsuck appliedStatus) {
        this.appliedStatus = appliedStatus;
        return this;
    }

    @Override
    public int getTimeSpan() {
        return 1000;
    }

    @Override
    public int getThumbnailResId() {
        return R.drawable.action_punch;
    }

    @Override
    public int getDescriptionResId() {
        return R.string.action_punch_description;
    }

    @NonNull
    @Override
    public Punch clone() {
        Punch toReturn = (Punch)super.clone();
        toReturn.setAppliedStatus(this.getAppliedStatus().clone());
        return toReturn;
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
