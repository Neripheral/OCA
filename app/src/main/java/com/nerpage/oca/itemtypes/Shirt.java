package com.nerpage.oca.itemtypes;

import com.nerpage.oca.classes.Equipment;
import com.nerpage.oca.classes.Item;
import com.nerpage.oca.interfaces.Equipable;

public abstract class Shirt extends Item implements Equipable {
    @Override
    public void initTags() {
        this.getTags().add(Tag.CLOTHING_TOP);
    }

    @Override
    public Equipment.Slot getEquipableSlot() {
        return Equipment.Slot.SHIRT;
    }
}
