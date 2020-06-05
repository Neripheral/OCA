package com.nerpage.oca.classes;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ItemStorage {
    //================================================================================
    // Constants
    //================================================================================

    public static final int MISSING_ITEM = -1;

    //================================================================================
    // Attributes
    //================================================================================

    private ArrayList<Item> storedItems;

    //================================================================================
    // Accessors
    //================================================================================

    public ArrayList<Item> getStoredItems() {
        return storedItems;
    }

    public ItemStorage setStoredItems(ArrayList<Item> storedItems) {
        this.storedItems = storedItems;
        return this;
    }

    //================================================================================
    // Constructors
    //================================================================================

    public ItemStorage(ArrayList<Item> storedItems) {
        this.setStoredItems(storedItems);
    }

    public ItemStorage(){
        this(new ArrayList<Item>());
    }

    //================================================================================
    // Methods
    //================================================================================

    public void cleanEmptyItems(){
        Iterator i = this.getStoredItems().iterator();
        Item item;
        while(i.hasNext()){
            item = (Item) i.next();
            if(item.shouldBeDiscarded())
                i.remove();
        }
    }

    public boolean add(Item item){
        List<Item.Groupable> list = ItemSearchEngine.findAbleToAdd(this.getStoredItems(), item);
        if(!list.isEmpty()) {
            list.get(0).add(item);
            return true;
        }else{
            this.getStoredItems().add(item);
        }
        this.cleanEmptyItems();
        return false;
    }

    public boolean subtract(Item item) {
        List<Item.Groupable> list = ItemSearchEngine.findAbleToSubtract(this.getStoredItems(), item);
        if(!list.isEmpty()) {
            list.get(0).subtract(item);
            return true;
        }
        this.cleanEmptyItems();
        return false;
    }
}
