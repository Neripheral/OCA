package com.nerpage.oca.itemtypes;

import android.app.AlertDialog;

import androidx.annotation.CallSuper;

import com.nerpage.oca.classes.Equipment;
import com.nerpage.oca.interfaces.Equippable;
import com.nerpage.oca.interfaces.Inventory;

public abstract class Backpack extends Container implements Inventory, Equippable {
    @CallSuper
    @Override
    public void initTags() {
        this.getTags().add(Tag.BACKPACK);
    }

    @Override
    public Equipment.Slot getEquippableSlot() {
        return Equipment.Slot.BACK;
    }

    public Backpack(){

    }
}
