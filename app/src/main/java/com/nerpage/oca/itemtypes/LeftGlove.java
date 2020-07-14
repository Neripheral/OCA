package com.nerpage.oca.itemtypes;

import com.nerpage.oca.classes.Equipment;

public abstract class LeftGlove extends Glove {
    @Override
    public Equipment.Slot getEquipableSlot() {
        return Equipment.Slot.LEFT_HAND;
    }
}
