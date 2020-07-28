package com.nerpage.oca.entities;

import com.nerpage.oca.classes.Entity;
import com.nerpage.oca.classes.HumanEquipment;

import java.util.Arrays;

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
