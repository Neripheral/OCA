package com.nerpage.oca.itemtypes;

import com.nerpage.oca.classes.HumanEquipment;

public abstract class LeftGlove extends Glove {
    @Override
    public Object getEquipableSlot() {
        return HumanEquipment.Slot.LEFT_HAND;
    }
}
