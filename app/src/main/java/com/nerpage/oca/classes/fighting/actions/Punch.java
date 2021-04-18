package com.nerpage.oca.classes.fighting.actions;

import androidx.annotation.NonNull;

import com.nerpage.oca.R;
import com.nerpage.oca.classes.fighting.statuses.Bloodsuck;

public class Punch extends Action implements Action.HasEffectAnimation {


    //================================================================================
    // region //            Fields

    private Bloodsuck appliedStatus;

    // endregion //         Fields
    //================================================================================
    //================================================================================
    // region //            Accessors

    @Override
    public Bloodsuck getAppliedStatus() {
        return this.appliedStatus;
    }

    public Punch setAppliedStatus(Bloodsuck appliedStatus) {
        this.appliedStatus = appliedStatus;
        return this;
    }

    // endregion //         Accessors
    //================================================================================
    //================================================================================
    // region //            Methods

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

    @Override
    public int getEffectResId() {
        return R.drawable.animation_blunt;
    }

    @Override
    public int getEffectDuration() {
        return Duration.SHORT.value;
    }

    @Override
    public float getEffectScale() {
        return Scale.SMALL.value;
    }

    // endregion //         Methods
    //================================================================================
    //================================================================================
    // region //            Constructors

    public Punch(Bloodsuck appliedStatus) {
        this.setAppliedStatus(appliedStatus);
    }

    public Punch(int power){
        this(new Bloodsuck(power));
    }

    // endregion //         Constructors
    //================================================================================
}
