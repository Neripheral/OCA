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
    public List<Action> getPossibleActions() {
        List<Action> actions = super.getPossibleActions();
        assert actions != null;
        actions.add(new Punch(PUNCH_POWER));

        return actions;
    }

    //================================================================================
    // Constructors
    //================================================================================

    public Human(){
        this.setEquipment(new HumanEquipment());
    }


}
