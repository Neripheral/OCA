package com.nerpage.oca.classes;

public abstract class Entity implements Identifiable{
    //================================================================================
    // Fields
    //================================================================================

    private Equipment equipment;

    //================================================================================
    // Accessors
    //================================================================================


    @Override
    public String getPrefix() {
        return "entity";
    }

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
