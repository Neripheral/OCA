package com.nerpage.oca.classes;

import androidx.annotation.Nullable;

import com.nerpage.oca.classes.fighting.Action;
import com.nerpage.oca.classes.fighting.Status;

import java.util.ArrayList;
import java.util.List;

public abstract class Entity implements Identifiable{
    //================================================================================
    // Fields
    //================================================================================

    private int blood;
    private Equipment equipment;

    //================================================================================
    // Accessors
    //================================================================================


    @Override
    public String getPrefix() {
        return "entity";
    }

    public int getBlood() {
        return blood;
    }

    public Entity setBlood(int blood) {
        if(blood < this.getMinBlood())
            blood = this.getMinBlood();
        else if(blood > this.getMaxBlood())
            blood = this.getMaxBlood();
        this.blood = blood;
        return this;
    }

    public Equipment getEquipment() {
        return equipment;
    }

    public Entity setEquipment(Equipment equipment) {
        this.equipment = equipment;
        return this;
    }


    //================================================================================
    // Methods
    //================================================================================



    public int getMinBlood(){
        return 0;
    }

    abstract public int getMaxBlood();

    public boolean isDead(){
        return getBlood() == 0;
    }

    public void applyStatus(Status status){
        if(status instanceof Status.onApplication)
            ((Status.onApplication) status).onApplication(this);
    }

    @Nullable
    public List<Action> getPossibleActions(){
        return new ArrayList<>();
    }

    //================================================================================
    // Constructors
    //================================================================================

    public Entity(){}
}
