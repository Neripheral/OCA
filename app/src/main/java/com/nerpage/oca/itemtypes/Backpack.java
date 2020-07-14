package com.nerpage.oca.itemtypes;

import androidx.annotation.CallSuper;

import com.nerpage.oca.classes.Equipment;
import com.nerpage.oca.interfaces.Equipable;
import com.nerpage.oca.interfaces.Inventory;

public abstract class Backpack extends Container implements Inventory, Equipable {
    @CallSuper
    @Override
    public void initTags() {
        this.getTags().add(Tag.BACKPACK);
    }

    @Override
    public Equipment.Slot getEquipableSlot() {
        return Equipment.Slot.BACK;
    }

    public Backpack(){

    }
}
