package com.nerpage.oca.classes;

import com.nerpage.oca.interfaces.Equipable;
import com.nerpage.oca.interfaces.Inventory;

import java.util.HashMap;
import java.util.Map;

public abstract class Equipment {
    //================================================================================
    // Fields
    //================================================================================

    private Map<Object, Item> slots = new HashMap<>();

    //================================================================================
    // Accessors
    //================================================================================

    public Map<Object, Item> getSlots() {
        return slots;
    }
    public Equipment setSlots(Map<Object, Item> slots) {
        this.slots = slots;
        return this;
    }

    //================================================================================
    // Constructors
    //================================================================================

    public Equipment(){}

    //================================================================================
    // Methods
    //================================================================================

    public boolean isSlotEmpty(Object slot){
        return getSlots().get(slot) == null;
    }

    public Item unequip(Object slot) {
        Item currentItem = getSlots().get(slot);
        if (currentItem != null) {
            getSlots().remove(slot);
        }
        return currentItem;
    }

    public Item equip(Item item, Object slot){
        Item oldItem = this.unequip(slot);
        this.getSlots().put(slot, item);
        return oldItem;
    }

    public void equip(Equipable item){
        this.equip((Item)item, item.getEquipableSlot());
    }
}
