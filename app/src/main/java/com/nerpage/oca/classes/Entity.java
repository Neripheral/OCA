package com.nerpage.oca.classes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Entity{
    //================================================================================
    // Fields
    //================================================================================

    private Equipment equipment;

    //================================================================================
    // Accessors
    //================================================================================

    public Equipment getEquipment() {
        return equipment;
    }

    public Entity setEquipment(Equipment equipment) {
        this.equipment = equipment;
        return this;
    }

    //================================================================================
    // Constructors
    //================================================================================

    public Entity(){}

    //================================================================================
    // Methods
    //================================================================================

}
