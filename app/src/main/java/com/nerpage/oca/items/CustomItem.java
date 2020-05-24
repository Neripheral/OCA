package com.nerpage.oca.items;

import android.content.Context;

public class CustomItem extends Item{
    private String id;
    private String name;

    @Override
    public String getId(){
        return this.id;
    }

    public CustomItem setId(String id){
        this.id = id;
        return this;
    }

    public String getName(){
        return this.name;
    }

    @Override
    public String getName(Context context){
        return this.getName();
    }

    public CustomItem setName(String name){
        this.name = name;
        return this;
    }

    public CustomItem(String id, String name){
        super();
        this.setId(id);
        this.setName(name);
    }

    public CustomItem(String id){
        this(id, "missing_name");
    }
}
