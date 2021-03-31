package com.nerpage.oca.classes.entities.types;

import com.nerpage.oca.classes.Entity;
import com.nerpage.oca.classes.HumanEquipment;

public class Human extends Entity {
    //================================================================================
    // Inner classes
    //================================================================================

    /*public enum BodyPartKey {
        HEAD,
        NECK,
        CHEST;
    }*/

    //================================================================================
    // Constructors
    //================================================================================

    public Human(){
        this.setEquipment(new HumanEquipment());
    }
}
