package com.nerpage.oca.itemtypes;

import com.nerpage.oca.classes.HumanEquipment;

public abstract class RightGlove extends Glove {
    @Override
    public Object getEquipableSlot() {
        return HumanEquipment.Slot.RIGHT_HAND;
    }
}
