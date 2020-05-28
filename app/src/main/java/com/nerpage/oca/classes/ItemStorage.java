package com.nerpage.oca.classes;

import java.util.ArrayList;

public class ItemStorage {
    public static final int MISSING_ITEM = -1;

    private ArrayList<Item> storedItems;

    public ArrayList<Item> getStoredItems() {
        return storedItems;
    }

    public ItemStorage setStoredItems(ArrayList<Item> storedItems) {
        this.storedItems = storedItems;
        return this;
    }

    public ArrayList<Integer> findEx(Item item, int startingPos, int endingPos){
        ArrayList<Integer> searchResults = new ArrayList<Integer>();
        ArrayList<Item> items = getStoredItems();
        for(int i = startingPos; i < endingPos; i++){
            if(items.get(i).equals(item))
                searchResults.add(i);
        }
        return searchResults;
    }

    public ArrayList<Integer> findEx(Item item, int startingPos){
        return this.findEx(item, startingPos, this.getStoredItems().size());
    }

    public ArrayList<Integer> findEx(Item item){
        return this.findEx(item, 0);
    }

    public int find(Item item){
        ArrayList<Integer> results = this.findEx(item, 0);
        if(results.isEmpty())
            return MISSING_ITEM;
        else
            return results.get(0);
    }

    public int find(String id){
        CustomItem customItem = new CustomItem(id);
        return this.find(customItem);
    }

    public boolean add(Item item){
        this.getStoredItems().add(item);
        return true;
    }

    public boolean removeSlot(int position){
        if(this.getStoredItems().size() <= position)
            return false;
        this.getStoredItems().remove(position);
        return true;
    }

    public boolean remove(Item item) {
        int position = this.find(item);
        if(position == MISSING_ITEM)
            return false;
        else{
            removeSlot(position);
            return true;
        }
    }

    public boolean transferItem(ItemStorage receiver, int position){
        Item item = this.getStoredItems().get(position);
        if(receiver.add(item)) {
            this.getStoredItems().remove(position);
            return true;
        }else return false;
    }

    public boolean transferAll(ItemStorage receiver){
        int pivot = 0;

        // method transferItem removes an item from an array so the "for and i++" method isn't viable
        // it tries to give all items by continuously giving the first item in the array
        // pivot makes sure that when whatever reason causes transfer failure, it won't just repeat the same problematic position over and over but moves on
        while(pivot < this.getStoredItems().size()){
            if(!this.transferItem(receiver, pivot))
                pivot++;
        }

        return pivot == 0;
    }



    public ItemStorage(ArrayList<Item> storedItems) {
        this.setStoredItems(storedItems);
    }

    public ItemStorage(){
        this(new ArrayList<Item>());
    }
}
