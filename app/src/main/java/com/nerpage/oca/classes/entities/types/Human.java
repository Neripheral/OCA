package com.nerpage.oca.classes.entities.types;

import androidx.annotation.NonNull;

import com.nerpage.oca.classes.Entity;
import com.nerpage.oca.classes.HumanEquipment;
import com.nerpage.oca.classes.fighting.Action;
import com.nerpage.oca.classes.fighting.DuelistAI;
import com.nerpage.oca.classes.fighting.actions.Punch;

import java.util.List;

public abstract class Human extends Entity {

    public final int PUNCH_POWER = 20;

    //================================================================================
    // Overrides
    //================================================================================

    @Override
    public int getMaxBlood() {
        return 100;
    }
    
    @NonNull
    @Override
    public List<Class<? extends Action>> getPossibleActions() {
        List<Class<? extends Action>> actions = super.getPossibleActions();
        assert actions != null;
        actions.add(Punch.class);
        return actions;
    }

    //================================================================================
    // Constructors
    //================================================================================

    public Human(){
        this.setEquipment(new HumanEquipment());
    }


}
