package com.nerpage.oca.classes;

import com.nerpage.oca.interfaces.Equippable;
import com.nerpage.oca.interfaces.Inventory;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Equipment {
    //================================================================================
    // Inner class
    //================================================================================

    public enum Slot{
        BACK,
        BELT,
        LEFT_PALM,
        RIGHT_PALM;
    }

    //================================================================================
    // Fields
    //================================================================================

    private Map<Slot, Item> slots = new HashMap<>();
    private Inventory boundInventory;

    //================================================================================
    // Accessors
    //================================================================================

    public Map<Slot, Item> getSlots() {
        return slots;
    }
    public Equipment setSlots(Map<Slot, Item> slots) {
        this.slots = slots;
        return this;
    }

    public Inventory getBoundInventory() {
        return boundInventory;
    }
    public Equipment setBoundInventory(Inventory boundInventory) {
        this.boundInventory = boundInventory;
        return this;
    }

    //================================================================================
    // Constructors
    //================================================================================

    public Equipment(Inventory boundInventory){
        this.setBoundInventory(boundInventory);
    }


    public Equipment(){
        this(null);
    }

    //================================================================================
    // Methods
    //================================================================================

    public void unequip(Equipment.Slot slot){
        Item currentItem = getSlots().get(slot);
        if(currentItem != null) {
            if(getBoundInventory() != null)
                getBoundInventory().getInventory().add((Item)currentItem);
            getSlots().remove(slot);
        }
    }

    public void equip(Item item, Slot slot){
        this.unequip(slot);
        this.getSlots().put(slot, item);
    }

    public void equip(Equippable item){
        this.equip((Item)item, item.getEquippableSlot());
    }
}
