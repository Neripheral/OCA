package com.nerpage.oca.models;

import android.content.Context;

import com.nerpage.oca.classes.Item;

import java.lang.ref.WeakReference;

public class ItemModel implements Comparable<ItemModel> {
    private WeakReference<Item> itemRef;
    private String id = "";
    private String title = "";
    private String quantity = "";

    public String getId(){
        return id;
    }
    public ItemModel setId(String id){
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }
    public ItemModel setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getQuantity() {
        return quantity;
    }
    public ItemModel setQuantity(String quantity) {
        this.quantity = quantity;
        return this;
    }

    public WeakReference<Item> getItemRef(){
        return this.itemRef;
    }

    @Override
    public int compareTo(ItemModel other){
        String myStr = this.getTitle();
        String otherStr = other.getTitle();
        return myStr.compareTo(otherStr);
    }

    public ItemModel(Item item, Context context){
        this.itemRef = new WeakReference<>(item);
        this.setId(item.getId());
        this.setTitle(item.getName(context));
        if(item instanceof Item.Groupable){
            this.setQuantity(((Item.Groupable)item).getShownQuantity());
        }
    }
}
