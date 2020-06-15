package com.nerpage.oca.interfaces;

import com.nerpage.oca.classes.Item;
import com.nerpage.oca.classes.ItemStorage;

public interface Inventory {
    public interface ParentInventoryNotifier{
        public void onChildChanged();
    }

    default boolean isOpen(){
        return true;
    }
    default void setOpen(boolean isOpen){}
    ItemStorage getInventory();
    default int getContentWeight(){
        return getInventory().getContentWeight();
    };
    double getWeightReduction();
    default boolean matchesStoringCriteria(Item item){
        return true;
    }
    default String getContentWeightToDisplay(){
        return String.format("%.2f", (double)this.getContentWeight()/1000);
    }
    int getCapacity();
    default String getCapacityToDisplay(){
        return String.format("%.2f", (double)this.getCapacity()/1000);
    }
}
