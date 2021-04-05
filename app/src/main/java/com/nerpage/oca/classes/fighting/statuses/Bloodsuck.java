package com.nerpage.oca.classes.fighting.statuses;

import com.nerpage.oca.classes.Entity;
import com.nerpage.oca.classes.fighting.Status;

public class Bloodsuck extends Status {
    //================================================================================
    // Fields
    //================================================================================

    private int suckStrength;

    //================================================================================
    // Accessors
    //================================================================================

    public int getSuckStrength() {
        return suckStrength;
    }

    public Bloodsuck setSuckStrength(int suckStrength) {
        this.suckStrength = suckStrength;
        return this;
    }

    //================================================================================
    // Overrides
    //================================================================================

    @Override
    public void onApplication(Entity entity) {
        entity.setBlood(entity.getBlood() - suckStrength);
    }

    //================================================================================
    // Constructors
    //================================================================================

    public Bloodsuck(int suckStrength) {
        this.setSuckStrength(suckStrength);
    }
}
