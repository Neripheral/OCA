package com.nerpage.oca.models;

import android.content.Context;

import com.nerpage.oca.items.Item;

public class ItemModel implements Comparable<ItemModel> {
    private String title;

    public String getTitle() {
        return title;
    }
    public ItemModel setTitle(String title) {
        this.title = title;
        return this;
    }

    @Override
    public int compareTo(ItemModel other){
        String myStr = this.getTitle();
        String otherStr = other.getTitle();
        return myStr.compareTo(otherStr);
    }

    public ItemModel(String title){
        this.setTitle(title);
    }

    public ItemModel(Item item, Context context){
        this(item.getName(context));
    }
}
