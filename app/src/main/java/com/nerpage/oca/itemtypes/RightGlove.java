package com.nerpage.oca.itemtypes;

import com.nerpage.oca.classes.Equipment;

public abstract class RightGlove extends Glove {
    @Override
    public Equipment.Slot getEquipableSlot() {
        return Equipment.Slot.RIGHT_HAND;
    }
}
