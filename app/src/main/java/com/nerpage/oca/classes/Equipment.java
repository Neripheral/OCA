package com.nerpage.oca.classes;

import com.nerpage.oca.interfaces.Equipable;
import com.nerpage.oca.interfaces.Inventory;

import java.util.HashMap;
import java.util.Map;

public class Equipment {
    //================================================================================
    // Inner class
    //================================================================================

    public enum Slot{
        BACK,
        RIGHT_PALM,
        SHIRT,
        LEFT_HAND,
        RIGHT_HAND;
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
        return this.boundInventory;
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

    public boolean isSlotEmpty(Equipment.Slot slot){
        return getSlots().get(slot) == null;
    }

    public Item unequip(Equipment.Slot slot, boolean moveToDefaultInventory) {
        Item currentItem = getSlots().get(slot);
        if (currentItem != null) {
            if(moveToDefaultInventory && !this.getSlots().containsKey(Slot.RIGHT_PALM)) {
                this.getSlots().put(Slot.RIGHT_PALM, currentItem);
            }
            getSlots().remove(slot);
        }
        return currentItem;
    }

    public Item unequip(Equipment.Slot slot){
        return this.unequip(slot, false);
    }

    public void equip(Item item, Slot slot){
        this.unequip(slot);
        this.getSlots().put(slot, item);
    }

    public void equip(Equipable item){
        this.equip((Item)item, item.getEquipableSlot());
    }
}
