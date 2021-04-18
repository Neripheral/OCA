package com.nerpage.oca.classes.fighting.actions;

import com.nerpage.oca.R;
import com.nerpage.oca.classes.fighting.Status;
import com.nerpage.oca.classes.fighting.statuses.Bloodsuck;

public class Kick extends Action implements Action.HasEffectAnimation {
    //================================================================================
    // region //            Fields

    private Bloodsuck appliedStatus;

    // endregion //         Fields
    //================================================================================
    //================================================================================
    // region //            Accessors

    @Override
    public Status getAppliedStatus() {
        return appliedStatus;
    }

    public Kick setAppliedStatus(Bloodsuck appliedStatus) {
        this.appliedStatus = appliedStatus;
        return this;
    }

    // endregion //         Accessors
    //================================================================================
    //================================================================================
    // region //            Methods

    @Override
    public int getTimeSpan() {
        return 2000;
    }

    @Override
    public int getThumbnailResId() {
        return R.drawable.action_kick;
    }

    @Override
    public int getDescriptionResId() {
        return R.string.action_kick_description;
    }

    @Override
    public int getEffectResId() {
        return R.drawable.animation_blunt;
    }

    @Override
    public int getEffectDuration() {
        return Duration.MEDIUM.value;
    }

    @Override
    public float getEffectScale() {
        return Scale.LARGE.value;
    }

    // endregion //         Methods
    //================================================================================
    //================================================================================
    // region //            Constructors

    public Kick(Bloodsuck appliedStatus){
        this.appliedStatus = appliedStatus;
    }

    public Kick(int strength) {
        this(new Bloodsuck(strength));
    }

    // endregion //         Constructors
    //================================================================================
}
