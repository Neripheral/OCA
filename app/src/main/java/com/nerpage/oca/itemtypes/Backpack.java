package com.nerpage.oca.itemtypes;

import androidx.annotation.CallSuper;

import com.nerpage.oca.classes.HumanEquipment;
import com.nerpage.oca.interfaces.Equipable;
import com.nerpage.oca.interfaces.Inventory;

public abstract class Backpack extends Container implements Inventory, Equipable {
    @CallSuper
    @Override
    public void initTags() {
        this.getTags().add(Tag.BACKPACK);
    }

    @Override
    public Object getEquipableSlot() {
        return HumanEquipment.Slot.BACK;
    }

    public Backpack(){

    }
}
