package com.nerpage.oca.classes;

import androidx.annotation.Nullable;

import com.nerpage.oca.classes.fighting.Action;
import com.nerpage.oca.classes.fighting.Status;

import java.util.ArrayList;
import java.util.List;

public abstract class Entity implements Identifiable{
    //================================================================================
    // region //            Fields

    private int blood;
    private Equipment equipment;

    // endregion //         Fields
    //================================================================================
    //================================================================================
    // region //            Accessors

    public int getBlood() {
        return blood;
    }

    public Entity setBlood(int blood) {
        this.blood = Math.max(blood, this.getMinBlood());
        this.blood = Math.min(blood, this.getMaxBlood());
        return this;
    }

    public Equipment getEquipment() {
        return equipment;
    }

    public Entity setEquipment(Equipment equipment) {
        this.equipment = equipment;
        return this;
    }

    // endregion //         Accessors
    //================================================================================
    //================================================================================
    // region //            Methods

    @Override
    public String getPrefix() {
        return "entity";
    }

    public int getMinBlood(){
        return 0;
    }

    abstract public int getMaxBlood();

    public boolean isDead(){
        return getBlood() == this.getMinBlood();
    }

    public void applyStatus(Status status){
        status.onApplication(this);
    }

    public List<Action> getPossibleActions(){
        return new ArrayList<>();
    }

    // endregion //         Methods
    //================================================================================
    //================================================================================
    // region //            Constructors

    public Entity(){
        this.setBlood(this.getMaxBlood());
    }

    // endregion //         Constructors
    //================================================================================
}
