package com.nerpage.oca.classes;

public class HumanEquipment extends Equipment {
    public enum Slot{
        CARRY,
        BACK,
        RIGHT_PALM,
        SHIRT,
        LEFT_HAND,
        RIGHT_HAND;
    }

    public Slot getCarrySlot(){
        return Slot.CARRY;
    }

    public HumanEquipment(){
        super();
        this.equip(new CarryingSpace(), this.getCarrySlot());
    }
}
